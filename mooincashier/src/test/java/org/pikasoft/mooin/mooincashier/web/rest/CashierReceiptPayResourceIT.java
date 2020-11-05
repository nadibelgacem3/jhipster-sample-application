package org.pikasoft.mooin.mooincashier.web.rest;

import org.pikasoft.mooin.mooincashier.MooincashierApp;
import org.pikasoft.mooin.mooincashier.domain.CashierReceiptPay;
import org.pikasoft.mooin.mooincashier.repository.CashierReceiptPayRepository;
import org.pikasoft.mooin.mooincashier.service.CashierReceiptPayService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.pikasoft.mooin.mooincashier.domain.enumeration.CashierReceiptPayMeth;
/**
 * Integration tests for the {@link CashierReceiptPayResource} REST controller.
 */
@SpringBootTest(classes = MooincashierApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CashierReceiptPayResourceIT {

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(1);

    private static final Instant DEFAULT_PAYMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAYMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final CashierReceiptPayMeth DEFAULT_PAYMENT_METHOD = CashierReceiptPayMeth.CREDIT_CARD;
    private static final CashierReceiptPayMeth UPDATED_PAYMENT_METHOD = CashierReceiptPayMeth.CASH;

    private static final String DEFAULT_BANK_CHECKOR_TRAIT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BANK_CHECKOR_TRAIT_NUMBER = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE_JUSTIF = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_JUSTIF = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_JUSTIF_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_JUSTIF_CONTENT_TYPE = "image/png";

    @Autowired
    private CashierReceiptPayRepository cashierReceiptPayRepository;

    @Autowired
    private CashierReceiptPayService cashierReceiptPayService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCashierReceiptPayMockMvc;

    private CashierReceiptPay cashierReceiptPay;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CashierReceiptPay createEntity(EntityManager em) {
        CashierReceiptPay cashierReceiptPay = new CashierReceiptPay()
            .details(DEFAULT_DETAILS)
            .amount(DEFAULT_AMOUNT)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .bankCheckorTraitNumber(DEFAULT_BANK_CHECKOR_TRAIT_NUMBER)
            .imageJustif(DEFAULT_IMAGE_JUSTIF)
            .imageJustifContentType(DEFAULT_IMAGE_JUSTIF_CONTENT_TYPE);
        return cashierReceiptPay;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CashierReceiptPay createUpdatedEntity(EntityManager em) {
        CashierReceiptPay cashierReceiptPay = new CashierReceiptPay()
            .details(UPDATED_DETAILS)
            .amount(UPDATED_AMOUNT)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .bankCheckorTraitNumber(UPDATED_BANK_CHECKOR_TRAIT_NUMBER)
            .imageJustif(UPDATED_IMAGE_JUSTIF)
            .imageJustifContentType(UPDATED_IMAGE_JUSTIF_CONTENT_TYPE);
        return cashierReceiptPay;
    }

    @BeforeEach
    public void initTest() {
        cashierReceiptPay = createEntity(em);
    }

    @Test
    @Transactional
    public void createCashierReceiptPay() throws Exception {
        int databaseSizeBeforeCreate = cashierReceiptPayRepository.findAll().size();
        // Create the CashierReceiptPay
        restCashierReceiptPayMockMvc.perform(post("/api/cashier-receipt-pays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierReceiptPay)))
            .andExpect(status().isCreated());

        // Validate the CashierReceiptPay in the database
        List<CashierReceiptPay> cashierReceiptPayList = cashierReceiptPayRepository.findAll();
        assertThat(cashierReceiptPayList).hasSize(databaseSizeBeforeCreate + 1);
        CashierReceiptPay testCashierReceiptPay = cashierReceiptPayList.get(cashierReceiptPayList.size() - 1);
        assertThat(testCashierReceiptPay.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testCashierReceiptPay.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testCashierReceiptPay.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testCashierReceiptPay.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testCashierReceiptPay.getBankCheckorTraitNumber()).isEqualTo(DEFAULT_BANK_CHECKOR_TRAIT_NUMBER);
        assertThat(testCashierReceiptPay.getImageJustif()).isEqualTo(DEFAULT_IMAGE_JUSTIF);
        assertThat(testCashierReceiptPay.getImageJustifContentType()).isEqualTo(DEFAULT_IMAGE_JUSTIF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createCashierReceiptPayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cashierReceiptPayRepository.findAll().size();

        // Create the CashierReceiptPay with an existing ID
        cashierReceiptPay.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCashierReceiptPayMockMvc.perform(post("/api/cashier-receipt-pays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierReceiptPay)))
            .andExpect(status().isBadRequest());

        // Validate the CashierReceiptPay in the database
        List<CashierReceiptPay> cashierReceiptPayList = cashierReceiptPayRepository.findAll();
        assertThat(cashierReceiptPayList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashierReceiptPayRepository.findAll().size();
        // set the field null
        cashierReceiptPay.setAmount(null);

        // Create the CashierReceiptPay, which fails.


        restCashierReceiptPayMockMvc.perform(post("/api/cashier-receipt-pays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierReceiptPay)))
            .andExpect(status().isBadRequest());

        List<CashierReceiptPay> cashierReceiptPayList = cashierReceiptPayRepository.findAll();
        assertThat(cashierReceiptPayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCashierReceiptPays() throws Exception {
        // Initialize the database
        cashierReceiptPayRepository.saveAndFlush(cashierReceiptPay);

        // Get all the cashierReceiptPayList
        restCashierReceiptPayMockMvc.perform(get("/api/cashier-receipt-pays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cashierReceiptPay.getId().intValue())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].bankCheckorTraitNumber").value(hasItem(DEFAULT_BANK_CHECKOR_TRAIT_NUMBER)))
            .andExpect(jsonPath("$.[*].imageJustifContentType").value(hasItem(DEFAULT_IMAGE_JUSTIF_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageJustif").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_JUSTIF))));
    }
    
    @Test
    @Transactional
    public void getCashierReceiptPay() throws Exception {
        // Initialize the database
        cashierReceiptPayRepository.saveAndFlush(cashierReceiptPay);

        // Get the cashierReceiptPay
        restCashierReceiptPayMockMvc.perform(get("/api/cashier-receipt-pays/{id}", cashierReceiptPay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cashierReceiptPay.getId().intValue()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD.toString()))
            .andExpect(jsonPath("$.bankCheckorTraitNumber").value(DEFAULT_BANK_CHECKOR_TRAIT_NUMBER))
            .andExpect(jsonPath("$.imageJustifContentType").value(DEFAULT_IMAGE_JUSTIF_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageJustif").value(Base64Utils.encodeToString(DEFAULT_IMAGE_JUSTIF)));
    }
    @Test
    @Transactional
    public void getNonExistingCashierReceiptPay() throws Exception {
        // Get the cashierReceiptPay
        restCashierReceiptPayMockMvc.perform(get("/api/cashier-receipt-pays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCashierReceiptPay() throws Exception {
        // Initialize the database
        cashierReceiptPayService.save(cashierReceiptPay);

        int databaseSizeBeforeUpdate = cashierReceiptPayRepository.findAll().size();

        // Update the cashierReceiptPay
        CashierReceiptPay updatedCashierReceiptPay = cashierReceiptPayRepository.findById(cashierReceiptPay.getId()).get();
        // Disconnect from session so that the updates on updatedCashierReceiptPay are not directly saved in db
        em.detach(updatedCashierReceiptPay);
        updatedCashierReceiptPay
            .details(UPDATED_DETAILS)
            .amount(UPDATED_AMOUNT)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .bankCheckorTraitNumber(UPDATED_BANK_CHECKOR_TRAIT_NUMBER)
            .imageJustif(UPDATED_IMAGE_JUSTIF)
            .imageJustifContentType(UPDATED_IMAGE_JUSTIF_CONTENT_TYPE);

        restCashierReceiptPayMockMvc.perform(put("/api/cashier-receipt-pays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCashierReceiptPay)))
            .andExpect(status().isOk());

        // Validate the CashierReceiptPay in the database
        List<CashierReceiptPay> cashierReceiptPayList = cashierReceiptPayRepository.findAll();
        assertThat(cashierReceiptPayList).hasSize(databaseSizeBeforeUpdate);
        CashierReceiptPay testCashierReceiptPay = cashierReceiptPayList.get(cashierReceiptPayList.size() - 1);
        assertThat(testCashierReceiptPay.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testCashierReceiptPay.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCashierReceiptPay.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testCashierReceiptPay.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testCashierReceiptPay.getBankCheckorTraitNumber()).isEqualTo(UPDATED_BANK_CHECKOR_TRAIT_NUMBER);
        assertThat(testCashierReceiptPay.getImageJustif()).isEqualTo(UPDATED_IMAGE_JUSTIF);
        assertThat(testCashierReceiptPay.getImageJustifContentType()).isEqualTo(UPDATED_IMAGE_JUSTIF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCashierReceiptPay() throws Exception {
        int databaseSizeBeforeUpdate = cashierReceiptPayRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCashierReceiptPayMockMvc.perform(put("/api/cashier-receipt-pays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierReceiptPay)))
            .andExpect(status().isBadRequest());

        // Validate the CashierReceiptPay in the database
        List<CashierReceiptPay> cashierReceiptPayList = cashierReceiptPayRepository.findAll();
        assertThat(cashierReceiptPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCashierReceiptPay() throws Exception {
        // Initialize the database
        cashierReceiptPayService.save(cashierReceiptPay);

        int databaseSizeBeforeDelete = cashierReceiptPayRepository.findAll().size();

        // Delete the cashierReceiptPay
        restCashierReceiptPayMockMvc.perform(delete("/api/cashier-receipt-pays/{id}", cashierReceiptPay.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CashierReceiptPay> cashierReceiptPayList = cashierReceiptPayRepository.findAll();
        assertThat(cashierReceiptPayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
