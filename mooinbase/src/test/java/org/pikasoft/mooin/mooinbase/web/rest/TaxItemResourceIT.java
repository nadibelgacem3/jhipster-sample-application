package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.MooinbaseApp;
import org.pikasoft.mooin.mooinbase.domain.TaxItem;
import org.pikasoft.mooin.mooinbase.repository.TaxItemRepository;
import org.pikasoft.mooin.mooinbase.service.TaxItemService;

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
 * Integration tests for the {@link TaxItemResource} REST controller.
 */
@SpringBootTest(classes = MooinbaseApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaxItemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_VALUED = false;
    private static final Boolean UPDATED_IS_VALUED = true;

    private static final Boolean DEFAULT_IS_PERCENTAGE = false;
    private static final Boolean UPDATED_IS_PERCENTAGE = true;

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final Long DEFAULT_TAX_ID = 1L;
    private static final Long UPDATED_TAX_ID = 2L;

    @Autowired
    private TaxItemRepository taxItemRepository;

    @Autowired
    private TaxItemService taxItemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaxItemMockMvc;

    private TaxItem taxItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxItem createEntity(EntityManager em) {
        TaxItem taxItem = new TaxItem()
            .name(DEFAULT_NAME)
            .isValued(DEFAULT_IS_VALUED)
            .isPercentage(DEFAULT_IS_PERCENTAGE)
            .value(DEFAULT_VALUE)
            .companyID(DEFAULT_COMPANY_ID)
            .taxID(DEFAULT_TAX_ID);
        return taxItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxItem createUpdatedEntity(EntityManager em) {
        TaxItem taxItem = new TaxItem()
            .name(UPDATED_NAME)
            .isValued(UPDATED_IS_VALUED)
            .isPercentage(UPDATED_IS_PERCENTAGE)
            .value(UPDATED_VALUE)
            .companyID(UPDATED_COMPANY_ID)
            .taxID(UPDATED_TAX_ID);
        return taxItem;
    }

    @BeforeEach
    public void initTest() {
        taxItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaxItem() throws Exception {
        int databaseSizeBeforeCreate = taxItemRepository.findAll().size();
        // Create the TaxItem
        restTaxItemMockMvc.perform(post("/api/tax-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxItem)))
            .andExpect(status().isCreated());

        // Validate the TaxItem in the database
        List<TaxItem> taxItemList = taxItemRepository.findAll();
        assertThat(taxItemList).hasSize(databaseSizeBeforeCreate + 1);
        TaxItem testTaxItem = taxItemList.get(taxItemList.size() - 1);
        assertThat(testTaxItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaxItem.isIsValued()).isEqualTo(DEFAULT_IS_VALUED);
        assertThat(testTaxItem.isIsPercentage()).isEqualTo(DEFAULT_IS_PERCENTAGE);
        assertThat(testTaxItem.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testTaxItem.getCompanyID()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testTaxItem.getTaxID()).isEqualTo(DEFAULT_TAX_ID);
    }

    @Test
    @Transactional
    public void createTaxItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxItemRepository.findAll().size();

        // Create the TaxItem with an existing ID
        taxItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxItemMockMvc.perform(post("/api/tax-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxItem)))
            .andExpect(status().isBadRequest());

        // Validate the TaxItem in the database
        List<TaxItem> taxItemList = taxItemRepository.findAll();
        assertThat(taxItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxItemRepository.findAll().size();
        // set the field null
        taxItem.setName(null);

        // Create the TaxItem, which fails.


        restTaxItemMockMvc.perform(post("/api/tax-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxItem)))
            .andExpect(status().isBadRequest());

        List<TaxItem> taxItemList = taxItemRepository.findAll();
        assertThat(taxItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsValuedIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxItemRepository.findAll().size();
        // set the field null
        taxItem.setIsValued(null);

        // Create the TaxItem, which fails.


        restTaxItemMockMvc.perform(post("/api/tax-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxItem)))
            .andExpect(status().isBadRequest());

        List<TaxItem> taxItemList = taxItemRepository.findAll();
        assertThat(taxItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsPercentageIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxItemRepository.findAll().size();
        // set the field null
        taxItem.setIsPercentage(null);

        // Create the TaxItem, which fails.


        restTaxItemMockMvc.perform(post("/api/tax-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxItem)))
            .andExpect(status().isBadRequest());

        List<TaxItem> taxItemList = taxItemRepository.findAll();
        assertThat(taxItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxItemRepository.findAll().size();
        // set the field null
        taxItem.setValue(null);

        // Create the TaxItem, which fails.


        restTaxItemMockMvc.perform(post("/api/tax-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxItem)))
            .andExpect(status().isBadRequest());

        List<TaxItem> taxItemList = taxItemRepository.findAll();
        assertThat(taxItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaxItems() throws Exception {
        // Initialize the database
        taxItemRepository.saveAndFlush(taxItem);

        // Get all the taxItemList
        restTaxItemMockMvc.perform(get("/api/tax-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isValued").value(hasItem(DEFAULT_IS_VALUED.booleanValue())))
            .andExpect(jsonPath("$.[*].isPercentage").value(hasItem(DEFAULT_IS_PERCENTAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].companyID").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].taxID").value(hasItem(DEFAULT_TAX_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getTaxItem() throws Exception {
        // Initialize the database
        taxItemRepository.saveAndFlush(taxItem);

        // Get the taxItem
        restTaxItemMockMvc.perform(get("/api/tax-items/{id}", taxItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taxItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isValued").value(DEFAULT_IS_VALUED.booleanValue()))
            .andExpect(jsonPath("$.isPercentage").value(DEFAULT_IS_PERCENTAGE.booleanValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.companyID").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.taxID").value(DEFAULT_TAX_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTaxItem() throws Exception {
        // Get the taxItem
        restTaxItemMockMvc.perform(get("/api/tax-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxItem() throws Exception {
        // Initialize the database
        taxItemService.save(taxItem);

        int databaseSizeBeforeUpdate = taxItemRepository.findAll().size();

        // Update the taxItem
        TaxItem updatedTaxItem = taxItemRepository.findById(taxItem.getId()).get();
        // Disconnect from session so that the updates on updatedTaxItem are not directly saved in db
        em.detach(updatedTaxItem);
        updatedTaxItem
            .name(UPDATED_NAME)
            .isValued(UPDATED_IS_VALUED)
            .isPercentage(UPDATED_IS_PERCENTAGE)
            .value(UPDATED_VALUE)
            .companyID(UPDATED_COMPANY_ID)
            .taxID(UPDATED_TAX_ID);

        restTaxItemMockMvc.perform(put("/api/tax-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaxItem)))
            .andExpect(status().isOk());

        // Validate the TaxItem in the database
        List<TaxItem> taxItemList = taxItemRepository.findAll();
        assertThat(taxItemList).hasSize(databaseSizeBeforeUpdate);
        TaxItem testTaxItem = taxItemList.get(taxItemList.size() - 1);
        assertThat(testTaxItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaxItem.isIsValued()).isEqualTo(UPDATED_IS_VALUED);
        assertThat(testTaxItem.isIsPercentage()).isEqualTo(UPDATED_IS_PERCENTAGE);
        assertThat(testTaxItem.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTaxItem.getCompanyID()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testTaxItem.getTaxID()).isEqualTo(UPDATED_TAX_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTaxItem() throws Exception {
        int databaseSizeBeforeUpdate = taxItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxItemMockMvc.perform(put("/api/tax-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxItem)))
            .andExpect(status().isBadRequest());

        // Validate the TaxItem in the database
        List<TaxItem> taxItemList = taxItemRepository.findAll();
        assertThat(taxItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaxItem() throws Exception {
        // Initialize the database
        taxItemService.save(taxItem);

        int databaseSizeBeforeDelete = taxItemRepository.findAll().size();

        // Delete the taxItem
        restTaxItemMockMvc.perform(delete("/api/tax-items/{id}", taxItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaxItem> taxItemList = taxItemRepository.findAll();
        assertThat(taxItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
