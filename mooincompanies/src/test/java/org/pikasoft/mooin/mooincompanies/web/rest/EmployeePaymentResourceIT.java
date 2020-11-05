package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.MooincompaniesApp;
import org.pikasoft.mooin.mooincompanies.domain.EmployeePayment;
import org.pikasoft.mooin.mooincompanies.repository.EmployeePaymentRepository;
import org.pikasoft.mooin.mooincompanies.service.EmployeePaymentService;

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
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EmployeePaymentResource} REST controller.
 */
@SpringBootTest(classes = MooincompaniesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeePaymentResourceIT {

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(1);

    private static final Instant DEFAULT_PAYMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAYMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_IMAGE_JUSTIF = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_JUSTIF = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_JUSTIF_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_JUSTIF_CONTENT_TYPE = "image/png";

    @Autowired
    private EmployeePaymentRepository employeePaymentRepository;

    @Autowired
    private EmployeePaymentService employeePaymentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeePaymentMockMvc;

    private EmployeePayment employeePayment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeePayment createEntity(EntityManager em) {
        EmployeePayment employeePayment = new EmployeePayment()
            .details(DEFAULT_DETAILS)
            .amount(DEFAULT_AMOUNT)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .imageJustif(DEFAULT_IMAGE_JUSTIF)
            .imageJustifContentType(DEFAULT_IMAGE_JUSTIF_CONTENT_TYPE);
        return employeePayment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeePayment createUpdatedEntity(EntityManager em) {
        EmployeePayment employeePayment = new EmployeePayment()
            .details(UPDATED_DETAILS)
            .amount(UPDATED_AMOUNT)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .imageJustif(UPDATED_IMAGE_JUSTIF)
            .imageJustifContentType(UPDATED_IMAGE_JUSTIF_CONTENT_TYPE);
        return employeePayment;
    }

    @BeforeEach
    public void initTest() {
        employeePayment = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeePayment() throws Exception {
        int databaseSizeBeforeCreate = employeePaymentRepository.findAll().size();
        // Create the EmployeePayment
        restEmployeePaymentMockMvc.perform(post("/api/employee-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeePayment)))
            .andExpect(status().isCreated());

        // Validate the EmployeePayment in the database
        List<EmployeePayment> employeePaymentList = employeePaymentRepository.findAll();
        assertThat(employeePaymentList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeePayment testEmployeePayment = employeePaymentList.get(employeePaymentList.size() - 1);
        assertThat(testEmployeePayment.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testEmployeePayment.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testEmployeePayment.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testEmployeePayment.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testEmployeePayment.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testEmployeePayment.getImageJustif()).isEqualTo(DEFAULT_IMAGE_JUSTIF);
        assertThat(testEmployeePayment.getImageJustifContentType()).isEqualTo(DEFAULT_IMAGE_JUSTIF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createEmployeePaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeePaymentRepository.findAll().size();

        // Create the EmployeePayment with an existing ID
        employeePayment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeePaymentMockMvc.perform(post("/api/employee-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeePayment)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeePayment in the database
        List<EmployeePayment> employeePaymentList = employeePaymentRepository.findAll();
        assertThat(employeePaymentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeePaymentRepository.findAll().size();
        // set the field null
        employeePayment.setAmount(null);

        // Create the EmployeePayment, which fails.


        restEmployeePaymentMockMvc.perform(post("/api/employee-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeePayment)))
            .andExpect(status().isBadRequest());

        List<EmployeePayment> employeePaymentList = employeePaymentRepository.findAll();
        assertThat(employeePaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeePayments() throws Exception {
        // Initialize the database
        employeePaymentRepository.saveAndFlush(employeePayment);

        // Get all the employeePaymentList
        restEmployeePaymentMockMvc.perform(get("/api/employee-payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeePayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].imageJustifContentType").value(hasItem(DEFAULT_IMAGE_JUSTIF_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageJustif").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_JUSTIF))));
    }
    
    @Test
    @Transactional
    public void getEmployeePayment() throws Exception {
        // Initialize the database
        employeePaymentRepository.saveAndFlush(employeePayment);

        // Get the employeePayment
        restEmployeePaymentMockMvc.perform(get("/api/employee-payments/{id}", employeePayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeePayment.getId().intValue()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.imageJustifContentType").value(DEFAULT_IMAGE_JUSTIF_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageJustif").value(Base64Utils.encodeToString(DEFAULT_IMAGE_JUSTIF)));
    }
    @Test
    @Transactional
    public void getNonExistingEmployeePayment() throws Exception {
        // Get the employeePayment
        restEmployeePaymentMockMvc.perform(get("/api/employee-payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeePayment() throws Exception {
        // Initialize the database
        employeePaymentService.save(employeePayment);

        int databaseSizeBeforeUpdate = employeePaymentRepository.findAll().size();

        // Update the employeePayment
        EmployeePayment updatedEmployeePayment = employeePaymentRepository.findById(employeePayment.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeePayment are not directly saved in db
        em.detach(updatedEmployeePayment);
        updatedEmployeePayment
            .details(UPDATED_DETAILS)
            .amount(UPDATED_AMOUNT)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .imageJustif(UPDATED_IMAGE_JUSTIF)
            .imageJustifContentType(UPDATED_IMAGE_JUSTIF_CONTENT_TYPE);

        restEmployeePaymentMockMvc.perform(put("/api/employee-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeePayment)))
            .andExpect(status().isOk());

        // Validate the EmployeePayment in the database
        List<EmployeePayment> employeePaymentList = employeePaymentRepository.findAll();
        assertThat(employeePaymentList).hasSize(databaseSizeBeforeUpdate);
        EmployeePayment testEmployeePayment = employeePaymentList.get(employeePaymentList.size() - 1);
        assertThat(testEmployeePayment.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testEmployeePayment.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testEmployeePayment.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testEmployeePayment.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testEmployeePayment.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testEmployeePayment.getImageJustif()).isEqualTo(UPDATED_IMAGE_JUSTIF);
        assertThat(testEmployeePayment.getImageJustifContentType()).isEqualTo(UPDATED_IMAGE_JUSTIF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeePayment() throws Exception {
        int databaseSizeBeforeUpdate = employeePaymentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeePaymentMockMvc.perform(put("/api/employee-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeePayment)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeePayment in the database
        List<EmployeePayment> employeePaymentList = employeePaymentRepository.findAll();
        assertThat(employeePaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeePayment() throws Exception {
        // Initialize the database
        employeePaymentService.save(employeePayment);

        int databaseSizeBeforeDelete = employeePaymentRepository.findAll().size();

        // Delete the employeePayment
        restEmployeePaymentMockMvc.perform(delete("/api/employee-payments/{id}", employeePayment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeePayment> employeePaymentList = employeePaymentRepository.findAll();
        assertThat(employeePaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
