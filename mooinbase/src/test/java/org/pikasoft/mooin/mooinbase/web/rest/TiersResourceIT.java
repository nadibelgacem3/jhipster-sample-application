package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.MooinbaseApp;
import org.pikasoft.mooin.mooinbase.domain.Tiers;
import org.pikasoft.mooin.mooinbase.repository.TiersRepository;
import org.pikasoft.mooin.mooinbase.service.TiersService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.pikasoft.mooin.mooinbase.domain.enumeration.TiersTypeEnum;
/**
 * Integration tests for the {@link TiersResource} REST controller.
 */
@SpringBootTest(classes = MooinbaseApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TiersResourceIT {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_2 = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_EMAIL = "x@G{21kV..Us";
    private static final String UPDATED_EMAIL = "Clh}@)Ao\"7R.1a1R{[";

    private static final TiersTypeEnum DEFAULT_TYPE = TiersTypeEnum.PERSON;
    private static final TiersTypeEnum UPDATED_TYPE = TiersTypeEnum.STE;

    private static final Boolean DEFAULT_IS_CUSTOMER = false;
    private static final Boolean UPDATED_IS_CUSTOMER = true;

    private static final Boolean DEFAULT_IS_SUPPLIER = false;
    private static final Boolean UPDATED_IS_SUPPLIER = true;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    @Autowired
    private TiersRepository tiersRepository;

    @Autowired
    private TiersService tiersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTiersMockMvc;

    private Tiers tiers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tiers createEntity(EntityManager em) {
        Tiers tiers = new Tiers()
            .reference(DEFAULT_REFERENCE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .phone1(DEFAULT_PHONE_1)
            .phone2(DEFAULT_PHONE_2)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .email(DEFAULT_EMAIL)
            .type(DEFAULT_TYPE)
            .isCustomer(DEFAULT_IS_CUSTOMER)
            .isSupplier(DEFAULT_IS_SUPPLIER)
            .companyID(DEFAULT_COMPANY_ID);
        return tiers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tiers createUpdatedEntity(EntityManager em) {
        Tiers tiers = new Tiers()
            .reference(UPDATED_REFERENCE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .type(UPDATED_TYPE)
            .isCustomer(UPDATED_IS_CUSTOMER)
            .isSupplier(UPDATED_IS_SUPPLIER)
            .companyID(UPDATED_COMPANY_ID);
        return tiers;
    }

    @BeforeEach
    public void initTest() {
        tiers = createEntity(em);
    }

    @Test
    @Transactional
    public void createTiers() throws Exception {
        int databaseSizeBeforeCreate = tiersRepository.findAll().size();
        // Create the Tiers
        restTiersMockMvc.perform(post("/api/tiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiers)))
            .andExpect(status().isCreated());

        // Validate the Tiers in the database
        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeCreate + 1);
        Tiers testTiers = tiersList.get(tiersList.size() - 1);
        assertThat(testTiers.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testTiers.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testTiers.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testTiers.getPhone1()).isEqualTo(DEFAULT_PHONE_1);
        assertThat(testTiers.getPhone2()).isEqualTo(DEFAULT_PHONE_2);
        assertThat(testTiers.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testTiers.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testTiers.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTiers.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTiers.isIsCustomer()).isEqualTo(DEFAULT_IS_CUSTOMER);
        assertThat(testTiers.isIsSupplier()).isEqualTo(DEFAULT_IS_SUPPLIER);
        assertThat(testTiers.getCompanyID()).isEqualTo(DEFAULT_COMPANY_ID);
    }

    @Test
    @Transactional
    public void createTiersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tiersRepository.findAll().size();

        // Create the Tiers with an existing ID
        tiers.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTiersMockMvc.perform(post("/api/tiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiers)))
            .andExpect(status().isBadRequest());

        // Validate the Tiers in the database
        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiersRepository.findAll().size();
        // set the field null
        tiers.setFirstName(null);

        // Create the Tiers, which fails.


        restTiersMockMvc.perform(post("/api/tiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiers)))
            .andExpect(status().isBadRequest());

        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiersRepository.findAll().size();
        // set the field null
        tiers.setLastName(null);

        // Create the Tiers, which fails.


        restTiersMockMvc.perform(post("/api/tiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiers)))
            .andExpect(status().isBadRequest());

        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiersRepository.findAll().size();
        // set the field null
        tiers.setEmail(null);

        // Create the Tiers, which fails.


        restTiersMockMvc.perform(post("/api/tiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiers)))
            .andExpect(status().isBadRequest());

        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsCustomerIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiersRepository.findAll().size();
        // set the field null
        tiers.setIsCustomer(null);

        // Create the Tiers, which fails.


        restTiersMockMvc.perform(post("/api/tiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiers)))
            .andExpect(status().isBadRequest());

        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsSupplierIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiersRepository.findAll().size();
        // set the field null
        tiers.setIsSupplier(null);

        // Create the Tiers, which fails.


        restTiersMockMvc.perform(post("/api/tiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiers)))
            .andExpect(status().isBadRequest());

        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTiers() throws Exception {
        // Initialize the database
        tiersRepository.saveAndFlush(tiers);

        // Get all the tiersList
        restTiersMockMvc.perform(get("/api/tiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tiers.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1)))
            .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isCustomer").value(hasItem(DEFAULT_IS_CUSTOMER.booleanValue())))
            .andExpect(jsonPath("$.[*].isSupplier").value(hasItem(DEFAULT_IS_SUPPLIER.booleanValue())))
            .andExpect(jsonPath("$.[*].companyID").value(hasItem(DEFAULT_COMPANY_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getTiers() throws Exception {
        // Initialize the database
        tiersRepository.saveAndFlush(tiers);

        // Get the tiers
        restTiersMockMvc.perform(get("/api/tiers/{id}", tiers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tiers.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.phone1").value(DEFAULT_PHONE_1))
            .andExpect(jsonPath("$.phone2").value(DEFAULT_PHONE_2))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.isCustomer").value(DEFAULT_IS_CUSTOMER.booleanValue()))
            .andExpect(jsonPath("$.isSupplier").value(DEFAULT_IS_SUPPLIER.booleanValue()))
            .andExpect(jsonPath("$.companyID").value(DEFAULT_COMPANY_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTiers() throws Exception {
        // Get the tiers
        restTiersMockMvc.perform(get("/api/tiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTiers() throws Exception {
        // Initialize the database
        tiersService.save(tiers);

        int databaseSizeBeforeUpdate = tiersRepository.findAll().size();

        // Update the tiers
        Tiers updatedTiers = tiersRepository.findById(tiers.getId()).get();
        // Disconnect from session so that the updates on updatedTiers are not directly saved in db
        em.detach(updatedTiers);
        updatedTiers
            .reference(UPDATED_REFERENCE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .type(UPDATED_TYPE)
            .isCustomer(UPDATED_IS_CUSTOMER)
            .isSupplier(UPDATED_IS_SUPPLIER)
            .companyID(UPDATED_COMPANY_ID);

        restTiersMockMvc.perform(put("/api/tiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTiers)))
            .andExpect(status().isOk());

        // Validate the Tiers in the database
        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeUpdate);
        Tiers testTiers = tiersList.get(tiersList.size() - 1);
        assertThat(testTiers.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testTiers.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTiers.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testTiers.getPhone1()).isEqualTo(UPDATED_PHONE_1);
        assertThat(testTiers.getPhone2()).isEqualTo(UPDATED_PHONE_2);
        assertThat(testTiers.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testTiers.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testTiers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTiers.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTiers.isIsCustomer()).isEqualTo(UPDATED_IS_CUSTOMER);
        assertThat(testTiers.isIsSupplier()).isEqualTo(UPDATED_IS_SUPPLIER);
        assertThat(testTiers.getCompanyID()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTiers() throws Exception {
        int databaseSizeBeforeUpdate = tiersRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTiersMockMvc.perform(put("/api/tiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiers)))
            .andExpect(status().isBadRequest());

        // Validate the Tiers in the database
        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTiers() throws Exception {
        // Initialize the database
        tiersService.save(tiers);

        int databaseSizeBeforeDelete = tiersRepository.findAll().size();

        // Delete the tiers
        restTiersMockMvc.perform(delete("/api/tiers/{id}", tiers.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
