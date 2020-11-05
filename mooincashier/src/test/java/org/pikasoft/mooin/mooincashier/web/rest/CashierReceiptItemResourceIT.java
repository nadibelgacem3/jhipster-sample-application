package org.pikasoft.mooin.mooincashier.web.rest;

import org.pikasoft.mooin.mooincashier.MooincashierApp;
import org.pikasoft.mooin.mooincashier.domain.CashierReceiptItem;
import org.pikasoft.mooin.mooincashier.repository.CashierReceiptItemRepository;
import org.pikasoft.mooin.mooincashier.service.CashierReceiptItemService;

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
 * Integration tests for the {@link CashierReceiptItemResource} REST controller.
 */
@SpringBootTest(classes = MooincashierApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CashierReceiptItemResourceIT {

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
    private CashierReceiptItemRepository cashierReceiptItemRepository;

    @Autowired
    private CashierReceiptItemService cashierReceiptItemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCashierReceiptItemMockMvc;

    private CashierReceiptItem cashierReceiptItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CashierReceiptItem createEntity(EntityManager em) {
        CashierReceiptItem cashierReceiptItem = new CashierReceiptItem()
            .quantity(DEFAULT_QUANTITY)
            .discountRate(DEFAULT_DISCOUNT_RATE)
            .totalHT(DEFAULT_TOTAL_HT)
            .totalTVA(DEFAULT_TOTAL_TVA)
            .totaTTC(DEFAULT_TOTA_TTC);
        return cashierReceiptItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CashierReceiptItem createUpdatedEntity(EntityManager em) {
        CashierReceiptItem cashierReceiptItem = new CashierReceiptItem()
            .quantity(UPDATED_QUANTITY)
            .discountRate(UPDATED_DISCOUNT_RATE)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC);
        return cashierReceiptItem;
    }

    @BeforeEach
    public void initTest() {
        cashierReceiptItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createCashierReceiptItem() throws Exception {
        int databaseSizeBeforeCreate = cashierReceiptItemRepository.findAll().size();
        // Create the CashierReceiptItem
        restCashierReceiptItemMockMvc.perform(post("/api/cashier-receipt-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierReceiptItem)))
            .andExpect(status().isCreated());

        // Validate the CashierReceiptItem in the database
        List<CashierReceiptItem> cashierReceiptItemList = cashierReceiptItemRepository.findAll();
        assertThat(cashierReceiptItemList).hasSize(databaseSizeBeforeCreate + 1);
        CashierReceiptItem testCashierReceiptItem = cashierReceiptItemList.get(cashierReceiptItemList.size() - 1);
        assertThat(testCashierReceiptItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testCashierReceiptItem.getDiscountRate()).isEqualTo(DEFAULT_DISCOUNT_RATE);
        assertThat(testCashierReceiptItem.getTotalHT()).isEqualTo(DEFAULT_TOTAL_HT);
        assertThat(testCashierReceiptItem.getTotalTVA()).isEqualTo(DEFAULT_TOTAL_TVA);
        assertThat(testCashierReceiptItem.getTotaTTC()).isEqualTo(DEFAULT_TOTA_TTC);
    }

    @Test
    @Transactional
    public void createCashierReceiptItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cashierReceiptItemRepository.findAll().size();

        // Create the CashierReceiptItem with an existing ID
        cashierReceiptItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCashierReceiptItemMockMvc.perform(post("/api/cashier-receipt-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierReceiptItem)))
            .andExpect(status().isBadRequest());

        // Validate the CashierReceiptItem in the database
        List<CashierReceiptItem> cashierReceiptItemList = cashierReceiptItemRepository.findAll();
        assertThat(cashierReceiptItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCashierReceiptItems() throws Exception {
        // Initialize the database
        cashierReceiptItemRepository.saveAndFlush(cashierReceiptItem);

        // Get all the cashierReceiptItemList
        restCashierReceiptItemMockMvc.perform(get("/api/cashier-receipt-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cashierReceiptItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].discountRate").value(hasItem(DEFAULT_DISCOUNT_RATE)))
            .andExpect(jsonPath("$.[*].totalHT").value(hasItem(DEFAULT_TOTAL_HT.intValue())))
            .andExpect(jsonPath("$.[*].totalTVA").value(hasItem(DEFAULT_TOTAL_TVA.intValue())))
            .andExpect(jsonPath("$.[*].totaTTC").value(hasItem(DEFAULT_TOTA_TTC.intValue())));
    }
    
    @Test
    @Transactional
    public void getCashierReceiptItem() throws Exception {
        // Initialize the database
        cashierReceiptItemRepository.saveAndFlush(cashierReceiptItem);

        // Get the cashierReceiptItem
        restCashierReceiptItemMockMvc.perform(get("/api/cashier-receipt-items/{id}", cashierReceiptItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cashierReceiptItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.discountRate").value(DEFAULT_DISCOUNT_RATE))
            .andExpect(jsonPath("$.totalHT").value(DEFAULT_TOTAL_HT.intValue()))
            .andExpect(jsonPath("$.totalTVA").value(DEFAULT_TOTAL_TVA.intValue()))
            .andExpect(jsonPath("$.totaTTC").value(DEFAULT_TOTA_TTC.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCashierReceiptItem() throws Exception {
        // Get the cashierReceiptItem
        restCashierReceiptItemMockMvc.perform(get("/api/cashier-receipt-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCashierReceiptItem() throws Exception {
        // Initialize the database
        cashierReceiptItemService.save(cashierReceiptItem);

        int databaseSizeBeforeUpdate = cashierReceiptItemRepository.findAll().size();

        // Update the cashierReceiptItem
        CashierReceiptItem updatedCashierReceiptItem = cashierReceiptItemRepository.findById(cashierReceiptItem.getId()).get();
        // Disconnect from session so that the updates on updatedCashierReceiptItem are not directly saved in db
        em.detach(updatedCashierReceiptItem);
        updatedCashierReceiptItem
            .quantity(UPDATED_QUANTITY)
            .discountRate(UPDATED_DISCOUNT_RATE)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC);

        restCashierReceiptItemMockMvc.perform(put("/api/cashier-receipt-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCashierReceiptItem)))
            .andExpect(status().isOk());

        // Validate the CashierReceiptItem in the database
        List<CashierReceiptItem> cashierReceiptItemList = cashierReceiptItemRepository.findAll();
        assertThat(cashierReceiptItemList).hasSize(databaseSizeBeforeUpdate);
        CashierReceiptItem testCashierReceiptItem = cashierReceiptItemList.get(cashierReceiptItemList.size() - 1);
        assertThat(testCashierReceiptItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testCashierReceiptItem.getDiscountRate()).isEqualTo(UPDATED_DISCOUNT_RATE);
        assertThat(testCashierReceiptItem.getTotalHT()).isEqualTo(UPDATED_TOTAL_HT);
        assertThat(testCashierReceiptItem.getTotalTVA()).isEqualTo(UPDATED_TOTAL_TVA);
        assertThat(testCashierReceiptItem.getTotaTTC()).isEqualTo(UPDATED_TOTA_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingCashierReceiptItem() throws Exception {
        int databaseSizeBeforeUpdate = cashierReceiptItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCashierReceiptItemMockMvc.perform(put("/api/cashier-receipt-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierReceiptItem)))
            .andExpect(status().isBadRequest());

        // Validate the CashierReceiptItem in the database
        List<CashierReceiptItem> cashierReceiptItemList = cashierReceiptItemRepository.findAll();
        assertThat(cashierReceiptItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCashierReceiptItem() throws Exception {
        // Initialize the database
        cashierReceiptItemService.save(cashierReceiptItem);

        int databaseSizeBeforeDelete = cashierReceiptItemRepository.findAll().size();

        // Delete the cashierReceiptItem
        restCashierReceiptItemMockMvc.perform(delete("/api/cashier-receipt-items/{id}", cashierReceiptItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CashierReceiptItem> cashierReceiptItemList = cashierReceiptItemRepository.findAll();
        assertThat(cashierReceiptItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
