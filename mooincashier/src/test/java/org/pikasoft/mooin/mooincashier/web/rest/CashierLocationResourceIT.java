package org.pikasoft.mooin.mooincashier.web.rest;

import org.pikasoft.mooin.mooincashier.MooincashierApp;
import org.pikasoft.mooin.mooincashier.domain.CashierLocation;
import org.pikasoft.mooin.mooincashier.repository.CashierLocationRepository;
import org.pikasoft.mooin.mooincashier.service.CashierLocationService;

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

/**
 * Integration tests for the {@link CashierLocationResource} REST controller.
 */
@SpringBootTest(classes = MooincashierApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CashierLocationResourceIT {

    private static final String DEFAULT_LOCAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FLAG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FLAG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FLAG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FLAG_CONTENT_TYPE = "image/png";

    @Autowired
    private CashierLocationRepository cashierLocationRepository;

    @Autowired
    private CashierLocationService cashierLocationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCashierLocationMockMvc;

    private CashierLocation cashierLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CashierLocation createEntity(EntityManager em) {
        CashierLocation cashierLocation = new CashierLocation()
            .localNumber(DEFAULT_LOCAL_NUMBER)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .postalCode(DEFAULT_POSTAL_CODE)
            .city(DEFAULT_CITY)
            .stateProvince(DEFAULT_STATE_PROVINCE)
            .countryName(DEFAULT_COUNTRY_NAME)
            .flag(DEFAULT_FLAG)
            .flagContentType(DEFAULT_FLAG_CONTENT_TYPE);
        return cashierLocation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CashierLocation createUpdatedEntity(EntityManager em) {
        CashierLocation cashierLocation = new CashierLocation()
            .localNumber(UPDATED_LOCAL_NUMBER)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .countryName(UPDATED_COUNTRY_NAME)
            .flag(UPDATED_FLAG)
            .flagContentType(UPDATED_FLAG_CONTENT_TYPE);
        return cashierLocation;
    }

    @BeforeEach
    public void initTest() {
        cashierLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createCashierLocation() throws Exception {
        int databaseSizeBeforeCreate = cashierLocationRepository.findAll().size();
        // Create the CashierLocation
        restCashierLocationMockMvc.perform(post("/api/cashier-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierLocation)))
            .andExpect(status().isCreated());

        // Validate the CashierLocation in the database
        List<CashierLocation> cashierLocationList = cashierLocationRepository.findAll();
        assertThat(cashierLocationList).hasSize(databaseSizeBeforeCreate + 1);
        CashierLocation testCashierLocation = cashierLocationList.get(cashierLocationList.size() - 1);
        assertThat(testCashierLocation.getLocalNumber()).isEqualTo(DEFAULT_LOCAL_NUMBER);
        assertThat(testCashierLocation.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testCashierLocation.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testCashierLocation.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCashierLocation.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
        assertThat(testCashierLocation.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testCashierLocation.getFlag()).isEqualTo(DEFAULT_FLAG);
        assertThat(testCashierLocation.getFlagContentType()).isEqualTo(DEFAULT_FLAG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createCashierLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cashierLocationRepository.findAll().size();

        // Create the CashierLocation with an existing ID
        cashierLocation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCashierLocationMockMvc.perform(post("/api/cashier-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierLocation)))
            .andExpect(status().isBadRequest());

        // Validate the CashierLocation in the database
        List<CashierLocation> cashierLocationList = cashierLocationRepository.findAll();
        assertThat(cashierLocationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCashierLocations() throws Exception {
        // Initialize the database
        cashierLocationRepository.saveAndFlush(cashierLocation);

        // Get all the cashierLocationList
        restCashierLocationMockMvc.perform(get("/api/cashier-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cashierLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].localNumber").value(hasItem(DEFAULT_LOCAL_NUMBER)))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE)))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].flagContentType").value(hasItem(DEFAULT_FLAG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(Base64Utils.encodeToString(DEFAULT_FLAG))));
    }
    
    @Test
    @Transactional
    public void getCashierLocation() throws Exception {
        // Initialize the database
        cashierLocationRepository.saveAndFlush(cashierLocation);

        // Get the cashierLocation
        restCashierLocationMockMvc.perform(get("/api/cashier-locations/{id}", cashierLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cashierLocation.getId().intValue()))
            .andExpect(jsonPath("$.localNumber").value(DEFAULT_LOCAL_NUMBER))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME))
            .andExpect(jsonPath("$.flagContentType").value(DEFAULT_FLAG_CONTENT_TYPE))
            .andExpect(jsonPath("$.flag").value(Base64Utils.encodeToString(DEFAULT_FLAG)));
    }
    @Test
    @Transactional
    public void getNonExistingCashierLocation() throws Exception {
        // Get the cashierLocation
        restCashierLocationMockMvc.perform(get("/api/cashier-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCashierLocation() throws Exception {
        // Initialize the database
        cashierLocationService.save(cashierLocation);

        int databaseSizeBeforeUpdate = cashierLocationRepository.findAll().size();

        // Update the cashierLocation
        CashierLocation updatedCashierLocation = cashierLocationRepository.findById(cashierLocation.getId()).get();
        // Disconnect from session so that the updates on updatedCashierLocation are not directly saved in db
        em.detach(updatedCashierLocation);
        updatedCashierLocation
            .localNumber(UPDATED_LOCAL_NUMBER)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .countryName(UPDATED_COUNTRY_NAME)
            .flag(UPDATED_FLAG)
            .flagContentType(UPDATED_FLAG_CONTENT_TYPE);

        restCashierLocationMockMvc.perform(put("/api/cashier-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCashierLocation)))
            .andExpect(status().isOk());

        // Validate the CashierLocation in the database
        List<CashierLocation> cashierLocationList = cashierLocationRepository.findAll();
        assertThat(cashierLocationList).hasSize(databaseSizeBeforeUpdate);
        CashierLocation testCashierLocation = cashierLocationList.get(cashierLocationList.size() - 1);
        assertThat(testCashierLocation.getLocalNumber()).isEqualTo(UPDATED_LOCAL_NUMBER);
        assertThat(testCashierLocation.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testCashierLocation.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testCashierLocation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCashierLocation.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testCashierLocation.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testCashierLocation.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testCashierLocation.getFlagContentType()).isEqualTo(UPDATED_FLAG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCashierLocation() throws Exception {
        int databaseSizeBeforeUpdate = cashierLocationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCashierLocationMockMvc.perform(put("/api/cashier-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierLocation)))
            .andExpect(status().isBadRequest());

        // Validate the CashierLocation in the database
        List<CashierLocation> cashierLocationList = cashierLocationRepository.findAll();
        assertThat(cashierLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCashierLocation() throws Exception {
        // Initialize the database
        cashierLocationService.save(cashierLocation);

        int databaseSizeBeforeDelete = cashierLocationRepository.findAll().size();

        // Delete the cashierLocation
        restCashierLocationMockMvc.perform(delete("/api/cashier-locations/{id}", cashierLocation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CashierLocation> cashierLocationList = cashierLocationRepository.findAll();
        assertThat(cashierLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
