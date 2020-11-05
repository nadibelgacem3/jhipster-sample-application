package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.MooinbaseApp;
import org.pikasoft.mooin.mooinbase.domain.TiersBankCheck;
import org.pikasoft.mooin.mooinbase.repository.TiersBankCheckRepository;
import org.pikasoft.mooin.mooinbase.service.TiersBankCheckService;

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

import org.pikasoft.mooin.mooinbase.domain.enumeration.BankCheckType;
import org.pikasoft.mooin.mooinbase.domain.enumeration.BankCheckKind;
/**
 * Integration tests for the {@link TiersBankCheckResource} REST controller.
 */
@SpringBootTest(classes = MooinbaseApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TiersBankCheckResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BankCheckType DEFAULT_BANK_CHECK_TYPE = BankCheckType.CHECK;
    private static final BankCheckType UPDATED_BANK_CHECK_TYPE = BankCheckType.TRAIT;

    private static final BankCheckKind DEFAULT_BANK_CHECK_KIND = BankCheckKind.RECEIVED;
    private static final BankCheckKind UPDATED_BANK_CHECK_KIND = BankCheckKind.SUBMITTED;

    private static final String DEFAULT_SWIFT = "AAAAAAAAAA";
    private static final String UPDATED_SWIFT = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private TiersBankCheckRepository tiersBankCheckRepository;

    @Autowired
    private TiersBankCheckService tiersBankCheckService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTiersBankCheckMockMvc;

    private TiersBankCheck tiersBankCheck;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TiersBankCheck createEntity(EntityManager em) {
        TiersBankCheck tiersBankCheck = new TiersBankCheck()
            .name(DEFAULT_NAME)
            .bankName(DEFAULT_BANK_NAME)
            .number(DEFAULT_NUMBER)
            .amount(DEFAULT_AMOUNT)
            .dueDate(DEFAULT_DUE_DATE)
            .bankCheckType(DEFAULT_BANK_CHECK_TYPE)
            .bankCheckKind(DEFAULT_BANK_CHECK_KIND)
            .swift(DEFAULT_SWIFT)
            .type(DEFAULT_TYPE);
        return tiersBankCheck;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TiersBankCheck createUpdatedEntity(EntityManager em) {
        TiersBankCheck tiersBankCheck = new TiersBankCheck()
            .name(UPDATED_NAME)
            .bankName(UPDATED_BANK_NAME)
            .number(UPDATED_NUMBER)
            .amount(UPDATED_AMOUNT)
            .dueDate(UPDATED_DUE_DATE)
            .bankCheckType(UPDATED_BANK_CHECK_TYPE)
            .bankCheckKind(UPDATED_BANK_CHECK_KIND)
            .swift(UPDATED_SWIFT)
            .type(UPDATED_TYPE);
        return tiersBankCheck;
    }

    @BeforeEach
    public void initTest() {
        tiersBankCheck = createEntity(em);
    }

    @Test
    @Transactional
    public void createTiersBankCheck() throws Exception {
        int databaseSizeBeforeCreate = tiersBankCheckRepository.findAll().size();
        // Create the TiersBankCheck
        restTiersBankCheckMockMvc.perform(post("/api/tiers-bank-checks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiersBankCheck)))
            .andExpect(status().isCreated());

        // Validate the TiersBankCheck in the database
        List<TiersBankCheck> tiersBankCheckList = tiersBankCheckRepository.findAll();
        assertThat(tiersBankCheckList).hasSize(databaseSizeBeforeCreate + 1);
        TiersBankCheck testTiersBankCheck = tiersBankCheckList.get(tiersBankCheckList.size() - 1);
        assertThat(testTiersBankCheck.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTiersBankCheck.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testTiersBankCheck.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testTiersBankCheck.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testTiersBankCheck.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testTiersBankCheck.getBankCheckType()).isEqualTo(DEFAULT_BANK_CHECK_TYPE);
        assertThat(testTiersBankCheck.getBankCheckKind()).isEqualTo(DEFAULT_BANK_CHECK_KIND);
        assertThat(testTiersBankCheck.getSwift()).isEqualTo(DEFAULT_SWIFT);
        assertThat(testTiersBankCheck.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createTiersBankCheckWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tiersBankCheckRepository.findAll().size();

        // Create the TiersBankCheck with an existing ID
        tiersBankCheck.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTiersBankCheckMockMvc.perform(post("/api/tiers-bank-checks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiersBankCheck)))
            .andExpect(status().isBadRequest());

        // Validate the TiersBankCheck in the database
        List<TiersBankCheck> tiersBankCheckList = tiersBankCheckRepository.findAll();
        assertThat(tiersBankCheckList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiersBankCheckRepository.findAll().size();
        // set the field null
        tiersBankCheck.setAmount(null);

        // Create the TiersBankCheck, which fails.


        restTiersBankCheckMockMvc.perform(post("/api/tiers-bank-checks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiersBankCheck)))
            .andExpect(status().isBadRequest());

        List<TiersBankCheck> tiersBankCheckList = tiersBankCheckRepository.findAll();
        assertThat(tiersBankCheckList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDueDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiersBankCheckRepository.findAll().size();
        // set the field null
        tiersBankCheck.setDueDate(null);

        // Create the TiersBankCheck, which fails.


        restTiersBankCheckMockMvc.perform(post("/api/tiers-bank-checks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiersBankCheck)))
            .andExpect(status().isBadRequest());

        List<TiersBankCheck> tiersBankCheckList = tiersBankCheckRepository.findAll();
        assertThat(tiersBankCheckList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBankCheckTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiersBankCheckRepository.findAll().size();
        // set the field null
        tiersBankCheck.setBankCheckType(null);

        // Create the TiersBankCheck, which fails.


        restTiersBankCheckMockMvc.perform(post("/api/tiers-bank-checks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiersBankCheck)))
            .andExpect(status().isBadRequest());

        List<TiersBankCheck> tiersBankCheckList = tiersBankCheckRepository.findAll();
        assertThat(tiersBankCheckList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBankCheckKindIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiersBankCheckRepository.findAll().size();
        // set the field null
        tiersBankCheck.setBankCheckKind(null);

        // Create the TiersBankCheck, which fails.


        restTiersBankCheckMockMvc.perform(post("/api/tiers-bank-checks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiersBankCheck)))
            .andExpect(status().isBadRequest());

        List<TiersBankCheck> tiersBankCheckList = tiersBankCheckRepository.findAll();
        assertThat(tiersBankCheckList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTiersBankChecks() throws Exception {
        // Initialize the database
        tiersBankCheckRepository.saveAndFlush(tiersBankCheck);

        // Get all the tiersBankCheckList
        restTiersBankCheckMockMvc.perform(get("/api/tiers-bank-checks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tiersBankCheck.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].bankCheckType").value(hasItem(DEFAULT_BANK_CHECK_TYPE.toString())))
            .andExpect(jsonPath("$.[*].bankCheckKind").value(hasItem(DEFAULT_BANK_CHECK_KIND.toString())))
            .andExpect(jsonPath("$.[*].swift").value(hasItem(DEFAULT_SWIFT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getTiersBankCheck() throws Exception {
        // Initialize the database
        tiersBankCheckRepository.saveAndFlush(tiersBankCheck);

        // Get the tiersBankCheck
        restTiersBankCheckMockMvc.perform(get("/api/tiers-bank-checks/{id}", tiersBankCheck.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tiersBankCheck.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.bankCheckType").value(DEFAULT_BANK_CHECK_TYPE.toString()))
            .andExpect(jsonPath("$.bankCheckKind").value(DEFAULT_BANK_CHECK_KIND.toString()))
            .andExpect(jsonPath("$.swift").value(DEFAULT_SWIFT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingTiersBankCheck() throws Exception {
        // Get the tiersBankCheck
        restTiersBankCheckMockMvc.perform(get("/api/tiers-bank-checks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTiersBankCheck() throws Exception {
        // Initialize the database
        tiersBankCheckService.save(tiersBankCheck);

        int databaseSizeBeforeUpdate = tiersBankCheckRepository.findAll().size();

        // Update the tiersBankCheck
        TiersBankCheck updatedTiersBankCheck = tiersBankCheckRepository.findById(tiersBankCheck.getId()).get();
        // Disconnect from session so that the updates on updatedTiersBankCheck are not directly saved in db
        em.detach(updatedTiersBankCheck);
        updatedTiersBankCheck
            .name(UPDATED_NAME)
            .bankName(UPDATED_BANK_NAME)
            .number(UPDATED_NUMBER)
            .amount(UPDATED_AMOUNT)
            .dueDate(UPDATED_DUE_DATE)
            .bankCheckType(UPDATED_BANK_CHECK_TYPE)
            .bankCheckKind(UPDATED_BANK_CHECK_KIND)
            .swift(UPDATED_SWIFT)
            .type(UPDATED_TYPE);

        restTiersBankCheckMockMvc.perform(put("/api/tiers-bank-checks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTiersBankCheck)))
            .andExpect(status().isOk());

        // Validate the TiersBankCheck in the database
        List<TiersBankCheck> tiersBankCheckList = tiersBankCheckRepository.findAll();
        assertThat(tiersBankCheckList).hasSize(databaseSizeBeforeUpdate);
        TiersBankCheck testTiersBankCheck = tiersBankCheckList.get(tiersBankCheckList.size() - 1);
        assertThat(testTiersBankCheck.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTiersBankCheck.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testTiersBankCheck.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testTiersBankCheck.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTiersBankCheck.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testTiersBankCheck.getBankCheckType()).isEqualTo(UPDATED_BANK_CHECK_TYPE);
        assertThat(testTiersBankCheck.getBankCheckKind()).isEqualTo(UPDATED_BANK_CHECK_KIND);
        assertThat(testTiersBankCheck.getSwift()).isEqualTo(UPDATED_SWIFT);
        assertThat(testTiersBankCheck.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTiersBankCheck() throws Exception {
        int databaseSizeBeforeUpdate = tiersBankCheckRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTiersBankCheckMockMvc.perform(put("/api/tiers-bank-checks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiersBankCheck)))
            .andExpect(status().isBadRequest());

        // Validate the TiersBankCheck in the database
        List<TiersBankCheck> tiersBankCheckList = tiersBankCheckRepository.findAll();
        assertThat(tiersBankCheckList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTiersBankCheck() throws Exception {
        // Initialize the database
        tiersBankCheckService.save(tiersBankCheck);

        int databaseSizeBeforeDelete = tiersBankCheckRepository.findAll().size();

        // Delete the tiersBankCheck
        restTiersBankCheckMockMvc.perform(delete("/api/tiers-bank-checks/{id}", tiersBankCheck.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TiersBankCheck> tiersBankCheckList = tiersBankCheckRepository.findAll();
        assertThat(tiersBankCheckList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
