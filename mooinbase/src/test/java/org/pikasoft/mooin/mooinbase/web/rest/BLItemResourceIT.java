package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.MooinbaseApp;
import org.pikasoft.mooin.mooinbase.domain.BLItem;
import org.pikasoft.mooin.mooinbase.repository.BLItemRepository;
import org.pikasoft.mooin.mooinbase.service.BLItemService;

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
 * Integration tests for the {@link BLItemResource} REST controller.
 */
@SpringBootTest(classes = MooinbaseApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BLItemResourceIT {

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
    private BLItemRepository bLItemRepository;

    @Autowired
    private BLItemService bLItemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBLItemMockMvc;

    private BLItem bLItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BLItem createEntity(EntityManager em) {
        BLItem bLItem = new BLItem()
            .quantity(DEFAULT_QUANTITY)
            .discountRate(DEFAULT_DISCOUNT_RATE)
            .totalHT(DEFAULT_TOTAL_HT)
            .totalTVA(DEFAULT_TOTAL_TVA)
            .totaTTC(DEFAULT_TOTA_TTC);
        return bLItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BLItem createUpdatedEntity(EntityManager em) {
        BLItem bLItem = new BLItem()
            .quantity(UPDATED_QUANTITY)
            .discountRate(UPDATED_DISCOUNT_RATE)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC);
        return bLItem;
    }

    @BeforeEach
    public void initTest() {
        bLItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createBLItem() throws Exception {
        int databaseSizeBeforeCreate = bLItemRepository.findAll().size();
        // Create the BLItem
        restBLItemMockMvc.perform(post("/api/bl-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bLItem)))
            .andExpect(status().isCreated());

        // Validate the BLItem in the database
        List<BLItem> bLItemList = bLItemRepository.findAll();
        assertThat(bLItemList).hasSize(databaseSizeBeforeCreate + 1);
        BLItem testBLItem = bLItemList.get(bLItemList.size() - 1);
        assertThat(testBLItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testBLItem.getDiscountRate()).isEqualTo(DEFAULT_DISCOUNT_RATE);
        assertThat(testBLItem.getTotalHT()).isEqualTo(DEFAULT_TOTAL_HT);
        assertThat(testBLItem.getTotalTVA()).isEqualTo(DEFAULT_TOTAL_TVA);
        assertThat(testBLItem.getTotaTTC()).isEqualTo(DEFAULT_TOTA_TTC);
    }

    @Test
    @Transactional
    public void createBLItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bLItemRepository.findAll().size();

        // Create the BLItem with an existing ID
        bLItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBLItemMockMvc.perform(post("/api/bl-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bLItem)))
            .andExpect(status().isBadRequest());

        // Validate the BLItem in the database
        List<BLItem> bLItemList = bLItemRepository.findAll();
        assertThat(bLItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBLItems() throws Exception {
        // Initialize the database
        bLItemRepository.saveAndFlush(bLItem);

        // Get all the bLItemList
        restBLItemMockMvc.perform(get("/api/bl-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bLItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].discountRate").value(hasItem(DEFAULT_DISCOUNT_RATE)))
            .andExpect(jsonPath("$.[*].totalHT").value(hasItem(DEFAULT_TOTAL_HT.intValue())))
            .andExpect(jsonPath("$.[*].totalTVA").value(hasItem(DEFAULT_TOTAL_TVA.intValue())))
            .andExpect(jsonPath("$.[*].totaTTC").value(hasItem(DEFAULT_TOTA_TTC.intValue())));
    }
    
    @Test
    @Transactional
    public void getBLItem() throws Exception {
        // Initialize the database
        bLItemRepository.saveAndFlush(bLItem);

        // Get the bLItem
        restBLItemMockMvc.perform(get("/api/bl-items/{id}", bLItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bLItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.discountRate").value(DEFAULT_DISCOUNT_RATE))
            .andExpect(jsonPath("$.totalHT").value(DEFAULT_TOTAL_HT.intValue()))
            .andExpect(jsonPath("$.totalTVA").value(DEFAULT_TOTAL_TVA.intValue()))
            .andExpect(jsonPath("$.totaTTC").value(DEFAULT_TOTA_TTC.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingBLItem() throws Exception {
        // Get the bLItem
        restBLItemMockMvc.perform(get("/api/bl-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBLItem() throws Exception {
        // Initialize the database
        bLItemService.save(bLItem);

        int databaseSizeBeforeUpdate = bLItemRepository.findAll().size();

        // Update the bLItem
        BLItem updatedBLItem = bLItemRepository.findById(bLItem.getId()).get();
        // Disconnect from session so that the updates on updatedBLItem are not directly saved in db
        em.detach(updatedBLItem);
        updatedBLItem
            .quantity(UPDATED_QUANTITY)
            .discountRate(UPDATED_DISCOUNT_RATE)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC);

        restBLItemMockMvc.perform(put("/api/bl-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBLItem)))
            .andExpect(status().isOk());

        // Validate the BLItem in the database
        List<BLItem> bLItemList = bLItemRepository.findAll();
        assertThat(bLItemList).hasSize(databaseSizeBeforeUpdate);
        BLItem testBLItem = bLItemList.get(bLItemList.size() - 1);
        assertThat(testBLItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testBLItem.getDiscountRate()).isEqualTo(UPDATED_DISCOUNT_RATE);
        assertThat(testBLItem.getTotalHT()).isEqualTo(UPDATED_TOTAL_HT);
        assertThat(testBLItem.getTotalTVA()).isEqualTo(UPDATED_TOTAL_TVA);
        assertThat(testBLItem.getTotaTTC()).isEqualTo(UPDATED_TOTA_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingBLItem() throws Exception {
        int databaseSizeBeforeUpdate = bLItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBLItemMockMvc.perform(put("/api/bl-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bLItem)))
            .andExpect(status().isBadRequest());

        // Validate the BLItem in the database
        List<BLItem> bLItemList = bLItemRepository.findAll();
        assertThat(bLItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBLItem() throws Exception {
        // Initialize the database
        bLItemService.save(bLItem);

        int databaseSizeBeforeDelete = bLItemRepository.findAll().size();

        // Delete the bLItem
        restBLItemMockMvc.perform(delete("/api/bl-items/{id}", bLItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BLItem> bLItemList = bLItemRepository.findAll();
        assertThat(bLItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
