package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.MooincompaniesApp;
import org.pikasoft.mooin.mooincompanies.domain.CompanyBillPayment;
import org.pikasoft.mooin.mooincompanies.repository.CompanyBillPaymentRepository;
import org.pikasoft.mooin.mooincompanies.service.CompanyBillPaymentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.pikasoft.mooin.mooincompanies.domain.enumeration.CompanyPaymentMethod;
import org.pikasoft.mooin.mooincompanies.domain.enumeration.CompanyModulePaymentStatus;
/**
 * Integration tests for the {@link CompanyBillPaymentResource} REST controller.
 */
@SpringBootTest(classes = MooincompaniesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompanyBillPaymentResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final CompanyPaymentMethod DEFAULT_PAYMENT_METHOD = CompanyPaymentMethod.CREDIT_CARD;
    private static final CompanyPaymentMethod UPDATED_PAYMENT_METHOD = CompanyPaymentMethod.CASH;

    private static final String DEFAULT_BANK_CHECKOR_TRAIT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BANK_CHECKOR_TRAIT_NUMBER = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE_JUSTIF = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_JUSTIF = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_JUSTIF_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_JUSTIF_CONTENT_TYPE = "image/png";

    private static final CompanyModulePaymentStatus DEFAULT_STATUS = CompanyModulePaymentStatus.DRAFT_PANIER;
    private static final CompanyModulePaymentStatus UPDATED_STATUS = CompanyModulePaymentStatus.CONFIRMED;

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

    private static final Boolean DEFAULT_WITH_TVA = false;
    private static final Boolean UPDATED_WITH_TVA = true;

    private static final Boolean DEFAULT_WITH_TAX = false;
    private static final Boolean UPDATED_WITH_TAX = true;

    @Autowired
    private CompanyBillPaymentRepository companyBillPaymentRepository;

    @Autowired
    private CompanyBillPaymentService companyBillPaymentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyBillPaymentMockMvc;

    private CompanyBillPayment companyBillPayment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyBillPayment createEntity(EntityManager em) {
        CompanyBillPayment companyBillPayment = new CompanyBillPayment()
            .number(DEFAULT_NUMBER)
            .details(DEFAULT_DETAILS)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .bankCheckorTraitNumber(DEFAULT_BANK_CHECKOR_TRAIT_NUMBER)
            .imageJustif(DEFAULT_IMAGE_JUSTIF)
            .imageJustifContentType(DEFAULT_IMAGE_JUSTIF_CONTENT_TYPE)
            .status(DEFAULT_STATUS)
            .totalHT(DEFAULT_TOTAL_HT)
            .totalTVA(DEFAULT_TOTAL_TVA)
            .totaTTC(DEFAULT_TOTA_TTC)
            .date(DEFAULT_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .withTVA(DEFAULT_WITH_TVA)
            .withTax(DEFAULT_WITH_TAX);
        return companyBillPayment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyBillPayment createUpdatedEntity(EntityManager em) {
        CompanyBillPayment companyBillPayment = new CompanyBillPayment()
            .number(UPDATED_NUMBER)
            .details(UPDATED_DETAILS)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .bankCheckorTraitNumber(UPDATED_BANK_CHECKOR_TRAIT_NUMBER)
            .imageJustif(UPDATED_IMAGE_JUSTIF)
            .imageJustifContentType(UPDATED_IMAGE_JUSTIF_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC)
            .date(UPDATED_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .withTVA(UPDATED_WITH_TVA)
            .withTax(UPDATED_WITH_TAX);
        return companyBillPayment;
    }

    @BeforeEach
    public void initTest() {
        companyBillPayment = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyBillPayment() throws Exception {
        int databaseSizeBeforeCreate = companyBillPaymentRepository.findAll().size();
        // Create the CompanyBillPayment
        restCompanyBillPaymentMockMvc.perform(post("/api/company-bill-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyBillPayment)))
            .andExpect(status().isCreated());

        // Validate the CompanyBillPayment in the database
        List<CompanyBillPayment> companyBillPaymentList = companyBillPaymentRepository.findAll();
        assertThat(companyBillPaymentList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyBillPayment testCompanyBillPayment = companyBillPaymentList.get(companyBillPaymentList.size() - 1);
        assertThat(testCompanyBillPayment.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCompanyBillPayment.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testCompanyBillPayment.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testCompanyBillPayment.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testCompanyBillPayment.getBankCheckorTraitNumber()).isEqualTo(DEFAULT_BANK_CHECKOR_TRAIT_NUMBER);
        assertThat(testCompanyBillPayment.getImageJustif()).isEqualTo(DEFAULT_IMAGE_JUSTIF);
        assertThat(testCompanyBillPayment.getImageJustifContentType()).isEqualTo(DEFAULT_IMAGE_JUSTIF_CONTENT_TYPE);
        assertThat(testCompanyBillPayment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCompanyBillPayment.getTotalHT()).isEqualTo(DEFAULT_TOTAL_HT);
        assertThat(testCompanyBillPayment.getTotalTVA()).isEqualTo(DEFAULT_TOTAL_TVA);
        assertThat(testCompanyBillPayment.getTotaTTC()).isEqualTo(DEFAULT_TOTA_TTC);
        assertThat(testCompanyBillPayment.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCompanyBillPayment.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testCompanyBillPayment.isWithTVA()).isEqualTo(DEFAULT_WITH_TVA);
        assertThat(testCompanyBillPayment.isWithTax()).isEqualTo(DEFAULT_WITH_TAX);
    }

    @Test
    @Transactional
    public void createCompanyBillPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyBillPaymentRepository.findAll().size();

        // Create the CompanyBillPayment with an existing ID
        companyBillPayment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyBillPaymentMockMvc.perform(post("/api/company-bill-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyBillPayment)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyBillPayment in the database
        List<CompanyBillPayment> companyBillPaymentList = companyBillPaymentRepository.findAll();
        assertThat(companyBillPaymentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTotalHTIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyBillPaymentRepository.findAll().size();
        // set the field null
        companyBillPayment.setTotalHT(null);

        // Create the CompanyBillPayment, which fails.


        restCompanyBillPaymentMockMvc.perform(post("/api/company-bill-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyBillPayment)))
            .andExpect(status().isBadRequest());

        List<CompanyBillPayment> companyBillPaymentList = companyBillPaymentRepository.findAll();
        assertThat(companyBillPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalTVAIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyBillPaymentRepository.findAll().size();
        // set the field null
        companyBillPayment.setTotalTVA(null);

        // Create the CompanyBillPayment, which fails.


        restCompanyBillPaymentMockMvc.perform(post("/api/company-bill-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyBillPayment)))
            .andExpect(status().isBadRequest());

        List<CompanyBillPayment> companyBillPaymentList = companyBillPaymentRepository.findAll();
        assertThat(companyBillPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotaTTCIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyBillPaymentRepository.findAll().size();
        // set the field null
        companyBillPayment.setTotaTTC(null);

        // Create the CompanyBillPayment, which fails.


        restCompanyBillPaymentMockMvc.perform(post("/api/company-bill-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyBillPayment)))
            .andExpect(status().isBadRequest());

        List<CompanyBillPayment> companyBillPaymentList = companyBillPaymentRepository.findAll();
        assertThat(companyBillPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyBillPaymentRepository.findAll().size();
        // set the field null
        companyBillPayment.setDate(null);

        // Create the CompanyBillPayment, which fails.


        restCompanyBillPaymentMockMvc.perform(post("/api/company-bill-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyBillPayment)))
            .andExpect(status().isBadRequest());

        List<CompanyBillPayment> companyBillPaymentList = companyBillPaymentRepository.findAll();
        assertThat(companyBillPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanyBillPayments() throws Exception {
        // Initialize the database
        companyBillPaymentRepository.saveAndFlush(companyBillPayment);

        // Get all the companyBillPaymentList
        restCompanyBillPaymentMockMvc.perform(get("/api/company-bill-payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyBillPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].bankCheckorTraitNumber").value(hasItem(DEFAULT_BANK_CHECKOR_TRAIT_NUMBER)))
            .andExpect(jsonPath("$.[*].imageJustifContentType").value(hasItem(DEFAULT_IMAGE_JUSTIF_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageJustif").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_JUSTIF))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].totalHT").value(hasItem(DEFAULT_TOTAL_HT.intValue())))
            .andExpect(jsonPath("$.[*].totalTVA").value(hasItem(DEFAULT_TOTAL_TVA.intValue())))
            .andExpect(jsonPath("$.[*].totaTTC").value(hasItem(DEFAULT_TOTA_TTC.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].withTVA").value(hasItem(DEFAULT_WITH_TVA.booleanValue())))
            .andExpect(jsonPath("$.[*].withTax").value(hasItem(DEFAULT_WITH_TAX.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCompanyBillPayment() throws Exception {
        // Initialize the database
        companyBillPaymentRepository.saveAndFlush(companyBillPayment);

        // Get the companyBillPayment
        restCompanyBillPaymentMockMvc.perform(get("/api/company-bill-payments/{id}", companyBillPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyBillPayment.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD.toString()))
            .andExpect(jsonPath("$.bankCheckorTraitNumber").value(DEFAULT_BANK_CHECKOR_TRAIT_NUMBER))
            .andExpect(jsonPath("$.imageJustifContentType").value(DEFAULT_IMAGE_JUSTIF_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageJustif").value(Base64Utils.encodeToString(DEFAULT_IMAGE_JUSTIF)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.totalHT").value(DEFAULT_TOTAL_HT.intValue()))
            .andExpect(jsonPath("$.totalTVA").value(DEFAULT_TOTAL_TVA.intValue()))
            .andExpect(jsonPath("$.totaTTC").value(DEFAULT_TOTA_TTC.intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.withTVA").value(DEFAULT_WITH_TVA.booleanValue()))
            .andExpect(jsonPath("$.withTax").value(DEFAULT_WITH_TAX.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCompanyBillPayment() throws Exception {
        // Get the companyBillPayment
        restCompanyBillPaymentMockMvc.perform(get("/api/company-bill-payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyBillPayment() throws Exception {
        // Initialize the database
        companyBillPaymentService.save(companyBillPayment);

        int databaseSizeBeforeUpdate = companyBillPaymentRepository.findAll().size();

        // Update the companyBillPayment
        CompanyBillPayment updatedCompanyBillPayment = companyBillPaymentRepository.findById(companyBillPayment.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyBillPayment are not directly saved in db
        em.detach(updatedCompanyBillPayment);
        updatedCompanyBillPayment
            .number(UPDATED_NUMBER)
            .details(UPDATED_DETAILS)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .bankCheckorTraitNumber(UPDATED_BANK_CHECKOR_TRAIT_NUMBER)
            .imageJustif(UPDATED_IMAGE_JUSTIF)
            .imageJustifContentType(UPDATED_IMAGE_JUSTIF_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC)
            .date(UPDATED_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .withTVA(UPDATED_WITH_TVA)
            .withTax(UPDATED_WITH_TAX);

        restCompanyBillPaymentMockMvc.perform(put("/api/company-bill-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyBillPayment)))
            .andExpect(status().isOk());

        // Validate the CompanyBillPayment in the database
        List<CompanyBillPayment> companyBillPaymentList = companyBillPaymentRepository.findAll();
        assertThat(companyBillPaymentList).hasSize(databaseSizeBeforeUpdate);
        CompanyBillPayment testCompanyBillPayment = companyBillPaymentList.get(companyBillPaymentList.size() - 1);
        assertThat(testCompanyBillPayment.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCompanyBillPayment.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testCompanyBillPayment.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testCompanyBillPayment.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testCompanyBillPayment.getBankCheckorTraitNumber()).isEqualTo(UPDATED_BANK_CHECKOR_TRAIT_NUMBER);
        assertThat(testCompanyBillPayment.getImageJustif()).isEqualTo(UPDATED_IMAGE_JUSTIF);
        assertThat(testCompanyBillPayment.getImageJustifContentType()).isEqualTo(UPDATED_IMAGE_JUSTIF_CONTENT_TYPE);
        assertThat(testCompanyBillPayment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCompanyBillPayment.getTotalHT()).isEqualTo(UPDATED_TOTAL_HT);
        assertThat(testCompanyBillPayment.getTotalTVA()).isEqualTo(UPDATED_TOTAL_TVA);
        assertThat(testCompanyBillPayment.getTotaTTC()).isEqualTo(UPDATED_TOTA_TTC);
        assertThat(testCompanyBillPayment.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCompanyBillPayment.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testCompanyBillPayment.isWithTVA()).isEqualTo(UPDATED_WITH_TVA);
        assertThat(testCompanyBillPayment.isWithTax()).isEqualTo(UPDATED_WITH_TAX);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyBillPayment() throws Exception {
        int databaseSizeBeforeUpdate = companyBillPaymentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyBillPaymentMockMvc.perform(put("/api/company-bill-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyBillPayment)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyBillPayment in the database
        List<CompanyBillPayment> companyBillPaymentList = companyBillPaymentRepository.findAll();
        assertThat(companyBillPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyBillPayment() throws Exception {
        // Initialize the database
        companyBillPaymentService.save(companyBillPayment);

        int databaseSizeBeforeDelete = companyBillPaymentRepository.findAll().size();

        // Delete the companyBillPayment
        restCompanyBillPaymentMockMvc.perform(delete("/api/company-bill-payments/{id}", companyBillPayment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyBillPayment> companyBillPaymentList = companyBillPaymentRepository.findAll();
        assertThat(companyBillPaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
