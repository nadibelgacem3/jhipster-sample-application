package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.MooincompaniesApp;
import org.pikasoft.mooin.mooincompanies.domain.TVA;
import org.pikasoft.mooin.mooincompanies.repository.TVARepository;
import org.pikasoft.mooin.mooincompanies.service.TVAService;

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
 * Integration tests for the {@link TVAResource} REST controller.
 */
@SpringBootTest(classes = MooincompaniesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TVAResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PERCENTAGE_VALUE = 1D;
    private static final Double UPDATED_PERCENTAGE_VALUE = 2D;

    @Autowired
    private TVARepository tVARepository;

    @Autowired
    private TVAService tVAService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTVAMockMvc;

    private TVA tVA;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TVA createEntity(EntityManager em) {
        TVA tVA = new TVA()
            .name(DEFAULT_NAME)
            .percentageValue(DEFAULT_PERCENTAGE_VALUE);
        return tVA;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TVA createUpdatedEntity(EntityManager em) {
        TVA tVA = new TVA()
            .name(UPDATED_NAME)
            .percentageValue(UPDATED_PERCENTAGE_VALUE);
        return tVA;
    }

    @BeforeEach
    public void initTest() {
        tVA = createEntity(em);
    }

    @Test
    @Transactional
    public void createTVA() throws Exception {
        int databaseSizeBeforeCreate = tVARepository.findAll().size();
        // Create the TVA
        restTVAMockMvc.perform(post("/api/tvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tVA)))
            .andExpect(status().isCreated());

        // Validate the TVA in the database
        List<TVA> tVAList = tVARepository.findAll();
        assertThat(tVAList).hasSize(databaseSizeBeforeCreate + 1);
        TVA testTVA = tVAList.get(tVAList.size() - 1);
        assertThat(testTVA.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTVA.getPercentageValue()).isEqualTo(DEFAULT_PERCENTAGE_VALUE);
    }

    @Test
    @Transactional
    public void createTVAWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tVARepository.findAll().size();

        // Create the TVA with an existing ID
        tVA.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTVAMockMvc.perform(post("/api/tvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tVA)))
            .andExpect(status().isBadRequest());

        // Validate the TVA in the database
        List<TVA> tVAList = tVARepository.findAll();
        assertThat(tVAList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tVARepository.findAll().size();
        // set the field null
        tVA.setName(null);

        // Create the TVA, which fails.


        restTVAMockMvc.perform(post("/api/tvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tVA)))
            .andExpect(status().isBadRequest());

        List<TVA> tVAList = tVARepository.findAll();
        assertThat(tVAList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPercentageValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = tVARepository.findAll().size();
        // set the field null
        tVA.setPercentageValue(null);

        // Create the TVA, which fails.


        restTVAMockMvc.perform(post("/api/tvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tVA)))
            .andExpect(status().isBadRequest());

        List<TVA> tVAList = tVARepository.findAll();
        assertThat(tVAList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTVAS() throws Exception {
        // Initialize the database
        tVARepository.saveAndFlush(tVA);

        // Get all the tVAList
        restTVAMockMvc.perform(get("/api/tvas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tVA.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].percentageValue").value(hasItem(DEFAULT_PERCENTAGE_VALUE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTVA() throws Exception {
        // Initialize the database
        tVARepository.saveAndFlush(tVA);

        // Get the tVA
        restTVAMockMvc.perform(get("/api/tvas/{id}", tVA.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tVA.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.percentageValue").value(DEFAULT_PERCENTAGE_VALUE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTVA() throws Exception {
        // Get the tVA
        restTVAMockMvc.perform(get("/api/tvas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTVA() throws Exception {
        // Initialize the database
        tVAService.save(tVA);

        int databaseSizeBeforeUpdate = tVARepository.findAll().size();

        // Update the tVA
        TVA updatedTVA = tVARepository.findById(tVA.getId()).get();
        // Disconnect from session so that the updates on updatedTVA are not directly saved in db
        em.detach(updatedTVA);
        updatedTVA
            .name(UPDATED_NAME)
            .percentageValue(UPDATED_PERCENTAGE_VALUE);

        restTVAMockMvc.perform(put("/api/tvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTVA)))
            .andExpect(status().isOk());

        // Validate the TVA in the database
        List<TVA> tVAList = tVARepository.findAll();
        assertThat(tVAList).hasSize(databaseSizeBeforeUpdate);
        TVA testTVA = tVAList.get(tVAList.size() - 1);
        assertThat(testTVA.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTVA.getPercentageValue()).isEqualTo(UPDATED_PERCENTAGE_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingTVA() throws Exception {
        int databaseSizeBeforeUpdate = tVARepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTVAMockMvc.perform(put("/api/tvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tVA)))
            .andExpect(status().isBadRequest());

        // Validate the TVA in the database
        List<TVA> tVAList = tVARepository.findAll();
        assertThat(tVAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTVA() throws Exception {
        // Initialize the database
        tVAService.save(tVA);

        int databaseSizeBeforeDelete = tVARepository.findAll().size();

        // Delete the tVA
        restTVAMockMvc.perform(delete("/api/tvas/{id}", tVA.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TVA> tVAList = tVARepository.findAll();
        assertThat(tVAList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
