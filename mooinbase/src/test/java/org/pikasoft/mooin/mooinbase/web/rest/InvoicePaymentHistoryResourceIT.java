package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.MooinbaseApp;
import org.pikasoft.mooin.mooinbase.domain.InvoicePaymentHistory;
import org.pikasoft.mooin.mooinbase.repository.InvoicePaymentHistoryRepository;
import org.pikasoft.mooin.mooinbase.service.InvoicePaymentHistoryService;

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

import org.pikasoft.mooin.mooinbase.domain.enumeration.InvoicePaymentMethod;
/**
 * Integration tests for the {@link InvoicePaymentHistoryResource} REST controller.
 */
@SpringBootTest(classes = MooinbaseApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InvoicePaymentHistoryResourceIT {

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(1);

    private static final Instant DEFAULT_PAYMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAYMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final InvoicePaymentMethod DEFAULT_PAYMENT_METHOD = InvoicePaymentMethod.CREDIT_CARD;
    private static final InvoicePaymentMethod UPDATED_PAYMENT_METHOD = InvoicePaymentMethod.CASH;

    private static final String DEFAULT_BANK_CHECKOR_TRAIT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BANK_CHECKOR_TRAIT_NUMBER = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE_JUSTIF = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_JUSTIF = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_JUSTIF_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_JUSTIF_CONTENT_TYPE = "image/png";

    @Autowired
    private InvoicePaymentHistoryRepository invoicePaymentHistoryRepository;

    @Autowired
    private InvoicePaymentHistoryService invoicePaymentHistoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoicePaymentHistoryMockMvc;

    private InvoicePaymentHistory invoicePaymentHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoicePaymentHistory createEntity(EntityManager em) {
        InvoicePaymentHistory invoicePaymentHistory = new InvoicePaymentHistory()
            .details(DEFAULT_DETAILS)
            .amount(DEFAULT_AMOUNT)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .bankCheckorTraitNumber(DEFAULT_BANK_CHECKOR_TRAIT_NUMBER)
            .imageJustif(DEFAULT_IMAGE_JUSTIF)
            .imageJustifContentType(DEFAULT_IMAGE_JUSTIF_CONTENT_TYPE);
        return invoicePaymentHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoicePaymentHistory createUpdatedEntity(EntityManager em) {
        InvoicePaymentHistory invoicePaymentHistory = new InvoicePaymentHistory()
            .details(UPDATED_DETAILS)
            .amount(UPDATED_AMOUNT)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .bankCheckorTraitNumber(UPDATED_BANK_CHECKOR_TRAIT_NUMBER)
            .imageJustif(UPDATED_IMAGE_JUSTIF)
            .imageJustifContentType(UPDATED_IMAGE_JUSTIF_CONTENT_TYPE);
        return invoicePaymentHistory;
    }

    @BeforeEach
    public void initTest() {
        invoicePaymentHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoicePaymentHistory() throws Exception {
        int databaseSizeBeforeCreate = invoicePaymentHistoryRepository.findAll().size();
        // Create the InvoicePaymentHistory
        restInvoicePaymentHistoryMockMvc.perform(post("/api/invoice-payment-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoicePaymentHistory)))
            .andExpect(status().isCreated());

        // Validate the InvoicePaymentHistory in the database
        List<InvoicePaymentHistory> invoicePaymentHistoryList = invoicePaymentHistoryRepository.findAll();
        assertThat(invoicePaymentHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        InvoicePaymentHistory testInvoicePaymentHistory = invoicePaymentHistoryList.get(invoicePaymentHistoryList.size() - 1);
        assertThat(testInvoicePaymentHistory.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testInvoicePaymentHistory.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testInvoicePaymentHistory.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testInvoicePaymentHistory.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testInvoicePaymentHistory.getBankCheckorTraitNumber()).isEqualTo(DEFAULT_BANK_CHECKOR_TRAIT_NUMBER);
        assertThat(testInvoicePaymentHistory.getImageJustif()).isEqualTo(DEFAULT_IMAGE_JUSTIF);
        assertThat(testInvoicePaymentHistory.getImageJustifContentType()).isEqualTo(DEFAULT_IMAGE_JUSTIF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createInvoicePaymentHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoicePaymentHistoryRepository.findAll().size();

        // Create the InvoicePaymentHistory with an existing ID
        invoicePaymentHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoicePaymentHistoryMockMvc.perform(post("/api/invoice-payment-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoicePaymentHistory)))
            .andExpect(status().isBadRequest());

        // Validate the InvoicePaymentHistory in the database
        List<InvoicePaymentHistory> invoicePaymentHistoryList = invoicePaymentHistoryRepository.findAll();
        assertThat(invoicePaymentHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoicePaymentHistoryRepository.findAll().size();
        // set the field null
        invoicePaymentHistory.setAmount(null);

        // Create the InvoicePaymentHistory, which fails.


        restInvoicePaymentHistoryMockMvc.perform(post("/api/invoice-payment-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoicePaymentHistory)))
            .andExpect(status().isBadRequest());

        List<InvoicePaymentHistory> invoicePaymentHistoryList = invoicePaymentHistoryRepository.findAll();
        assertThat(invoicePaymentHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvoicePaymentHistories() throws Exception {
        // Initialize the database
        invoicePaymentHistoryRepository.saveAndFlush(invoicePaymentHistory);

        // Get all the invoicePaymentHistoryList
        restInvoicePaymentHistoryMockMvc.perform(get("/api/invoice-payment-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoicePaymentHistory.getId().intValue())))
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
    public void getInvoicePaymentHistory() throws Exception {
        // Initialize the database
        invoicePaymentHistoryRepository.saveAndFlush(invoicePaymentHistory);

        // Get the invoicePaymentHistory
        restInvoicePaymentHistoryMockMvc.perform(get("/api/invoice-payment-histories/{id}", invoicePaymentHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoicePaymentHistory.getId().intValue()))
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
    public void getNonExistingInvoicePaymentHistory() throws Exception {
        // Get the invoicePaymentHistory
        restInvoicePaymentHistoryMockMvc.perform(get("/api/invoice-payment-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoicePaymentHistory() throws Exception {
        // Initialize the database
        invoicePaymentHistoryService.save(invoicePaymentHistory);

        int databaseSizeBeforeUpdate = invoicePaymentHistoryRepository.findAll().size();

        // Update the invoicePaymentHistory
        InvoicePaymentHistory updatedInvoicePaymentHistory = invoicePaymentHistoryRepository.findById(invoicePaymentHistory.getId()).get();
        // Disconnect from session so that the updates on updatedInvoicePaymentHistory are not directly saved in db
        em.detach(updatedInvoicePaymentHistory);
        updatedInvoicePaymentHistory
            .details(UPDATED_DETAILS)
            .amount(UPDATED_AMOUNT)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .bankCheckorTraitNumber(UPDATED_BANK_CHECKOR_TRAIT_NUMBER)
            .imageJustif(UPDATED_IMAGE_JUSTIF)
            .imageJustifContentType(UPDATED_IMAGE_JUSTIF_CONTENT_TYPE);

        restInvoicePaymentHistoryMockMvc.perform(put("/api/invoice-payment-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvoicePaymentHistory)))
            .andExpect(status().isOk());

        // Validate the InvoicePaymentHistory in the database
        List<InvoicePaymentHistory> invoicePaymentHistoryList = invoicePaymentHistoryRepository.findAll();
        assertThat(invoicePaymentHistoryList).hasSize(databaseSizeBeforeUpdate);
        InvoicePaymentHistory testInvoicePaymentHistory = invoicePaymentHistoryList.get(invoicePaymentHistoryList.size() - 1);
        assertThat(testInvoicePaymentHistory.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testInvoicePaymentHistory.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testInvoicePaymentHistory.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testInvoicePaymentHistory.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testInvoicePaymentHistory.getBankCheckorTraitNumber()).isEqualTo(UPDATED_BANK_CHECKOR_TRAIT_NUMBER);
        assertThat(testInvoicePaymentHistory.getImageJustif()).isEqualTo(UPDATED_IMAGE_JUSTIF);
        assertThat(testInvoicePaymentHistory.getImageJustifContentType()).isEqualTo(UPDATED_IMAGE_JUSTIF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoicePaymentHistory() throws Exception {
        int databaseSizeBeforeUpdate = invoicePaymentHistoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoicePaymentHistoryMockMvc.perform(put("/api/invoice-payment-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoicePaymentHistory)))
            .andExpect(status().isBadRequest());

        // Validate the InvoicePaymentHistory in the database
        List<InvoicePaymentHistory> invoicePaymentHistoryList = invoicePaymentHistoryRepository.findAll();
        assertThat(invoicePaymentHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoicePaymentHistory() throws Exception {
        // Initialize the database
        invoicePaymentHistoryService.save(invoicePaymentHistory);

        int databaseSizeBeforeDelete = invoicePaymentHistoryRepository.findAll().size();

        // Delete the invoicePaymentHistory
        restInvoicePaymentHistoryMockMvc.perform(delete("/api/invoice-payment-histories/{id}", invoicePaymentHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvoicePaymentHistory> invoicePaymentHistoryList = invoicePaymentHistoryRepository.findAll();
        assertThat(invoicePaymentHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
