package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.MooincompaniesApp;
import org.pikasoft.mooin.mooincompanies.domain.EmployeeLocation;
import org.pikasoft.mooin.mooincompanies.repository.EmployeeLocationRepository;
import org.pikasoft.mooin.mooincompanies.service.EmployeeLocationService;

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
 * Integration tests for the {@link EmployeeLocationResource} REST controller.
 */
@SpringBootTest(classes = MooincompaniesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeLocationResourceIT {

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
    private EmployeeLocationRepository employeeLocationRepository;

    @Autowired
    private EmployeeLocationService employeeLocationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeLocationMockMvc;

    private EmployeeLocation employeeLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeLocation createEntity(EntityManager em) {
        EmployeeLocation employeeLocation = new EmployeeLocation()
            .localNumber(DEFAULT_LOCAL_NUMBER)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .postalCode(DEFAULT_POSTAL_CODE)
            .city(DEFAULT_CITY)
            .stateProvince(DEFAULT_STATE_PROVINCE)
            .countryName(DEFAULT_COUNTRY_NAME)
            .flag(DEFAULT_FLAG)
            .flagContentType(DEFAULT_FLAG_CONTENT_TYPE);
        return employeeLocation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeLocation createUpdatedEntity(EntityManager em) {
        EmployeeLocation employeeLocation = new EmployeeLocation()
            .localNumber(UPDATED_LOCAL_NUMBER)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .countryName(UPDATED_COUNTRY_NAME)
            .flag(UPDATED_FLAG)
            .flagContentType(UPDATED_FLAG_CONTENT_TYPE);
        return employeeLocation;
    }

    @BeforeEach
    public void initTest() {
        employeeLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeLocation() throws Exception {
        int databaseSizeBeforeCreate = employeeLocationRepository.findAll().size();
        // Create the EmployeeLocation
        restEmployeeLocationMockMvc.perform(post("/api/employee-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeLocation)))
            .andExpect(status().isCreated());

        // Validate the EmployeeLocation in the database
        List<EmployeeLocation> employeeLocationList = employeeLocationRepository.findAll();
        assertThat(employeeLocationList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeLocation testEmployeeLocation = employeeLocationList.get(employeeLocationList.size() - 1);
        assertThat(testEmployeeLocation.getLocalNumber()).isEqualTo(DEFAULT_LOCAL_NUMBER);
        assertThat(testEmployeeLocation.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testEmployeeLocation.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testEmployeeLocation.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testEmployeeLocation.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
        assertThat(testEmployeeLocation.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testEmployeeLocation.getFlag()).isEqualTo(DEFAULT_FLAG);
        assertThat(testEmployeeLocation.getFlagContentType()).isEqualTo(DEFAULT_FLAG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createEmployeeLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeLocationRepository.findAll().size();

        // Create the EmployeeLocation with an existing ID
        employeeLocation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeLocationMockMvc.perform(post("/api/employee-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeLocation)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeLocation in the database
        List<EmployeeLocation> employeeLocationList = employeeLocationRepository.findAll();
        assertThat(employeeLocationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployeeLocations() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get all the employeeLocationList
        restEmployeeLocationMockMvc.perform(get("/api/employee-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeLocation.getId().intValue())))
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
    public void getEmployeeLocation() throws Exception {
        // Initialize the database
        employeeLocationRepository.saveAndFlush(employeeLocation);

        // Get the employeeLocation
        restEmployeeLocationMockMvc.perform(get("/api/employee-locations/{id}", employeeLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeLocation.getId().intValue()))
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
    public void getNonExistingEmployeeLocation() throws Exception {
        // Get the employeeLocation
        restEmployeeLocationMockMvc.perform(get("/api/employee-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeLocation() throws Exception {
        // Initialize the database
        employeeLocationService.save(employeeLocation);

        int databaseSizeBeforeUpdate = employeeLocationRepository.findAll().size();

        // Update the employeeLocation
        EmployeeLocation updatedEmployeeLocation = employeeLocationRepository.findById(employeeLocation.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeLocation are not directly saved in db
        em.detach(updatedEmployeeLocation);
        updatedEmployeeLocation
            .localNumber(UPDATED_LOCAL_NUMBER)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .countryName(UPDATED_COUNTRY_NAME)
            .flag(UPDATED_FLAG)
            .flagContentType(UPDATED_FLAG_CONTENT_TYPE);

        restEmployeeLocationMockMvc.perform(put("/api/employee-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeLocation)))
            .andExpect(status().isOk());

        // Validate the EmployeeLocation in the database
        List<EmployeeLocation> employeeLocationList = employeeLocationRepository.findAll();
        assertThat(employeeLocationList).hasSize(databaseSizeBeforeUpdate);
        EmployeeLocation testEmployeeLocation = employeeLocationList.get(employeeLocationList.size() - 1);
        assertThat(testEmployeeLocation.getLocalNumber()).isEqualTo(UPDATED_LOCAL_NUMBER);
        assertThat(testEmployeeLocation.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testEmployeeLocation.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testEmployeeLocation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testEmployeeLocation.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testEmployeeLocation.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testEmployeeLocation.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testEmployeeLocation.getFlagContentType()).isEqualTo(UPDATED_FLAG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeLocation() throws Exception {
        int databaseSizeBeforeUpdate = employeeLocationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeLocationMockMvc.perform(put("/api/employee-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeLocation)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeLocation in the database
        List<EmployeeLocation> employeeLocationList = employeeLocationRepository.findAll();
        assertThat(employeeLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeLocation() throws Exception {
        // Initialize the database
        employeeLocationService.save(employeeLocation);

        int databaseSizeBeforeDelete = employeeLocationRepository.findAll().size();

        // Delete the employeeLocation
        restEmployeeLocationMockMvc.perform(delete("/api/employee-locations/{id}", employeeLocation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeLocation> employeeLocationList = employeeLocationRepository.findAll();
        assertThat(employeeLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
