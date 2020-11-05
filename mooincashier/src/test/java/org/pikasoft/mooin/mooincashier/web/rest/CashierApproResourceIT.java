package org.pikasoft.mooin.mooincashier.web.rest;

import org.pikasoft.mooin.mooincashier.MooincashierApp;
import org.pikasoft.mooin.mooincashier.domain.CashierAppro;
import org.pikasoft.mooin.mooincashier.repository.CashierApproRepository;
import org.pikasoft.mooin.mooincashier.service.CashierApproService;

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

import org.pikasoft.mooin.mooincashier.domain.enumeration.CashierApproStatusEnum;
/**
 * Integration tests for the {@link CashierApproResource} REST controller.
 */
@SpringBootTest(classes = MooincashierApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CashierApproResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final CashierApproStatusEnum DEFAULT_STATUS = CashierApproStatusEnum.DRAFT;
    private static final CashierApproStatusEnum UPDATED_STATUS = CashierApproStatusEnum.VALIDATED;

    private static final BigDecimal DEFAULT_TOTAL_HT = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_HT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TOTAL_TVA = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_TVA = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TOTA_TTC = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTA_TTC = new BigDecimal(1);

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_CONVERTED = false;
    private static final Boolean UPDATED_IS_CONVERTED = true;

    private static final Boolean DEFAULT_WITH_TVA = false;
    private static final Boolean UPDATED_WITH_TVA = true;

    private static final Boolean DEFAULT_WITH_TAX = false;
    private static final Boolean UPDATED_WITH_TAX = true;

    @Autowired
    private CashierApproRepository cashierApproRepository;

    @Autowired
    private CashierApproService cashierApproService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCashierApproMockMvc;

    private CashierAppro cashierAppro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CashierAppro createEntity(EntityManager em) {
        CashierAppro cashierAppro = new CashierAppro()
            .number(DEFAULT_NUMBER)
            .status(DEFAULT_STATUS)
            .totalHT(DEFAULT_TOTAL_HT)
            .totalTVA(DEFAULT_TOTAL_TVA)
            .totaTTC(DEFAULT_TOTA_TTC)
            .date(DEFAULT_DATE)
            .isConverted(DEFAULT_IS_CONVERTED)
            .withTVA(DEFAULT_WITH_TVA)
            .withTax(DEFAULT_WITH_TAX);
        return cashierAppro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CashierAppro createUpdatedEntity(EntityManager em) {
        CashierAppro cashierAppro = new CashierAppro()
            .number(UPDATED_NUMBER)
            .status(UPDATED_STATUS)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC)
            .date(UPDATED_DATE)
            .isConverted(UPDATED_IS_CONVERTED)
            .withTVA(UPDATED_WITH_TVA)
            .withTax(UPDATED_WITH_TAX);
        return cashierAppro;
    }

    @BeforeEach
    public void initTest() {
        cashierAppro = createEntity(em);
    }

    @Test
    @Transactional
    public void createCashierAppro() throws Exception {
        int databaseSizeBeforeCreate = cashierApproRepository.findAll().size();
        // Create the CashierAppro
        restCashierApproMockMvc.perform(post("/api/cashier-appros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierAppro)))
            .andExpect(status().isCreated());

        // Validate the CashierAppro in the database
        List<CashierAppro> cashierApproList = cashierApproRepository.findAll();
        assertThat(cashierApproList).hasSize(databaseSizeBeforeCreate + 1);
        CashierAppro testCashierAppro = cashierApproList.get(cashierApproList.size() - 1);
        assertThat(testCashierAppro.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCashierAppro.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCashierAppro.getTotalHT()).isEqualTo(DEFAULT_TOTAL_HT);
        assertThat(testCashierAppro.getTotalTVA()).isEqualTo(DEFAULT_TOTAL_TVA);
        assertThat(testCashierAppro.getTotaTTC()).isEqualTo(DEFAULT_TOTA_TTC);
        assertThat(testCashierAppro.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCashierAppro.isIsConverted()).isEqualTo(DEFAULT_IS_CONVERTED);
        assertThat(testCashierAppro.isWithTVA()).isEqualTo(DEFAULT_WITH_TVA);
        assertThat(testCashierAppro.isWithTax()).isEqualTo(DEFAULT_WITH_TAX);
    }

    @Test
    @Transactional
    public void createCashierApproWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cashierApproRepository.findAll().size();

        // Create the CashierAppro with an existing ID
        cashierAppro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCashierApproMockMvc.perform(post("/api/cashier-appros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierAppro)))
            .andExpect(status().isBadRequest());

        // Validate the CashierAppro in the database
        List<CashierAppro> cashierApproList = cashierApproRepository.findAll();
        assertThat(cashierApproList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTotalHTIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashierApproRepository.findAll().size();
        // set the field null
        cashierAppro.setTotalHT(null);

        // Create the CashierAppro, which fails.


        restCashierApproMockMvc.perform(post("/api/cashier-appros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierAppro)))
            .andExpect(status().isBadRequest());

        List<CashierAppro> cashierApproList = cashierApproRepository.findAll();
        assertThat(cashierApproList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalTVAIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashierApproRepository.findAll().size();
        // set the field null
        cashierAppro.setTotalTVA(null);

        // Create the CashierAppro, which fails.


        restCashierApproMockMvc.perform(post("/api/cashier-appros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierAppro)))
            .andExpect(status().isBadRequest());

        List<CashierAppro> cashierApproList = cashierApproRepository.findAll();
        assertThat(cashierApproList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotaTTCIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashierApproRepository.findAll().size();
        // set the field null
        cashierAppro.setTotaTTC(null);

        // Create the CashierAppro, which fails.


        restCashierApproMockMvc.perform(post("/api/cashier-appros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierAppro)))
            .andExpect(status().isBadRequest());

        List<CashierAppro> cashierApproList = cashierApproRepository.findAll();
        assertThat(cashierApproList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashierApproRepository.findAll().size();
        // set the field null
        cashierAppro.setDate(null);

        // Create the CashierAppro, which fails.


        restCashierApproMockMvc.perform(post("/api/cashier-appros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierAppro)))
            .andExpect(status().isBadRequest());

        List<CashierAppro> cashierApproList = cashierApproRepository.findAll();
        assertThat(cashierApproList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCashierAppros() throws Exception {
        // Initialize the database
        cashierApproRepository.saveAndFlush(cashierAppro);

        // Get all the cashierApproList
        restCashierApproMockMvc.perform(get("/api/cashier-appros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cashierAppro.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].totalHT").value(hasItem(DEFAULT_TOTAL_HT.intValue())))
            .andExpect(jsonPath("$.[*].totalTVA").value(hasItem(DEFAULT_TOTAL_TVA.intValue())))
            .andExpect(jsonPath("$.[*].totaTTC").value(hasItem(DEFAULT_TOTA_TTC.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].isConverted").value(hasItem(DEFAULT_IS_CONVERTED.booleanValue())))
            .andExpect(jsonPath("$.[*].withTVA").value(hasItem(DEFAULT_WITH_TVA.booleanValue())))
            .andExpect(jsonPath("$.[*].withTax").value(hasItem(DEFAULT_WITH_TAX.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCashierAppro() throws Exception {
        // Initialize the database
        cashierApproRepository.saveAndFlush(cashierAppro);

        // Get the cashierAppro
        restCashierApproMockMvc.perform(get("/api/cashier-appros/{id}", cashierAppro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cashierAppro.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.totalHT").value(DEFAULT_TOTAL_HT.intValue()))
            .andExpect(jsonPath("$.totalTVA").value(DEFAULT_TOTAL_TVA.intValue()))
            .andExpect(jsonPath("$.totaTTC").value(DEFAULT_TOTA_TTC.intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.isConverted").value(DEFAULT_IS_CONVERTED.booleanValue()))
            .andExpect(jsonPath("$.withTVA").value(DEFAULT_WITH_TVA.booleanValue()))
            .andExpect(jsonPath("$.withTax").value(DEFAULT_WITH_TAX.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCashierAppro() throws Exception {
        // Get the cashierAppro
        restCashierApproMockMvc.perform(get("/api/cashier-appros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCashierAppro() throws Exception {
        // Initialize the database
        cashierApproService.save(cashierAppro);

        int databaseSizeBeforeUpdate = cashierApproRepository.findAll().size();

        // Update the cashierAppro
        CashierAppro updatedCashierAppro = cashierApproRepository.findById(cashierAppro.getId()).get();
        // Disconnect from session so that the updates on updatedCashierAppro are not directly saved in db
        em.detach(updatedCashierAppro);
        updatedCashierAppro
            .number(UPDATED_NUMBER)
            .status(UPDATED_STATUS)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC)
            .date(UPDATED_DATE)
            .isConverted(UPDATED_IS_CONVERTED)
            .withTVA(UPDATED_WITH_TVA)
            .withTax(UPDATED_WITH_TAX);

        restCashierApproMockMvc.perform(put("/api/cashier-appros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCashierAppro)))
            .andExpect(status().isOk());

        // Validate the CashierAppro in the database
        List<CashierAppro> cashierApproList = cashierApproRepository.findAll();
        assertThat(cashierApproList).hasSize(databaseSizeBeforeUpdate);
        CashierAppro testCashierAppro = cashierApproList.get(cashierApproList.size() - 1);
        assertThat(testCashierAppro.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCashierAppro.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCashierAppro.getTotalHT()).isEqualTo(UPDATED_TOTAL_HT);
        assertThat(testCashierAppro.getTotalTVA()).isEqualTo(UPDATED_TOTAL_TVA);
        assertThat(testCashierAppro.getTotaTTC()).isEqualTo(UPDATED_TOTA_TTC);
        assertThat(testCashierAppro.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCashierAppro.isIsConverted()).isEqualTo(UPDATED_IS_CONVERTED);
        assertThat(testCashierAppro.isWithTVA()).isEqualTo(UPDATED_WITH_TVA);
        assertThat(testCashierAppro.isWithTax()).isEqualTo(UPDATED_WITH_TAX);
    }

    @Test
    @Transactional
    public void updateNonExistingCashierAppro() throws Exception {
        int databaseSizeBeforeUpdate = cashierApproRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCashierApproMockMvc.perform(put("/api/cashier-appros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierAppro)))
            .andExpect(status().isBadRequest());

        // Validate the CashierAppro in the database
        List<CashierAppro> cashierApproList = cashierApproRepository.findAll();
        assertThat(cashierApproList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCashierAppro() throws Exception {
        // Initialize the database
        cashierApproService.save(cashierAppro);

        int databaseSizeBeforeDelete = cashierApproRepository.findAll().size();

        // Delete the cashierAppro
        restCashierApproMockMvc.perform(delete("/api/cashier-appros/{id}", cashierAppro.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CashierAppro> cashierApproList = cashierApproRepository.findAll();
        assertThat(cashierApproList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
