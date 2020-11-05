package org.pikasoft.mooin.mooincashier.web.rest;

import org.pikasoft.mooin.mooincashier.MooincashierApp;
import org.pikasoft.mooin.mooincashier.domain.CashierReceipt;
import org.pikasoft.mooin.mooincashier.repository.CashierReceiptRepository;
import org.pikasoft.mooin.mooincashier.service.CashierReceiptService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.pikasoft.mooin.mooincashier.domain.enumeration.CashierReceiptStatusEnum;
/**
 * Integration tests for the {@link CashierReceiptResource} REST controller.
 */
@SpringBootTest(classes = MooincashierApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CashierReceiptResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final CashierReceiptStatusEnum DEFAULT_STATUS = CashierReceiptStatusEnum.DRAFT;
    private static final CashierReceiptStatusEnum UPDATED_STATUS = CashierReceiptStatusEnum.FINALIZED;

    private static final BigDecimal DEFAULT_TOTAL_HT = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_HT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TOTAL_TVA = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_TVA = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TOTA_TTC = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTA_TTC = new BigDecimal(1);

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_CONVERTED = false;
    private static final Boolean UPDATED_IS_CONVERTED = true;

    private static final Boolean DEFAULT_WITH_TVA = false;
    private static final Boolean UPDATED_WITH_TVA = true;

    private static final Boolean DEFAULT_WITH_TAX = false;
    private static final Boolean UPDATED_WITH_TAX = true;

    @Autowired
    private CashierReceiptRepository cashierReceiptRepository;

    @Autowired
    private CashierReceiptService cashierReceiptService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCashierReceiptMockMvc;

    private CashierReceipt cashierReceipt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CashierReceipt createEntity(EntityManager em) {
        CashierReceipt cashierReceipt = new CashierReceipt()
            .number(DEFAULT_NUMBER)
            .reference(DEFAULT_REFERENCE)
            .status(DEFAULT_STATUS)
            .totalHT(DEFAULT_TOTAL_HT)
            .totalTVA(DEFAULT_TOTAL_TVA)
            .totaTTC(DEFAULT_TOTA_TTC)
            .date(DEFAULT_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .isConverted(DEFAULT_IS_CONVERTED)
            .withTVA(DEFAULT_WITH_TVA)
            .withTax(DEFAULT_WITH_TAX);
        return cashierReceipt;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CashierReceipt createUpdatedEntity(EntityManager em) {
        CashierReceipt cashierReceipt = new CashierReceipt()
            .number(UPDATED_NUMBER)
            .reference(UPDATED_REFERENCE)
            .status(UPDATED_STATUS)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC)
            .date(UPDATED_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .isConverted(UPDATED_IS_CONVERTED)
            .withTVA(UPDATED_WITH_TVA)
            .withTax(UPDATED_WITH_TAX);
        return cashierReceipt;
    }

    @BeforeEach
    public void initTest() {
        cashierReceipt = createEntity(em);
    }

    @Test
    @Transactional
    public void createCashierReceipt() throws Exception {
        int databaseSizeBeforeCreate = cashierReceiptRepository.findAll().size();
        // Create the CashierReceipt
        restCashierReceiptMockMvc.perform(post("/api/cashier-receipts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierReceipt)))
            .andExpect(status().isCreated());

        // Validate the CashierReceipt in the database
        List<CashierReceipt> cashierReceiptList = cashierReceiptRepository.findAll();
        assertThat(cashierReceiptList).hasSize(databaseSizeBeforeCreate + 1);
        CashierReceipt testCashierReceipt = cashierReceiptList.get(cashierReceiptList.size() - 1);
        assertThat(testCashierReceipt.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCashierReceipt.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testCashierReceipt.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCashierReceipt.getTotalHT()).isEqualTo(DEFAULT_TOTAL_HT);
        assertThat(testCashierReceipt.getTotalTVA()).isEqualTo(DEFAULT_TOTAL_TVA);
        assertThat(testCashierReceipt.getTotaTTC()).isEqualTo(DEFAULT_TOTA_TTC);
        assertThat(testCashierReceipt.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCashierReceipt.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testCashierReceipt.isIsConverted()).isEqualTo(DEFAULT_IS_CONVERTED);
        assertThat(testCashierReceipt.isWithTVA()).isEqualTo(DEFAULT_WITH_TVA);
        assertThat(testCashierReceipt.isWithTax()).isEqualTo(DEFAULT_WITH_TAX);
    }

    @Test
    @Transactional
    public void createCashierReceiptWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cashierReceiptRepository.findAll().size();

        // Create the CashierReceipt with an existing ID
        cashierReceipt.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCashierReceiptMockMvc.perform(post("/api/cashier-receipts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierReceipt)))
            .andExpect(status().isBadRequest());

        // Validate the CashierReceipt in the database
        List<CashierReceipt> cashierReceiptList = cashierReceiptRepository.findAll();
        assertThat(cashierReceiptList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTotalHTIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashierReceiptRepository.findAll().size();
        // set the field null
        cashierReceipt.setTotalHT(null);

        // Create the CashierReceipt, which fails.


        restCashierReceiptMockMvc.perform(post("/api/cashier-receipts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierReceipt)))
            .andExpect(status().isBadRequest());

        List<CashierReceipt> cashierReceiptList = cashierReceiptRepository.findAll();
        assertThat(cashierReceiptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalTVAIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashierReceiptRepository.findAll().size();
        // set the field null
        cashierReceipt.setTotalTVA(null);

        // Create the CashierReceipt, which fails.


        restCashierReceiptMockMvc.perform(post("/api/cashier-receipts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierReceipt)))
            .andExpect(status().isBadRequest());

        List<CashierReceipt> cashierReceiptList = cashierReceiptRepository.findAll();
        assertThat(cashierReceiptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotaTTCIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashierReceiptRepository.findAll().size();
        // set the field null
        cashierReceipt.setTotaTTC(null);

        // Create the CashierReceipt, which fails.


        restCashierReceiptMockMvc.perform(post("/api/cashier-receipts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierReceipt)))
            .andExpect(status().isBadRequest());

        List<CashierReceipt> cashierReceiptList = cashierReceiptRepository.findAll();
        assertThat(cashierReceiptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashierReceiptRepository.findAll().size();
        // set the field null
        cashierReceipt.setDate(null);

        // Create the CashierReceipt, which fails.


        restCashierReceiptMockMvc.perform(post("/api/cashier-receipts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierReceipt)))
            .andExpect(status().isBadRequest());

        List<CashierReceipt> cashierReceiptList = cashierReceiptRepository.findAll();
        assertThat(cashierReceiptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCashierReceipts() throws Exception {
        // Initialize the database
        cashierReceiptRepository.saveAndFlush(cashierReceipt);

        // Get all the cashierReceiptList
        restCashierReceiptMockMvc.perform(get("/api/cashier-receipts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cashierReceipt.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].totalHT").value(hasItem(DEFAULT_TOTAL_HT.intValue())))
            .andExpect(jsonPath("$.[*].totalTVA").value(hasItem(DEFAULT_TOTAL_TVA.intValue())))
            .andExpect(jsonPath("$.[*].totaTTC").value(hasItem(DEFAULT_TOTA_TTC.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].isConverted").value(hasItem(DEFAULT_IS_CONVERTED.booleanValue())))
            .andExpect(jsonPath("$.[*].withTVA").value(hasItem(DEFAULT_WITH_TVA.booleanValue())))
            .andExpect(jsonPath("$.[*].withTax").value(hasItem(DEFAULT_WITH_TAX.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCashierReceipt() throws Exception {
        // Initialize the database
        cashierReceiptRepository.saveAndFlush(cashierReceipt);

        // Get the cashierReceipt
        restCashierReceiptMockMvc.perform(get("/api/cashier-receipts/{id}", cashierReceipt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cashierReceipt.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.totalHT").value(DEFAULT_TOTAL_HT.intValue()))
            .andExpect(jsonPath("$.totalTVA").value(DEFAULT_TOTAL_TVA.intValue()))
            .andExpect(jsonPath("$.totaTTC").value(DEFAULT_TOTA_TTC.intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.isConverted").value(DEFAULT_IS_CONVERTED.booleanValue()))
            .andExpect(jsonPath("$.withTVA").value(DEFAULT_WITH_TVA.booleanValue()))
            .andExpect(jsonPath("$.withTax").value(DEFAULT_WITH_TAX.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCashierReceipt() throws Exception {
        // Get the cashierReceipt
        restCashierReceiptMockMvc.perform(get("/api/cashier-receipts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCashierReceipt() throws Exception {
        // Initialize the database
        cashierReceiptService.save(cashierReceipt);

        int databaseSizeBeforeUpdate = cashierReceiptRepository.findAll().size();

        // Update the cashierReceipt
        CashierReceipt updatedCashierReceipt = cashierReceiptRepository.findById(cashierReceipt.getId()).get();
        // Disconnect from session so that the updates on updatedCashierReceipt are not directly saved in db
        em.detach(updatedCashierReceipt);
        updatedCashierReceipt
            .number(UPDATED_NUMBER)
            .reference(UPDATED_REFERENCE)
            .status(UPDATED_STATUS)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC)
            .date(UPDATED_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .isConverted(UPDATED_IS_CONVERTED)
            .withTVA(UPDATED_WITH_TVA)
            .withTax(UPDATED_WITH_TAX);

        restCashierReceiptMockMvc.perform(put("/api/cashier-receipts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCashierReceipt)))
            .andExpect(status().isOk());

        // Validate the CashierReceipt in the database
        List<CashierReceipt> cashierReceiptList = cashierReceiptRepository.findAll();
        assertThat(cashierReceiptList).hasSize(databaseSizeBeforeUpdate);
        CashierReceipt testCashierReceipt = cashierReceiptList.get(cashierReceiptList.size() - 1);
        assertThat(testCashierReceipt.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCashierReceipt.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testCashierReceipt.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCashierReceipt.getTotalHT()).isEqualTo(UPDATED_TOTAL_HT);
        assertThat(testCashierReceipt.getTotalTVA()).isEqualTo(UPDATED_TOTAL_TVA);
        assertThat(testCashierReceipt.getTotaTTC()).isEqualTo(UPDATED_TOTA_TTC);
        assertThat(testCashierReceipt.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCashierReceipt.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testCashierReceipt.isIsConverted()).isEqualTo(UPDATED_IS_CONVERTED);
        assertThat(testCashierReceipt.isWithTVA()).isEqualTo(UPDATED_WITH_TVA);
        assertThat(testCashierReceipt.isWithTax()).isEqualTo(UPDATED_WITH_TAX);
    }

    @Test
    @Transactional
    public void updateNonExistingCashierReceipt() throws Exception {
        int databaseSizeBeforeUpdate = cashierReceiptRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCashierReceiptMockMvc.perform(put("/api/cashier-receipts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierReceipt)))
            .andExpect(status().isBadRequest());

        // Validate the CashierReceipt in the database
        List<CashierReceipt> cashierReceiptList = cashierReceiptRepository.findAll();
        assertThat(cashierReceiptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCashierReceipt() throws Exception {
        // Initialize the database
        cashierReceiptService.save(cashierReceipt);

        int databaseSizeBeforeDelete = cashierReceiptRepository.findAll().size();

        // Delete the cashierReceipt
        restCashierReceiptMockMvc.perform(delete("/api/cashier-receipts/{id}", cashierReceipt.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CashierReceipt> cashierReceiptList = cashierReceiptRepository.findAll();
        assertThat(cashierReceiptList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
