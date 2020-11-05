package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.MooincompaniesApp;
import org.pikasoft.mooin.mooincompanies.domain.CompanyModule;
import org.pikasoft.mooin.mooincompanies.repository.CompanyModuleRepository;
import org.pikasoft.mooin.mooincompanies.service.CompanyModuleService;

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

/**
 * Integration tests for the {@link CompanyModuleResource} REST controller.
 */
@SpringBootTest(classes = MooincompaniesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompanyModuleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ACTIVATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACTIVATED_UNTIL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVATED_UNTIL = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_ACTIVATED = false;
    private static final Boolean UPDATED_IS_ACTIVATED = true;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(1);

    @Autowired
    private CompanyModuleRepository companyModuleRepository;

    @Autowired
    private CompanyModuleService companyModuleService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyModuleMockMvc;

    private CompanyModule companyModule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyModule createEntity(EntityManager em) {
        CompanyModule companyModule = new CompanyModule()
            .name(DEFAULT_NAME)
            .activatedAt(DEFAULT_ACTIVATED_AT)
            .activatedUntil(DEFAULT_ACTIVATED_UNTIL)
            .isActivated(DEFAULT_IS_ACTIVATED)
            .price(DEFAULT_PRICE);
        return companyModule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyModule createUpdatedEntity(EntityManager em) {
        CompanyModule companyModule = new CompanyModule()
            .name(UPDATED_NAME)
            .activatedAt(UPDATED_ACTIVATED_AT)
            .activatedUntil(UPDATED_ACTIVATED_UNTIL)
            .isActivated(UPDATED_IS_ACTIVATED)
            .price(UPDATED_PRICE);
        return companyModule;
    }

    @BeforeEach
    public void initTest() {
        companyModule = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyModule() throws Exception {
        int databaseSizeBeforeCreate = companyModuleRepository.findAll().size();
        // Create the CompanyModule
        restCompanyModuleMockMvc.perform(post("/api/company-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyModule)))
            .andExpect(status().isCreated());

        // Validate the CompanyModule in the database
        List<CompanyModule> companyModuleList = companyModuleRepository.findAll();
        assertThat(companyModuleList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyModule testCompanyModule = companyModuleList.get(companyModuleList.size() - 1);
        assertThat(testCompanyModule.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyModule.getActivatedAt()).isEqualTo(DEFAULT_ACTIVATED_AT);
        assertThat(testCompanyModule.getActivatedUntil()).isEqualTo(DEFAULT_ACTIVATED_UNTIL);
        assertThat(testCompanyModule.isIsActivated()).isEqualTo(DEFAULT_IS_ACTIVATED);
        assertThat(testCompanyModule.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createCompanyModuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyModuleRepository.findAll().size();

        // Create the CompanyModule with an existing ID
        companyModule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyModuleMockMvc.perform(post("/api/company-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyModule)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyModule in the database
        List<CompanyModule> companyModuleList = companyModuleRepository.findAll();
        assertThat(companyModuleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyModuleRepository.findAll().size();
        // set the field null
        companyModule.setName(null);

        // Create the CompanyModule, which fails.


        restCompanyModuleMockMvc.perform(post("/api/company-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyModule)))
            .andExpect(status().isBadRequest());

        List<CompanyModule> companyModuleList = companyModuleRepository.findAll();
        assertThat(companyModuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyModuleRepository.findAll().size();
        // set the field null
        companyModule.setActivatedAt(null);

        // Create the CompanyModule, which fails.


        restCompanyModuleMockMvc.perform(post("/api/company-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyModule)))
            .andExpect(status().isBadRequest());

        List<CompanyModule> companyModuleList = companyModuleRepository.findAll();
        assertThat(companyModuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivatedUntilIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyModuleRepository.findAll().size();
        // set the field null
        companyModule.setActivatedUntil(null);

        // Create the CompanyModule, which fails.


        restCompanyModuleMockMvc.perform(post("/api/company-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyModule)))
            .andExpect(status().isBadRequest());

        List<CompanyModule> companyModuleList = companyModuleRepository.findAll();
        assertThat(companyModuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsActivatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyModuleRepository.findAll().size();
        // set the field null
        companyModule.setIsActivated(null);

        // Create the CompanyModule, which fails.


        restCompanyModuleMockMvc.perform(post("/api/company-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyModule)))
            .andExpect(status().isBadRequest());

        List<CompanyModule> companyModuleList = companyModuleRepository.findAll();
        assertThat(companyModuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyModuleRepository.findAll().size();
        // set the field null
        companyModule.setPrice(null);

        // Create the CompanyModule, which fails.


        restCompanyModuleMockMvc.perform(post("/api/company-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyModule)))
            .andExpect(status().isBadRequest());

        List<CompanyModule> companyModuleList = companyModuleRepository.findAll();
        assertThat(companyModuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanyModules() throws Exception {
        // Initialize the database
        companyModuleRepository.saveAndFlush(companyModule);

        // Get all the companyModuleList
        restCompanyModuleMockMvc.perform(get("/api/company-modules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyModule.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].activatedAt").value(hasItem(DEFAULT_ACTIVATED_AT.toString())))
            .andExpect(jsonPath("$.[*].activatedUntil").value(hasItem(DEFAULT_ACTIVATED_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].isActivated").value(hasItem(DEFAULT_IS_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    
    @Test
    @Transactional
    public void getCompanyModule() throws Exception {
        // Initialize the database
        companyModuleRepository.saveAndFlush(companyModule);

        // Get the companyModule
        restCompanyModuleMockMvc.perform(get("/api/company-modules/{id}", companyModule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyModule.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.activatedAt").value(DEFAULT_ACTIVATED_AT.toString()))
            .andExpect(jsonPath("$.activatedUntil").value(DEFAULT_ACTIVATED_UNTIL.toString()))
            .andExpect(jsonPath("$.isActivated").value(DEFAULT_IS_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCompanyModule() throws Exception {
        // Get the companyModule
        restCompanyModuleMockMvc.perform(get("/api/company-modules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyModule() throws Exception {
        // Initialize the database
        companyModuleService.save(companyModule);

        int databaseSizeBeforeUpdate = companyModuleRepository.findAll().size();

        // Update the companyModule
        CompanyModule updatedCompanyModule = companyModuleRepository.findById(companyModule.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyModule are not directly saved in db
        em.detach(updatedCompanyModule);
        updatedCompanyModule
            .name(UPDATED_NAME)
            .activatedAt(UPDATED_ACTIVATED_AT)
            .activatedUntil(UPDATED_ACTIVATED_UNTIL)
            .isActivated(UPDATED_IS_ACTIVATED)
            .price(UPDATED_PRICE);

        restCompanyModuleMockMvc.perform(put("/api/company-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyModule)))
            .andExpect(status().isOk());

        // Validate the CompanyModule in the database
        List<CompanyModule> companyModuleList = companyModuleRepository.findAll();
        assertThat(companyModuleList).hasSize(databaseSizeBeforeUpdate);
        CompanyModule testCompanyModule = companyModuleList.get(companyModuleList.size() - 1);
        assertThat(testCompanyModule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyModule.getActivatedAt()).isEqualTo(UPDATED_ACTIVATED_AT);
        assertThat(testCompanyModule.getActivatedUntil()).isEqualTo(UPDATED_ACTIVATED_UNTIL);
        assertThat(testCompanyModule.isIsActivated()).isEqualTo(UPDATED_IS_ACTIVATED);
        assertThat(testCompanyModule.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyModule() throws Exception {
        int databaseSizeBeforeUpdate = companyModuleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyModuleMockMvc.perform(put("/api/company-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyModule)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyModule in the database
        List<CompanyModule> companyModuleList = companyModuleRepository.findAll();
        assertThat(companyModuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyModule() throws Exception {
        // Initialize the database
        companyModuleService.save(companyModule);

        int databaseSizeBeforeDelete = companyModuleRepository.findAll().size();

        // Delete the companyModule
        restCompanyModuleMockMvc.perform(delete("/api/company-modules/{id}", companyModule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyModule> companyModuleList = companyModuleRepository.findAll();
        assertThat(companyModuleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
