package org.pikasoft.mooin.mooincashier.web.rest;

import org.pikasoft.mooin.mooincashier.MooincashierApp;
import org.pikasoft.mooin.mooincashier.domain.CashierApproItem;
import org.pikasoft.mooin.mooincashier.repository.CashierApproItemRepository;
import org.pikasoft.mooin.mooincashier.service.CashierApproItemService;

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
 * Integration tests for the {@link CashierApproItemResource} REST controller.
 */
@SpringBootTest(classes = MooincashierApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CashierApproItemResourceIT {

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
    private CashierApproItemRepository cashierApproItemRepository;

    @Autowired
    private CashierApproItemService cashierApproItemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCashierApproItemMockMvc;

    private CashierApproItem cashierApproItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CashierApproItem createEntity(EntityManager em) {
        CashierApproItem cashierApproItem = new CashierApproItem()
            .quantity(DEFAULT_QUANTITY)
            .discountRate(DEFAULT_DISCOUNT_RATE)
            .totalHT(DEFAULT_TOTAL_HT)
            .totalTVA(DEFAULT_TOTAL_TVA)
            .totaTTC(DEFAULT_TOTA_TTC);
        return cashierApproItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CashierApproItem createUpdatedEntity(EntityManager em) {
        CashierApproItem cashierApproItem = new CashierApproItem()
            .quantity(UPDATED_QUANTITY)
            .discountRate(UPDATED_DISCOUNT_RATE)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC);
        return cashierApproItem;
    }

    @BeforeEach
    public void initTest() {
        cashierApproItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createCashierApproItem() throws Exception {
        int databaseSizeBeforeCreate = cashierApproItemRepository.findAll().size();
        // Create the CashierApproItem
        restCashierApproItemMockMvc.perform(post("/api/cashier-appro-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierApproItem)))
            .andExpect(status().isCreated());

        // Validate the CashierApproItem in the database
        List<CashierApproItem> cashierApproItemList = cashierApproItemRepository.findAll();
        assertThat(cashierApproItemList).hasSize(databaseSizeBeforeCreate + 1);
        CashierApproItem testCashierApproItem = cashierApproItemList.get(cashierApproItemList.size() - 1);
        assertThat(testCashierApproItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testCashierApproItem.getDiscountRate()).isEqualTo(DEFAULT_DISCOUNT_RATE);
        assertThat(testCashierApproItem.getTotalHT()).isEqualTo(DEFAULT_TOTAL_HT);
        assertThat(testCashierApproItem.getTotalTVA()).isEqualTo(DEFAULT_TOTAL_TVA);
        assertThat(testCashierApproItem.getTotaTTC()).isEqualTo(DEFAULT_TOTA_TTC);
    }

    @Test
    @Transactional
    public void createCashierApproItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cashierApproItemRepository.findAll().size();

        // Create the CashierApproItem with an existing ID
        cashierApproItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCashierApproItemMockMvc.perform(post("/api/cashier-appro-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierApproItem)))
            .andExpect(status().isBadRequest());

        // Validate the CashierApproItem in the database
        List<CashierApproItem> cashierApproItemList = cashierApproItemRepository.findAll();
        assertThat(cashierApproItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCashierApproItems() throws Exception {
        // Initialize the database
        cashierApproItemRepository.saveAndFlush(cashierApproItem);

        // Get all the cashierApproItemList
        restCashierApproItemMockMvc.perform(get("/api/cashier-appro-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cashierApproItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].discountRate").value(hasItem(DEFAULT_DISCOUNT_RATE)))
            .andExpect(jsonPath("$.[*].totalHT").value(hasItem(DEFAULT_TOTAL_HT.intValue())))
            .andExpect(jsonPath("$.[*].totalTVA").value(hasItem(DEFAULT_TOTAL_TVA.intValue())))
            .andExpect(jsonPath("$.[*].totaTTC").value(hasItem(DEFAULT_TOTA_TTC.intValue())));
    }
    
    @Test
    @Transactional
    public void getCashierApproItem() throws Exception {
        // Initialize the database
        cashierApproItemRepository.saveAndFlush(cashierApproItem);

        // Get the cashierApproItem
        restCashierApproItemMockMvc.perform(get("/api/cashier-appro-items/{id}", cashierApproItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cashierApproItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.discountRate").value(DEFAULT_DISCOUNT_RATE))
            .andExpect(jsonPath("$.totalHT").value(DEFAULT_TOTAL_HT.intValue()))
            .andExpect(jsonPath("$.totalTVA").value(DEFAULT_TOTAL_TVA.intValue()))
            .andExpect(jsonPath("$.totaTTC").value(DEFAULT_TOTA_TTC.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCashierApproItem() throws Exception {
        // Get the cashierApproItem
        restCashierApproItemMockMvc.perform(get("/api/cashier-appro-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCashierApproItem() throws Exception {
        // Initialize the database
        cashierApproItemService.save(cashierApproItem);

        int databaseSizeBeforeUpdate = cashierApproItemRepository.findAll().size();

        // Update the cashierApproItem
        CashierApproItem updatedCashierApproItem = cashierApproItemRepository.findById(cashierApproItem.getId()).get();
        // Disconnect from session so that the updates on updatedCashierApproItem are not directly saved in db
        em.detach(updatedCashierApproItem);
        updatedCashierApproItem
            .quantity(UPDATED_QUANTITY)
            .discountRate(UPDATED_DISCOUNT_RATE)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC);

        restCashierApproItemMockMvc.perform(put("/api/cashier-appro-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCashierApproItem)))
            .andExpect(status().isOk());

        // Validate the CashierApproItem in the database
        List<CashierApproItem> cashierApproItemList = cashierApproItemRepository.findAll();
        assertThat(cashierApproItemList).hasSize(databaseSizeBeforeUpdate);
        CashierApproItem testCashierApproItem = cashierApproItemList.get(cashierApproItemList.size() - 1);
        assertThat(testCashierApproItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testCashierApproItem.getDiscountRate()).isEqualTo(UPDATED_DISCOUNT_RATE);
        assertThat(testCashierApproItem.getTotalHT()).isEqualTo(UPDATED_TOTAL_HT);
        assertThat(testCashierApproItem.getTotalTVA()).isEqualTo(UPDATED_TOTAL_TVA);
        assertThat(testCashierApproItem.getTotaTTC()).isEqualTo(UPDATED_TOTA_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingCashierApproItem() throws Exception {
        int databaseSizeBeforeUpdate = cashierApproItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCashierApproItemMockMvc.perform(put("/api/cashier-appro-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierApproItem)))
            .andExpect(status().isBadRequest());

        // Validate the CashierApproItem in the database
        List<CashierApproItem> cashierApproItemList = cashierApproItemRepository.findAll();
        assertThat(cashierApproItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCashierApproItem() throws Exception {
        // Initialize the database
        cashierApproItemService.save(cashierApproItem);

        int databaseSizeBeforeDelete = cashierApproItemRepository.findAll().size();

        // Delete the cashierApproItem
        restCashierApproItemMockMvc.perform(delete("/api/cashier-appro-items/{id}", cashierApproItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CashierApproItem> cashierApproItemList = cashierApproItemRepository.findAll();
        assertThat(cashierApproItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
