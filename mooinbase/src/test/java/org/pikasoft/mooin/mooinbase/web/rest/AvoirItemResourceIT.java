package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.MooinbaseApp;
import org.pikasoft.mooin.mooinbase.domain.AvoirItem;
import org.pikasoft.mooin.mooinbase.repository.AvoirItemRepository;
import org.pikasoft.mooin.mooinbase.service.AvoirItemService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AvoirItemResource} REST controller.
 */
@SpringBootTest(classes = MooinbaseApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AvoirItemResourceIT {

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(0);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(1);

    private static final Integer DEFAULT_DISCOUNT_RATE = 0;
    private static final Integer UPDATED_DISCOUNT_RATE = 1;

    private static final BigDecimal DEFAULT_TOTAL_HT = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_HT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TOTAL_TVA = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_TVA = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TOTA_TTC = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTA_TTC = new BigDecimal(1);

    @Autowired
    private AvoirItemRepository avoirItemRepository;

    @Autowired
    private AvoirItemService avoirItemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvoirItemMockMvc;

    private AvoirItem avoirItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvoirItem createEntity(EntityManager em) {
        AvoirItem avoirItem = new AvoirItem()
            .quantity(DEFAULT_QUANTITY)
            .discountRate(DEFAULT_DISCOUNT_RATE)
            .totalHT(DEFAULT_TOTAL_HT)
            .totalTVA(DEFAULT_TOTAL_TVA)
            .totaTTC(DEFAULT_TOTA_TTC);
        return avoirItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvoirItem createUpdatedEntity(EntityManager em) {
        AvoirItem avoirItem = new AvoirItem()
            .quantity(UPDATED_QUANTITY)
            .discountRate(UPDATED_DISCOUNT_RATE)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC);
        return avoirItem;
    }

    @BeforeEach
    public void initTest() {
        avoirItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvoirItem() throws Exception {
        int databaseSizeBeforeCreate = avoirItemRepository.findAll().size();
        // Create the AvoirItem
        restAvoirItemMockMvc.perform(post("/api/avoir-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avoirItem)))
            .andExpect(status().isCreated());

        // Validate the AvoirItem in the database
        List<AvoirItem> avoirItemList = avoirItemRepository.findAll();
        assertThat(avoirItemList).hasSize(databaseSizeBeforeCreate + 1);
        AvoirItem testAvoirItem = avoirItemList.get(avoirItemList.size() - 1);
        assertThat(testAvoirItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testAvoirItem.getDiscountRate()).isEqualTo(DEFAULT_DISCOUNT_RATE);
        assertThat(testAvoirItem.getTotalHT()).isEqualTo(DEFAULT_TOTAL_HT);
        assertThat(testAvoirItem.getTotalTVA()).isEqualTo(DEFAULT_TOTAL_TVA);
        assertThat(testAvoirItem.getTotaTTC()).isEqualTo(DEFAULT_TOTA_TTC);
    }

    @Test
    @Transactional
    public void createAvoirItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avoirItemRepository.findAll().size();

        // Create the AvoirItem with an existing ID
        avoirItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvoirItemMockMvc.perform(post("/api/avoir-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avoirItem)))
            .andExpect(status().isBadRequest());

        // Validate the AvoirItem in the database
        List<AvoirItem> avoirItemList = avoirItemRepository.findAll();
        assertThat(avoirItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAvoirItems() throws Exception {
        // Initialize the database
        avoirItemRepository.saveAndFlush(avoirItem);

        // Get all the avoirItemList
        restAvoirItemMockMvc.perform(get("/api/avoir-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avoirItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].discountRate").value(hasItem(DEFAULT_DISCOUNT_RATE)))
            .andExpect(jsonPath("$.[*].totalHT").value(hasItem(DEFAULT_TOTAL_HT.intValue())))
            .andExpect(jsonPath("$.[*].totalTVA").value(hasItem(DEFAULT_TOTAL_TVA.intValue())))
            .andExpect(jsonPath("$.[*].totaTTC").value(hasItem(DEFAULT_TOTA_TTC.intValue())));
    }
    
    @Test
    @Transactional
    public void getAvoirItem() throws Exception {
        // Initialize the database
        avoirItemRepository.saveAndFlush(avoirItem);

        // Get the avoirItem
        restAvoirItemMockMvc.perform(get("/api/avoir-items/{id}", avoirItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avoirItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.discountRate").value(DEFAULT_DISCOUNT_RATE))
            .andExpect(jsonPath("$.totalHT").value(DEFAULT_TOTAL_HT.intValue()))
            .andExpect(jsonPath("$.totalTVA").value(DEFAULT_TOTAL_TVA.intValue()))
            .andExpect(jsonPath("$.totaTTC").value(DEFAULT_TOTA_TTC.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAvoirItem() throws Exception {
        // Get the avoirItem
        restAvoirItemMockMvc.perform(get("/api/avoir-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvoirItem() throws Exception {
        // Initialize the database
        avoirItemService.save(avoirItem);

        int databaseSizeBeforeUpdate = avoirItemRepository.findAll().size();

        // Update the avoirItem
        AvoirItem updatedAvoirItem = avoirItemRepository.findById(avoirItem.getId()).get();
        // Disconnect from session so that the updates on updatedAvoirItem are not directly saved in db
        em.detach(updatedAvoirItem);
        updatedAvoirItem
            .quantity(UPDATED_QUANTITY)
            .discountRate(UPDATED_DISCOUNT_RATE)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC);

        restAvoirItemMockMvc.perform(put("/api/avoir-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvoirItem)))
            .andExpect(status().isOk());

        // Validate the AvoirItem in the database
        List<AvoirItem> avoirItemList = avoirItemRepository.findAll();
        assertThat(avoirItemList).hasSize(databaseSizeBeforeUpdate);
        AvoirItem testAvoirItem = avoirItemList.get(avoirItemList.size() - 1);
        assertThat(testAvoirItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testAvoirItem.getDiscountRate()).isEqualTo(UPDATED_DISCOUNT_RATE);
        assertThat(testAvoirItem.getTotalHT()).isEqualTo(UPDATED_TOTAL_HT);
        assertThat(testAvoirItem.getTotalTVA()).isEqualTo(UPDATED_TOTAL_TVA);
        assertThat(testAvoirItem.getTotaTTC()).isEqualTo(UPDATED_TOTA_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingAvoirItem() throws Exception {
        int databaseSizeBeforeUpdate = avoirItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvoirItemMockMvc.perform(put("/api/avoir-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avoirItem)))
            .andExpect(status().isBadRequest());

        // Validate the AvoirItem in the database
        List<AvoirItem> avoirItemList = avoirItemRepository.findAll();
        assertThat(avoirItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAvoirItem() throws Exception {
        // Initialize the database
        avoirItemService.save(avoirItem);

        int databaseSizeBeforeDelete = avoirItemRepository.findAll().size();

        // Delete the avoirItem
        restAvoirItemMockMvc.perform(delete("/api/avoir-items/{id}", avoirItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AvoirItem> avoirItemList = avoirItemRepository.findAll();
        assertThat(avoirItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
