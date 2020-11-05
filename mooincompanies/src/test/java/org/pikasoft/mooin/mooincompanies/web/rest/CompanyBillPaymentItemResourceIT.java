package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.MooincompaniesApp;
import org.pikasoft.mooin.mooincompanies.domain.CompanyBillPaymentItem;
import org.pikasoft.mooin.mooincompanies.repository.CompanyBillPaymentItemRepository;
import org.pikasoft.mooin.mooincompanies.service.CompanyBillPaymentItemService;

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
 * Integration tests for the {@link CompanyBillPaymentItemResource} REST controller.
 */
@SpringBootTest(classes = MooincompaniesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompanyBillPaymentItemResourceIT {

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
    private CompanyBillPaymentItemRepository companyBillPaymentItemRepository;

    @Autowired
    private CompanyBillPaymentItemService companyBillPaymentItemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyBillPaymentItemMockMvc;

    private CompanyBillPaymentItem companyBillPaymentItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyBillPaymentItem createEntity(EntityManager em) {
        CompanyBillPaymentItem companyBillPaymentItem = new CompanyBillPaymentItem()
            .quantity(DEFAULT_QUANTITY)
            .discountRate(DEFAULT_DISCOUNT_RATE)
            .totalHT(DEFAULT_TOTAL_HT)
            .totalTVA(DEFAULT_TOTAL_TVA)
            .totaTTC(DEFAULT_TOTA_TTC);
        return companyBillPaymentItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyBillPaymentItem createUpdatedEntity(EntityManager em) {
        CompanyBillPaymentItem companyBillPaymentItem = new CompanyBillPaymentItem()
            .quantity(UPDATED_QUANTITY)
            .discountRate(UPDATED_DISCOUNT_RATE)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC);
        return companyBillPaymentItem;
    }

    @BeforeEach
    public void initTest() {
        companyBillPaymentItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyBillPaymentItem() throws Exception {
        int databaseSizeBeforeCreate = companyBillPaymentItemRepository.findAll().size();
        // Create the CompanyBillPaymentItem
        restCompanyBillPaymentItemMockMvc.perform(post("/api/company-bill-payment-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyBillPaymentItem)))
            .andExpect(status().isCreated());

        // Validate the CompanyBillPaymentItem in the database
        List<CompanyBillPaymentItem> companyBillPaymentItemList = companyBillPaymentItemRepository.findAll();
        assertThat(companyBillPaymentItemList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyBillPaymentItem testCompanyBillPaymentItem = companyBillPaymentItemList.get(companyBillPaymentItemList.size() - 1);
        assertThat(testCompanyBillPaymentItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testCompanyBillPaymentItem.getDiscountRate()).isEqualTo(DEFAULT_DISCOUNT_RATE);
        assertThat(testCompanyBillPaymentItem.getTotalHT()).isEqualTo(DEFAULT_TOTAL_HT);
        assertThat(testCompanyBillPaymentItem.getTotalTVA()).isEqualTo(DEFAULT_TOTAL_TVA);
        assertThat(testCompanyBillPaymentItem.getTotaTTC()).isEqualTo(DEFAULT_TOTA_TTC);
    }

    @Test
    @Transactional
    public void createCompanyBillPaymentItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyBillPaymentItemRepository.findAll().size();

        // Create the CompanyBillPaymentItem with an existing ID
        companyBillPaymentItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyBillPaymentItemMockMvc.perform(post("/api/company-bill-payment-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyBillPaymentItem)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyBillPaymentItem in the database
        List<CompanyBillPaymentItem> companyBillPaymentItemList = companyBillPaymentItemRepository.findAll();
        assertThat(companyBillPaymentItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompanyBillPaymentItems() throws Exception {
        // Initialize the database
        companyBillPaymentItemRepository.saveAndFlush(companyBillPaymentItem);

        // Get all the companyBillPaymentItemList
        restCompanyBillPaymentItemMockMvc.perform(get("/api/company-bill-payment-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyBillPaymentItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].discountRate").value(hasItem(DEFAULT_DISCOUNT_RATE)))
            .andExpect(jsonPath("$.[*].totalHT").value(hasItem(DEFAULT_TOTAL_HT.intValue())))
            .andExpect(jsonPath("$.[*].totalTVA").value(hasItem(DEFAULT_TOTAL_TVA.intValue())))
            .andExpect(jsonPath("$.[*].totaTTC").value(hasItem(DEFAULT_TOTA_TTC.intValue())));
    }
    
    @Test
    @Transactional
    public void getCompanyBillPaymentItem() throws Exception {
        // Initialize the database
        companyBillPaymentItemRepository.saveAndFlush(companyBillPaymentItem);

        // Get the companyBillPaymentItem
        restCompanyBillPaymentItemMockMvc.perform(get("/api/company-bill-payment-items/{id}", companyBillPaymentItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyBillPaymentItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.discountRate").value(DEFAULT_DISCOUNT_RATE))
            .andExpect(jsonPath("$.totalHT").value(DEFAULT_TOTAL_HT.intValue()))
            .andExpect(jsonPath("$.totalTVA").value(DEFAULT_TOTAL_TVA.intValue()))
            .andExpect(jsonPath("$.totaTTC").value(DEFAULT_TOTA_TTC.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCompanyBillPaymentItem() throws Exception {
        // Get the companyBillPaymentItem
        restCompanyBillPaymentItemMockMvc.perform(get("/api/company-bill-payment-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyBillPaymentItem() throws Exception {
        // Initialize the database
        companyBillPaymentItemService.save(companyBillPaymentItem);

        int databaseSizeBeforeUpdate = companyBillPaymentItemRepository.findAll().size();

        // Update the companyBillPaymentItem
        CompanyBillPaymentItem updatedCompanyBillPaymentItem = companyBillPaymentItemRepository.findById(companyBillPaymentItem.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyBillPaymentItem are not directly saved in db
        em.detach(updatedCompanyBillPaymentItem);
        updatedCompanyBillPaymentItem
            .quantity(UPDATED_QUANTITY)
            .discountRate(UPDATED_DISCOUNT_RATE)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC);

        restCompanyBillPaymentItemMockMvc.perform(put("/api/company-bill-payment-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyBillPaymentItem)))
            .andExpect(status().isOk());

        // Validate the CompanyBillPaymentItem in the database
        List<CompanyBillPaymentItem> companyBillPaymentItemList = companyBillPaymentItemRepository.findAll();
        assertThat(companyBillPaymentItemList).hasSize(databaseSizeBeforeUpdate);
        CompanyBillPaymentItem testCompanyBillPaymentItem = companyBillPaymentItemList.get(companyBillPaymentItemList.size() - 1);
        assertThat(testCompanyBillPaymentItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testCompanyBillPaymentItem.getDiscountRate()).isEqualTo(UPDATED_DISCOUNT_RATE);
        assertThat(testCompanyBillPaymentItem.getTotalHT()).isEqualTo(UPDATED_TOTAL_HT);
        assertThat(testCompanyBillPaymentItem.getTotalTVA()).isEqualTo(UPDATED_TOTAL_TVA);
        assertThat(testCompanyBillPaymentItem.getTotaTTC()).isEqualTo(UPDATED_TOTA_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyBillPaymentItem() throws Exception {
        int databaseSizeBeforeUpdate = companyBillPaymentItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyBillPaymentItemMockMvc.perform(put("/api/company-bill-payment-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyBillPaymentItem)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyBillPaymentItem in the database
        List<CompanyBillPaymentItem> companyBillPaymentItemList = companyBillPaymentItemRepository.findAll();
        assertThat(companyBillPaymentItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyBillPaymentItem() throws Exception {
        // Initialize the database
        companyBillPaymentItemService.save(companyBillPaymentItem);

        int databaseSizeBeforeDelete = companyBillPaymentItemRepository.findAll().size();

        // Delete the companyBillPaymentItem
        restCompanyBillPaymentItemMockMvc.perform(delete("/api/company-bill-payment-items/{id}", companyBillPaymentItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyBillPaymentItem> companyBillPaymentItemList = companyBillPaymentItemRepository.findAll();
        assertThat(companyBillPaymentItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
