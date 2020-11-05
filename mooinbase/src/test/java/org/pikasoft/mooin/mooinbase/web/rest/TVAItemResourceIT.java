package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.MooinbaseApp;
import org.pikasoft.mooin.mooinbase.domain.TVAItem;
import org.pikasoft.mooin.mooinbase.repository.TVAItemRepository;
import org.pikasoft.mooin.mooinbase.service.TVAItemService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TVAItemResource} REST controller.
 */
@SpringBootTest(classes = MooinbaseApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TVAItemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PERCENTAGE_VALUE = 1D;
    private static final Double UPDATED_PERCENTAGE_VALUE = 2D;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final Long DEFAULT_TVA_ID = 1L;
    private static final Long UPDATED_TVA_ID = 2L;

    @Autowired
    private TVAItemRepository tVAItemRepository;

    @Autowired
    private TVAItemService tVAItemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTVAItemMockMvc;

    private TVAItem tVAItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TVAItem createEntity(EntityManager em) {
        TVAItem tVAItem = new TVAItem()
            .name(DEFAULT_NAME)
            .percentageValue(DEFAULT_PERCENTAGE_VALUE)
            .companyID(DEFAULT_COMPANY_ID)
            .tvaID(DEFAULT_TVA_ID);
        return tVAItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TVAItem createUpdatedEntity(EntityManager em) {
        TVAItem tVAItem = new TVAItem()
            .name(UPDATED_NAME)
            .percentageValue(UPDATED_PERCENTAGE_VALUE)
            .companyID(UPDATED_COMPANY_ID)
            .tvaID(UPDATED_TVA_ID);
        return tVAItem;
    }

    @BeforeEach
    public void initTest() {
        tVAItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createTVAItem() throws Exception {
        int databaseSizeBeforeCreate = tVAItemRepository.findAll().size();
        // Create the TVAItem
        restTVAItemMockMvc.perform(post("/api/tva-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tVAItem)))
            .andExpect(status().isCreated());

        // Validate the TVAItem in the database
        List<TVAItem> tVAItemList = tVAItemRepository.findAll();
        assertThat(tVAItemList).hasSize(databaseSizeBeforeCreate + 1);
        TVAItem testTVAItem = tVAItemList.get(tVAItemList.size() - 1);
        assertThat(testTVAItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTVAItem.getPercentageValue()).isEqualTo(DEFAULT_PERCENTAGE_VALUE);
        assertThat(testTVAItem.getCompanyID()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testTVAItem.getTvaID()).isEqualTo(DEFAULT_TVA_ID);
    }

    @Test
    @Transactional
    public void createTVAItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tVAItemRepository.findAll().size();

        // Create the TVAItem with an existing ID
        tVAItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTVAItemMockMvc.perform(post("/api/tva-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tVAItem)))
            .andExpect(status().isBadRequest());

        // Validate the TVAItem in the database
        List<TVAItem> tVAItemList = tVAItemRepository.findAll();
        assertThat(tVAItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tVAItemRepository.findAll().size();
        // set the field null
        tVAItem.setName(null);

        // Create the TVAItem, which fails.


        restTVAItemMockMvc.perform(post("/api/tva-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tVAItem)))
            .andExpect(status().isBadRequest());

        List<TVAItem> tVAItemList = tVAItemRepository.findAll();
        assertThat(tVAItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPercentageValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = tVAItemRepository.findAll().size();
        // set the field null
        tVAItem.setPercentageValue(null);

        // Create the TVAItem, which fails.


        restTVAItemMockMvc.perform(post("/api/tva-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tVAItem)))
            .andExpect(status().isBadRequest());

        List<TVAItem> tVAItemList = tVAItemRepository.findAll();
        assertThat(tVAItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTVAItems() throws Exception {
        // Initialize the database
        tVAItemRepository.saveAndFlush(tVAItem);

        // Get all the tVAItemList
        restTVAItemMockMvc.perform(get("/api/tva-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tVAItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].percentageValue").value(hasItem(DEFAULT_PERCENTAGE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].companyID").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].tvaID").value(hasItem(DEFAULT_TVA_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getTVAItem() throws Exception {
        // Initialize the database
        tVAItemRepository.saveAndFlush(tVAItem);

        // Get the tVAItem
        restTVAItemMockMvc.perform(get("/api/tva-items/{id}", tVAItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tVAItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.percentageValue").value(DEFAULT_PERCENTAGE_VALUE.doubleValue()))
            .andExpect(jsonPath("$.companyID").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.tvaID").value(DEFAULT_TVA_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTVAItem() throws Exception {
        // Get the tVAItem
        restTVAItemMockMvc.perform(get("/api/tva-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTVAItem() throws Exception {
        // Initialize the database
        tVAItemService.save(tVAItem);

        int databaseSizeBeforeUpdate = tVAItemRepository.findAll().size();

        // Update the tVAItem
        TVAItem updatedTVAItem = tVAItemRepository.findById(tVAItem.getId()).get();
        // Disconnect from session so that the updates on updatedTVAItem are not directly saved in db
        em.detach(updatedTVAItem);
        updatedTVAItem
            .name(UPDATED_NAME)
            .percentageValue(UPDATED_PERCENTAGE_VALUE)
            .companyID(UPDATED_COMPANY_ID)
            .tvaID(UPDATED_TVA_ID);

        restTVAItemMockMvc.perform(put("/api/tva-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTVAItem)))
            .andExpect(status().isOk());

        // Validate the TVAItem in the database
        List<TVAItem> tVAItemList = tVAItemRepository.findAll();
        assertThat(tVAItemList).hasSize(databaseSizeBeforeUpdate);
        TVAItem testTVAItem = tVAItemList.get(tVAItemList.size() - 1);
        assertThat(testTVAItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTVAItem.getPercentageValue()).isEqualTo(UPDATED_PERCENTAGE_VALUE);
        assertThat(testTVAItem.getCompanyID()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testTVAItem.getTvaID()).isEqualTo(UPDATED_TVA_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTVAItem() throws Exception {
        int databaseSizeBeforeUpdate = tVAItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTVAItemMockMvc.perform(put("/api/tva-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tVAItem)))
            .andExpect(status().isBadRequest());

        // Validate the TVAItem in the database
        List<TVAItem> tVAItemList = tVAItemRepository.findAll();
        assertThat(tVAItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTVAItem() throws Exception {
        // Initialize the database
        tVAItemService.save(tVAItem);

        int databaseSizeBeforeDelete = tVAItemRepository.findAll().size();

        // Delete the tVAItem
        restTVAItemMockMvc.perform(delete("/api/tva-items/{id}", tVAItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TVAItem> tVAItemList = tVAItemRepository.findAll();
        assertThat(tVAItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
