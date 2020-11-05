package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.MooincompaniesApp;
import org.pikasoft.mooin.mooincompanies.domain.CompanyBankAccount;
import org.pikasoft.mooin.mooincompanies.repository.CompanyBankAccountRepository;
import org.pikasoft.mooin.mooincompanies.service.CompanyBankAccountService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CompanyBankAccountResource} REST controller.
 */
@SpringBootTest(classes = MooincompaniesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompanyBankAccountResourceIT {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_IBAN = "AAAAAAAAAA";
    private static final String UPDATED_IBAN = "BBBBBBBBBB";

    private static final String DEFAULT_SWIFT = "AAAAAAAAAA";
    private static final String UPDATED_SWIFT = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private CompanyBankAccountRepository companyBankAccountRepository;

    @Autowired
    private CompanyBankAccountService companyBankAccountService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyBankAccountMockMvc;

    private CompanyBankAccount companyBankAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyBankAccount createEntity(EntityManager em) {
        CompanyBankAccount companyBankAccount = new CompanyBankAccount()
            .reference(DEFAULT_REFERENCE)
            .bankName(DEFAULT_BANK_NAME)
            .bankAccountNumber(DEFAULT_BANK_ACCOUNT_NUMBER)
            .iban(DEFAULT_IBAN)
            .swift(DEFAULT_SWIFT)
            .type(DEFAULT_TYPE);
        return companyBankAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyBankAccount createUpdatedEntity(EntityManager em) {
        CompanyBankAccount companyBankAccount = new CompanyBankAccount()
            .reference(UPDATED_REFERENCE)
            .bankName(UPDATED_BANK_NAME)
            .bankAccountNumber(UPDATED_BANK_ACCOUNT_NUMBER)
            .iban(UPDATED_IBAN)
            .swift(UPDATED_SWIFT)
            .type(UPDATED_TYPE);
        return companyBankAccount;
    }

    @BeforeEach
    public void initTest() {
        companyBankAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyBankAccount() throws Exception {
        int databaseSizeBeforeCreate = companyBankAccountRepository.findAll().size();
        // Create the CompanyBankAccount
        restCompanyBankAccountMockMvc.perform(post("/api/company-bank-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyBankAccount)))
            .andExpect(status().isCreated());

        // Validate the CompanyBankAccount in the database
        List<CompanyBankAccount> companyBankAccountList = companyBankAccountRepository.findAll();
        assertThat(companyBankAccountList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyBankAccount testCompanyBankAccount = companyBankAccountList.get(companyBankAccountList.size() - 1);
        assertThat(testCompanyBankAccount.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testCompanyBankAccount.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testCompanyBankAccount.getBankAccountNumber()).isEqualTo(DEFAULT_BANK_ACCOUNT_NUMBER);
        assertThat(testCompanyBankAccount.getIban()).isEqualTo(DEFAULT_IBAN);
        assertThat(testCompanyBankAccount.getSwift()).isEqualTo(DEFAULT_SWIFT);
        assertThat(testCompanyBankAccount.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createCompanyBankAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyBankAccountRepository.findAll().size();

        // Create the CompanyBankAccount with an existing ID
        companyBankAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyBankAccountMockMvc.perform(post("/api/company-bank-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyBankAccount)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyBankAccount in the database
        List<CompanyBankAccount> companyBankAccountList = companyBankAccountRepository.findAll();
        assertThat(companyBankAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompanyBankAccounts() throws Exception {
        // Initialize the database
        companyBankAccountRepository.saveAndFlush(companyBankAccount);

        // Get all the companyBankAccountList
        restCompanyBankAccountMockMvc.perform(get("/api/company-bank-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyBankAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].bankAccountNumber").value(hasItem(DEFAULT_BANK_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN)))
            .andExpect(jsonPath("$.[*].swift").value(hasItem(DEFAULT_SWIFT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getCompanyBankAccount() throws Exception {
        // Initialize the database
        companyBankAccountRepository.saveAndFlush(companyBankAccount);

        // Get the companyBankAccount
        restCompanyBankAccountMockMvc.perform(get("/api/company-bank-accounts/{id}", companyBankAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyBankAccount.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.bankAccountNumber").value(DEFAULT_BANK_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.iban").value(DEFAULT_IBAN))
            .andExpect(jsonPath("$.swift").value(DEFAULT_SWIFT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingCompanyBankAccount() throws Exception {
        // Get the companyBankAccount
        restCompanyBankAccountMockMvc.perform(get("/api/company-bank-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyBankAccount() throws Exception {
        // Initialize the database
        companyBankAccountService.save(companyBankAccount);

        int databaseSizeBeforeUpdate = companyBankAccountRepository.findAll().size();

        // Update the companyBankAccount
        CompanyBankAccount updatedCompanyBankAccount = companyBankAccountRepository.findById(companyBankAccount.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyBankAccount are not directly saved in db
        em.detach(updatedCompanyBankAccount);
        updatedCompanyBankAccount
            .reference(UPDATED_REFERENCE)
            .bankName(UPDATED_BANK_NAME)
            .bankAccountNumber(UPDATED_BANK_ACCOUNT_NUMBER)
            .iban(UPDATED_IBAN)
            .swift(UPDATED_SWIFT)
            .type(UPDATED_TYPE);

        restCompanyBankAccountMockMvc.perform(put("/api/company-bank-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyBankAccount)))
            .andExpect(status().isOk());

        // Validate the CompanyBankAccount in the database
        List<CompanyBankAccount> companyBankAccountList = companyBankAccountRepository.findAll();
        assertThat(companyBankAccountList).hasSize(databaseSizeBeforeUpdate);
        CompanyBankAccount testCompanyBankAccount = companyBankAccountList.get(companyBankAccountList.size() - 1);
        assertThat(testCompanyBankAccount.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testCompanyBankAccount.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testCompanyBankAccount.getBankAccountNumber()).isEqualTo(UPDATED_BANK_ACCOUNT_NUMBER);
        assertThat(testCompanyBankAccount.getIban()).isEqualTo(UPDATED_IBAN);
        assertThat(testCompanyBankAccount.getSwift()).isEqualTo(UPDATED_SWIFT);
        assertThat(testCompanyBankAccount.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyBankAccount() throws Exception {
        int databaseSizeBeforeUpdate = companyBankAccountRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyBankAccountMockMvc.perform(put("/api/company-bank-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyBankAccount)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyBankAccount in the database
        List<CompanyBankAccount> companyBankAccountList = companyBankAccountRepository.findAll();
        assertThat(companyBankAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyBankAccount() throws Exception {
        // Initialize the database
        companyBankAccountService.save(companyBankAccount);

        int databaseSizeBeforeDelete = companyBankAccountRepository.findAll().size();

        // Delete the companyBankAccount
        restCompanyBankAccountMockMvc.perform(delete("/api/company-bank-accounts/{id}", companyBankAccount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyBankAccount> companyBankAccountList = companyBankAccountRepository.findAll();
        assertThat(companyBankAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
