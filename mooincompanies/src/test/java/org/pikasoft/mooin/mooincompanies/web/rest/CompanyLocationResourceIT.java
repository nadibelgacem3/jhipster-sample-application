package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.MooincompaniesApp;
import org.pikasoft.mooin.mooincompanies.domain.CompanyLocation;
import org.pikasoft.mooin.mooincompanies.repository.CompanyLocationRepository;
import org.pikasoft.mooin.mooincompanies.service.CompanyLocationService;

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
 * Integration tests for the {@link CompanyLocationResource} REST controller.
 */
@SpringBootTest(classes = MooincompaniesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompanyLocationResourceIT {

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
    private CompanyLocationRepository companyLocationRepository;

    @Autowired
    private CompanyLocationService companyLocationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyLocationMockMvc;

    private CompanyLocation companyLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyLocation createEntity(EntityManager em) {
        CompanyLocation companyLocation = new CompanyLocation()
            .localNumber(DEFAULT_LOCAL_NUMBER)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .postalCode(DEFAULT_POSTAL_CODE)
            .city(DEFAULT_CITY)
            .stateProvince(DEFAULT_STATE_PROVINCE)
            .countryName(DEFAULT_COUNTRY_NAME)
            .flag(DEFAULT_FLAG)
            .flagContentType(DEFAULT_FLAG_CONTENT_TYPE);
        return companyLocation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyLocation createUpdatedEntity(EntityManager em) {
        CompanyLocation companyLocation = new CompanyLocation()
            .localNumber(UPDATED_LOCAL_NUMBER)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .countryName(UPDATED_COUNTRY_NAME)
            .flag(UPDATED_FLAG)
            .flagContentType(UPDATED_FLAG_CONTENT_TYPE);
        return companyLocation;
    }

    @BeforeEach
    public void initTest() {
        companyLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyLocation() throws Exception {
        int databaseSizeBeforeCreate = companyLocationRepository.findAll().size();
        // Create the CompanyLocation
        restCompanyLocationMockMvc.perform(post("/api/company-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyLocation)))
            .andExpect(status().isCreated());

        // Validate the CompanyLocation in the database
        List<CompanyLocation> companyLocationList = companyLocationRepository.findAll();
        assertThat(companyLocationList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyLocation testCompanyLocation = companyLocationList.get(companyLocationList.size() - 1);
        assertThat(testCompanyLocation.getLocalNumber()).isEqualTo(DEFAULT_LOCAL_NUMBER);
        assertThat(testCompanyLocation.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testCompanyLocation.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testCompanyLocation.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCompanyLocation.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
        assertThat(testCompanyLocation.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testCompanyLocation.getFlag()).isEqualTo(DEFAULT_FLAG);
        assertThat(testCompanyLocation.getFlagContentType()).isEqualTo(DEFAULT_FLAG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createCompanyLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyLocationRepository.findAll().size();

        // Create the CompanyLocation with an existing ID
        companyLocation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyLocationMockMvc.perform(post("/api/company-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyLocation)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyLocation in the database
        List<CompanyLocation> companyLocationList = companyLocationRepository.findAll();
        assertThat(companyLocationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompanyLocations() throws Exception {
        // Initialize the database
        companyLocationRepository.saveAndFlush(companyLocation);

        // Get all the companyLocationList
        restCompanyLocationMockMvc.perform(get("/api/company-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyLocation.getId().intValue())))
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
    public void getCompanyLocation() throws Exception {
        // Initialize the database
        companyLocationRepository.saveAndFlush(companyLocation);

        // Get the companyLocation
        restCompanyLocationMockMvc.perform(get("/api/company-locations/{id}", companyLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyLocation.getId().intValue()))
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
    public void getNonExistingCompanyLocation() throws Exception {
        // Get the companyLocation
        restCompanyLocationMockMvc.perform(get("/api/company-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyLocation() throws Exception {
        // Initialize the database
        companyLocationService.save(companyLocation);

        int databaseSizeBeforeUpdate = companyLocationRepository.findAll().size();

        // Update the companyLocation
        CompanyLocation updatedCompanyLocation = companyLocationRepository.findById(companyLocation.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyLocation are not directly saved in db
        em.detach(updatedCompanyLocation);
        updatedCompanyLocation
            .localNumber(UPDATED_LOCAL_NUMBER)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .countryName(UPDATED_COUNTRY_NAME)
            .flag(UPDATED_FLAG)
            .flagContentType(UPDATED_FLAG_CONTENT_TYPE);

        restCompanyLocationMockMvc.perform(put("/api/company-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyLocation)))
            .andExpect(status().isOk());

        // Validate the CompanyLocation in the database
        List<CompanyLocation> companyLocationList = companyLocationRepository.findAll();
        assertThat(companyLocationList).hasSize(databaseSizeBeforeUpdate);
        CompanyLocation testCompanyLocation = companyLocationList.get(companyLocationList.size() - 1);
        assertThat(testCompanyLocation.getLocalNumber()).isEqualTo(UPDATED_LOCAL_NUMBER);
        assertThat(testCompanyLocation.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testCompanyLocation.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testCompanyLocation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCompanyLocation.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testCompanyLocation.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testCompanyLocation.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testCompanyLocation.getFlagContentType()).isEqualTo(UPDATED_FLAG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyLocation() throws Exception {
        int databaseSizeBeforeUpdate = companyLocationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyLocationMockMvc.perform(put("/api/company-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyLocation)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyLocation in the database
        List<CompanyLocation> companyLocationList = companyLocationRepository.findAll();
        assertThat(companyLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyLocation() throws Exception {
        // Initialize the database
        companyLocationService.save(companyLocation);

        int databaseSizeBeforeDelete = companyLocationRepository.findAll().size();

        // Delete the companyLocation
        restCompanyLocationMockMvc.perform(delete("/api/company-locations/{id}", companyLocation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyLocation> companyLocationList = companyLocationRepository.findAll();
        assertThat(companyLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
