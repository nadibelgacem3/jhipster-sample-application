package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.MooincompaniesApp;
import org.pikasoft.mooin.mooincompanies.domain.TransactionComp;
import org.pikasoft.mooin.mooincompanies.repository.TransactionCompRepository;
import org.pikasoft.mooin.mooincompanies.service.TransactionCompService;

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

import org.pikasoft.mooin.mooincompanies.domain.enumeration.TransactionCompTypeEnum;
import org.pikasoft.mooin.mooincompanies.domain.enumeration.TransactionCompPaymentMethod;
/**
 * Integration tests for the {@link TransactionCompResource} REST controller.
 */
@SpringBootTest(classes = MooincompaniesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransactionCompResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final TransactionCompTypeEnum DEFAULT_TYPE = TransactionCompTypeEnum.DEPENSE;
    private static final TransactionCompTypeEnum UPDATED_TYPE = TransactionCompTypeEnum.RECETTE;

    private static final TransactionCompPaymentMethod DEFAULT_PAYMENT_METHOD = TransactionCompPaymentMethod.CREDIT_CARD;
    private static final TransactionCompPaymentMethod UPDATED_PAYMENT_METHOD = TransactionCompPaymentMethod.CASH;

    private static final Boolean DEFAULT_WITH_TVA = false;
    private static final Boolean UPDATED_WITH_TVA = true;

    private static final BigDecimal DEFAULT_TOTAL_HT = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_HT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TOTAL_TVA = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_TVA = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TOTA_TTC = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTA_TTC = new BigDecimal(1);

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TransactionCompRepository transactionCompRepository;

    @Autowired
    private TransactionCompService transactionCompService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionCompMockMvc;

    private TransactionComp transactionComp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionComp createEntity(EntityManager em) {
        TransactionComp transactionComp = new TransactionComp()
            .number(DEFAULT_NUMBER)
            .details(DEFAULT_DETAILS)
            .type(DEFAULT_TYPE)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .withTVA(DEFAULT_WITH_TVA)
            .totalHT(DEFAULT_TOTAL_HT)
            .totalTVA(DEFAULT_TOTAL_TVA)
            .totaTTC(DEFAULT_TOTA_TTC)
            .date(DEFAULT_DATE);
        return transactionComp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionComp createUpdatedEntity(EntityManager em) {
        TransactionComp transactionComp = new TransactionComp()
            .number(UPDATED_NUMBER)
            .details(UPDATED_DETAILS)
            .type(UPDATED_TYPE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .withTVA(UPDATED_WITH_TVA)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC)
            .date(UPDATED_DATE);
        return transactionComp;
    }

    @BeforeEach
    public void initTest() {
        transactionComp = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionComp() throws Exception {
        int databaseSizeBeforeCreate = transactionCompRepository.findAll().size();
        // Create the TransactionComp
        restTransactionCompMockMvc.perform(post("/api/transaction-comps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionComp)))
            .andExpect(status().isCreated());

        // Validate the TransactionComp in the database
        List<TransactionComp> transactionCompList = transactionCompRepository.findAll();
        assertThat(transactionCompList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionComp testTransactionComp = transactionCompList.get(transactionCompList.size() - 1);
        assertThat(testTransactionComp.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testTransactionComp.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testTransactionComp.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTransactionComp.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testTransactionComp.isWithTVA()).isEqualTo(DEFAULT_WITH_TVA);
        assertThat(testTransactionComp.getTotalHT()).isEqualTo(DEFAULT_TOTAL_HT);
        assertThat(testTransactionComp.getTotalTVA()).isEqualTo(DEFAULT_TOTAL_TVA);
        assertThat(testTransactionComp.getTotaTTC()).isEqualTo(DEFAULT_TOTA_TTC);
        assertThat(testTransactionComp.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createTransactionCompWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionCompRepository.findAll().size();

        // Create the TransactionComp with an existing ID
        transactionComp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionCompMockMvc.perform(post("/api/transaction-comps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionComp)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionComp in the database
        List<TransactionComp> transactionCompList = transactionCompRepository.findAll();
        assertThat(transactionCompList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTotaTTCIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionCompRepository.findAll().size();
        // set the field null
        transactionComp.setTotaTTC(null);

        // Create the TransactionComp, which fails.


        restTransactionCompMockMvc.perform(post("/api/transaction-comps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionComp)))
            .andExpect(status().isBadRequest());

        List<TransactionComp> transactionCompList = transactionCompRepository.findAll();
        assertThat(transactionCompList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionCompRepository.findAll().size();
        // set the field null
        transactionComp.setDate(null);

        // Create the TransactionComp, which fails.


        restTransactionCompMockMvc.perform(post("/api/transaction-comps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionComp)))
            .andExpect(status().isBadRequest());

        List<TransactionComp> transactionCompList = transactionCompRepository.findAll();
        assertThat(transactionCompList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionComps() throws Exception {
        // Initialize the database
        transactionCompRepository.saveAndFlush(transactionComp);

        // Get all the transactionCompList
        restTransactionCompMockMvc.perform(get("/api/transaction-comps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionComp.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].withTVA").value(hasItem(DEFAULT_WITH_TVA.booleanValue())))
            .andExpect(jsonPath("$.[*].totalHT").value(hasItem(DEFAULT_TOTAL_HT.intValue())))
            .andExpect(jsonPath("$.[*].totalTVA").value(hasItem(DEFAULT_TOTAL_TVA.intValue())))
            .andExpect(jsonPath("$.[*].totaTTC").value(hasItem(DEFAULT_TOTA_TTC.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTransactionComp() throws Exception {
        // Initialize the database
        transactionCompRepository.saveAndFlush(transactionComp);

        // Get the transactionComp
        restTransactionCompMockMvc.perform(get("/api/transaction-comps/{id}", transactionComp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transactionComp.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD.toString()))
            .andExpect(jsonPath("$.withTVA").value(DEFAULT_WITH_TVA.booleanValue()))
            .andExpect(jsonPath("$.totalHT").value(DEFAULT_TOTAL_HT.intValue()))
            .andExpect(jsonPath("$.totalTVA").value(DEFAULT_TOTAL_TVA.intValue()))
            .andExpect(jsonPath("$.totaTTC").value(DEFAULT_TOTA_TTC.intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTransactionComp() throws Exception {
        // Get the transactionComp
        restTransactionCompMockMvc.perform(get("/api/transaction-comps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionComp() throws Exception {
        // Initialize the database
        transactionCompService.save(transactionComp);

        int databaseSizeBeforeUpdate = transactionCompRepository.findAll().size();

        // Update the transactionComp
        TransactionComp updatedTransactionComp = transactionCompRepository.findById(transactionComp.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionComp are not directly saved in db
        em.detach(updatedTransactionComp);
        updatedTransactionComp
            .number(UPDATED_NUMBER)
            .details(UPDATED_DETAILS)
            .type(UPDATED_TYPE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .withTVA(UPDATED_WITH_TVA)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC)
            .date(UPDATED_DATE);

        restTransactionCompMockMvc.perform(put("/api/transaction-comps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransactionComp)))
            .andExpect(status().isOk());

        // Validate the TransactionComp in the database
        List<TransactionComp> transactionCompList = transactionCompRepository.findAll();
        assertThat(transactionCompList).hasSize(databaseSizeBeforeUpdate);
        TransactionComp testTransactionComp = transactionCompList.get(transactionCompList.size() - 1);
        assertThat(testTransactionComp.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testTransactionComp.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testTransactionComp.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTransactionComp.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testTransactionComp.isWithTVA()).isEqualTo(UPDATED_WITH_TVA);
        assertThat(testTransactionComp.getTotalHT()).isEqualTo(UPDATED_TOTAL_HT);
        assertThat(testTransactionComp.getTotalTVA()).isEqualTo(UPDATED_TOTAL_TVA);
        assertThat(testTransactionComp.getTotaTTC()).isEqualTo(UPDATED_TOTA_TTC);
        assertThat(testTransactionComp.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionComp() throws Exception {
        int databaseSizeBeforeUpdate = transactionCompRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionCompMockMvc.perform(put("/api/transaction-comps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionComp)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionComp in the database
        List<TransactionComp> transactionCompList = transactionCompRepository.findAll();
        assertThat(transactionCompList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionComp() throws Exception {
        // Initialize the database
        transactionCompService.save(transactionComp);

        int databaseSizeBeforeDelete = transactionCompRepository.findAll().size();

        // Delete the transactionComp
        restTransactionCompMockMvc.perform(delete("/api/transaction-comps/{id}", transactionComp.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionComp> transactionCompList = transactionCompRepository.findAll();
        assertThat(transactionCompList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
