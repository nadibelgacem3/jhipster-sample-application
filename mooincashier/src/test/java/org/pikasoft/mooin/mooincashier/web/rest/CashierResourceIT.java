package org.pikasoft.mooin.mooincashier.web.rest;

import org.pikasoft.mooin.mooincashier.MooincashierApp;
import org.pikasoft.mooin.mooincashier.domain.Cashier;
import org.pikasoft.mooin.mooincashier.repository.CashierRepository;
import org.pikasoft.mooin.mooincashier.service.CashierService;

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
 * Integration tests for the {@link CashierResource} REST controller.
 */
@SpringBootTest(classes = MooincashierApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CashierResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_WITH_TICKET = false;
    private static final Boolean UPDATED_WITH_TICKET = true;

    private static final Boolean DEFAULT_WITH_TVA = false;
    private static final Boolean UPDATED_WITH_TVA = true;

    private static final Boolean DEFAULT_WITH_TAX = false;
    private static final Boolean UPDATED_WITH_TAX = true;

    private static final Boolean DEFAULT_WITH_APPRO = false;
    private static final Boolean UPDATED_WITH_APPRO = true;

    private static final String DEFAULT_THEME_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_THEME_COLOR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVATED = false;
    private static final Boolean UPDATED_IS_ACTIVATED = true;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    @Autowired
    private CashierRepository cashierRepository;

    @Autowired
    private CashierService cashierService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCashierMockMvc;

    private Cashier cashier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cashier createEntity(EntityManager em) {
        Cashier cashier = new Cashier()
            .name(DEFAULT_NAME)
            .withTicket(DEFAULT_WITH_TICKET)
            .withTVA(DEFAULT_WITH_TVA)
            .withTax(DEFAULT_WITH_TAX)
            .withAppro(DEFAULT_WITH_APPRO)
            .themeColor(DEFAULT_THEME_COLOR)
            .isActivated(DEFAULT_IS_ACTIVATED)
            .companyID(DEFAULT_COMPANY_ID);
        return cashier;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cashier createUpdatedEntity(EntityManager em) {
        Cashier cashier = new Cashier()
            .name(UPDATED_NAME)
            .withTicket(UPDATED_WITH_TICKET)
            .withTVA(UPDATED_WITH_TVA)
            .withTax(UPDATED_WITH_TAX)
            .withAppro(UPDATED_WITH_APPRO)
            .themeColor(UPDATED_THEME_COLOR)
            .isActivated(UPDATED_IS_ACTIVATED)
            .companyID(UPDATED_COMPANY_ID);
        return cashier;
    }

    @BeforeEach
    public void initTest() {
        cashier = createEntity(em);
    }

    @Test
    @Transactional
    public void createCashier() throws Exception {
        int databaseSizeBeforeCreate = cashierRepository.findAll().size();
        // Create the Cashier
        restCashierMockMvc.perform(post("/api/cashiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashier)))
            .andExpect(status().isCreated());

        // Validate the Cashier in the database
        List<Cashier> cashierList = cashierRepository.findAll();
        assertThat(cashierList).hasSize(databaseSizeBeforeCreate + 1);
        Cashier testCashier = cashierList.get(cashierList.size() - 1);
        assertThat(testCashier.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCashier.isWithTicket()).isEqualTo(DEFAULT_WITH_TICKET);
        assertThat(testCashier.isWithTVA()).isEqualTo(DEFAULT_WITH_TVA);
        assertThat(testCashier.isWithTax()).isEqualTo(DEFAULT_WITH_TAX);
        assertThat(testCashier.isWithAppro()).isEqualTo(DEFAULT_WITH_APPRO);
        assertThat(testCashier.getThemeColor()).isEqualTo(DEFAULT_THEME_COLOR);
        assertThat(testCashier.isIsActivated()).isEqualTo(DEFAULT_IS_ACTIVATED);
        assertThat(testCashier.getCompanyID()).isEqualTo(DEFAULT_COMPANY_ID);
    }

    @Test
    @Transactional
    public void createCashierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cashierRepository.findAll().size();

        // Create the Cashier with an existing ID
        cashier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCashierMockMvc.perform(post("/api/cashiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashier)))
            .andExpect(status().isBadRequest());

        // Validate the Cashier in the database
        List<Cashier> cashierList = cashierRepository.findAll();
        assertThat(cashierList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashierRepository.findAll().size();
        // set the field null
        cashier.setName(null);

        // Create the Cashier, which fails.


        restCashierMockMvc.perform(post("/api/cashiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashier)))
            .andExpect(status().isBadRequest());

        List<Cashier> cashierList = cashierRepository.findAll();
        assertThat(cashierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCashiers() throws Exception {
        // Initialize the database
        cashierRepository.saveAndFlush(cashier);

        // Get all the cashierList
        restCashierMockMvc.perform(get("/api/cashiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cashier.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].withTicket").value(hasItem(DEFAULT_WITH_TICKET.booleanValue())))
            .andExpect(jsonPath("$.[*].withTVA").value(hasItem(DEFAULT_WITH_TVA.booleanValue())))
            .andExpect(jsonPath("$.[*].withTax").value(hasItem(DEFAULT_WITH_TAX.booleanValue())))
            .andExpect(jsonPath("$.[*].withAppro").value(hasItem(DEFAULT_WITH_APPRO.booleanValue())))
            .andExpect(jsonPath("$.[*].themeColor").value(hasItem(DEFAULT_THEME_COLOR)))
            .andExpect(jsonPath("$.[*].isActivated").value(hasItem(DEFAULT_IS_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].companyID").value(hasItem(DEFAULT_COMPANY_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getCashier() throws Exception {
        // Initialize the database
        cashierRepository.saveAndFlush(cashier);

        // Get the cashier
        restCashierMockMvc.perform(get("/api/cashiers/{id}", cashier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cashier.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.withTicket").value(DEFAULT_WITH_TICKET.booleanValue()))
            .andExpect(jsonPath("$.withTVA").value(DEFAULT_WITH_TVA.booleanValue()))
            .andExpect(jsonPath("$.withTax").value(DEFAULT_WITH_TAX.booleanValue()))
            .andExpect(jsonPath("$.withAppro").value(DEFAULT_WITH_APPRO.booleanValue()))
            .andExpect(jsonPath("$.themeColor").value(DEFAULT_THEME_COLOR))
            .andExpect(jsonPath("$.isActivated").value(DEFAULT_IS_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.companyID").value(DEFAULT_COMPANY_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCashier() throws Exception {
        // Get the cashier
        restCashierMockMvc.perform(get("/api/cashiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCashier() throws Exception {
        // Initialize the database
        cashierService.save(cashier);

        int databaseSizeBeforeUpdate = cashierRepository.findAll().size();

        // Update the cashier
        Cashier updatedCashier = cashierRepository.findById(cashier.getId()).get();
        // Disconnect from session so that the updates on updatedCashier are not directly saved in db
        em.detach(updatedCashier);
        updatedCashier
            .name(UPDATED_NAME)
            .withTicket(UPDATED_WITH_TICKET)
            .withTVA(UPDATED_WITH_TVA)
            .withTax(UPDATED_WITH_TAX)
            .withAppro(UPDATED_WITH_APPRO)
            .themeColor(UPDATED_THEME_COLOR)
            .isActivated(UPDATED_IS_ACTIVATED)
            .companyID(UPDATED_COMPANY_ID);

        restCashierMockMvc.perform(put("/api/cashiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCashier)))
            .andExpect(status().isOk());

        // Validate the Cashier in the database
        List<Cashier> cashierList = cashierRepository.findAll();
        assertThat(cashierList).hasSize(databaseSizeBeforeUpdate);
        Cashier testCashier = cashierList.get(cashierList.size() - 1);
        assertThat(testCashier.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCashier.isWithTicket()).isEqualTo(UPDATED_WITH_TICKET);
        assertThat(testCashier.isWithTVA()).isEqualTo(UPDATED_WITH_TVA);
        assertThat(testCashier.isWithTax()).isEqualTo(UPDATED_WITH_TAX);
        assertThat(testCashier.isWithAppro()).isEqualTo(UPDATED_WITH_APPRO);
        assertThat(testCashier.getThemeColor()).isEqualTo(UPDATED_THEME_COLOR);
        assertThat(testCashier.isIsActivated()).isEqualTo(UPDATED_IS_ACTIVATED);
        assertThat(testCashier.getCompanyID()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingCashier() throws Exception {
        int databaseSizeBeforeUpdate = cashierRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCashierMockMvc.perform(put("/api/cashiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashier)))
            .andExpect(status().isBadRequest());

        // Validate the Cashier in the database
        List<Cashier> cashierList = cashierRepository.findAll();
        assertThat(cashierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCashier() throws Exception {
        // Initialize the database
        cashierService.save(cashier);

        int databaseSizeBeforeDelete = cashierRepository.findAll().size();

        // Delete the cashier
        restCashierMockMvc.perform(delete("/api/cashiers/{id}", cashier.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cashier> cashierList = cashierRepository.findAll();
        assertThat(cashierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
