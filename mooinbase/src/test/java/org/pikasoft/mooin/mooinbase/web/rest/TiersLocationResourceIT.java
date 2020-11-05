package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.MooinbaseApp;
import org.pikasoft.mooin.mooinbase.domain.TiersLocation;
import org.pikasoft.mooin.mooinbase.repository.TiersLocationRepository;
import org.pikasoft.mooin.mooinbase.service.TiersLocationService;

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
 * Integration tests for the {@link TiersLocationResource} REST controller.
 */
@SpringBootTest(classes = MooinbaseApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TiersLocationResourceIT {

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
    private TiersLocationRepository tiersLocationRepository;

    @Autowired
    private TiersLocationService tiersLocationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTiersLocationMockMvc;

    private TiersLocation tiersLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TiersLocation createEntity(EntityManager em) {
        TiersLocation tiersLocation = new TiersLocation()
            .localNumber(DEFAULT_LOCAL_NUMBER)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .postalCode(DEFAULT_POSTAL_CODE)
            .city(DEFAULT_CITY)
            .stateProvince(DEFAULT_STATE_PROVINCE)
            .countryName(DEFAULT_COUNTRY_NAME)
            .flag(DEFAULT_FLAG)
            .flagContentType(DEFAULT_FLAG_CONTENT_TYPE);
        return tiersLocation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TiersLocation createUpdatedEntity(EntityManager em) {
        TiersLocation tiersLocation = new TiersLocation()
            .localNumber(UPDATED_LOCAL_NUMBER)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .countryName(UPDATED_COUNTRY_NAME)
            .flag(UPDATED_FLAG)
            .flagContentType(UPDATED_FLAG_CONTENT_TYPE);
        return tiersLocation;
    }

    @BeforeEach
    public void initTest() {
        tiersLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createTiersLocation() throws Exception {
        int databaseSizeBeforeCreate = tiersLocationRepository.findAll().size();
        // Create the TiersLocation
        restTiersLocationMockMvc.perform(post("/api/tiers-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiersLocation)))
            .andExpect(status().isCreated());

        // Validate the TiersLocation in the database
        List<TiersLocation> tiersLocationList = tiersLocationRepository.findAll();
        assertThat(tiersLocationList).hasSize(databaseSizeBeforeCreate + 1);
        TiersLocation testTiersLocation = tiersLocationList.get(tiersLocationList.size() - 1);
        assertThat(testTiersLocation.getLocalNumber()).isEqualTo(DEFAULT_LOCAL_NUMBER);
        assertThat(testTiersLocation.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testTiersLocation.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testTiersLocation.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testTiersLocation.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
        assertThat(testTiersLocation.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testTiersLocation.getFlag()).isEqualTo(DEFAULT_FLAG);
        assertThat(testTiersLocation.getFlagContentType()).isEqualTo(DEFAULT_FLAG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createTiersLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tiersLocationRepository.findAll().size();

        // Create the TiersLocation with an existing ID
        tiersLocation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTiersLocationMockMvc.perform(post("/api/tiers-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiersLocation)))
            .andExpect(status().isBadRequest());

        // Validate the TiersLocation in the database
        List<TiersLocation> tiersLocationList = tiersLocationRepository.findAll();
        assertThat(tiersLocationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTiersLocations() throws Exception {
        // Initialize the database
        tiersLocationRepository.saveAndFlush(tiersLocation);

        // Get all the tiersLocationList
        restTiersLocationMockMvc.perform(get("/api/tiers-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tiersLocation.getId().intValue())))
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
    public void getTiersLocation() throws Exception {
        // Initialize the database
        tiersLocationRepository.saveAndFlush(tiersLocation);

        // Get the tiersLocation
        restTiersLocationMockMvc.perform(get("/api/tiers-locations/{id}", tiersLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tiersLocation.getId().intValue()))
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
    public void getNonExistingTiersLocation() throws Exception {
        // Get the tiersLocation
        restTiersLocationMockMvc.perform(get("/api/tiers-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTiersLocation() throws Exception {
        // Initialize the database
        tiersLocationService.save(tiersLocation);

        int databaseSizeBeforeUpdate = tiersLocationRepository.findAll().size();

        // Update the tiersLocation
        TiersLocation updatedTiersLocation = tiersLocationRepository.findById(tiersLocation.getId()).get();
        // Disconnect from session so that the updates on updatedTiersLocation are not directly saved in db
        em.detach(updatedTiersLocation);
        updatedTiersLocation
            .localNumber(UPDATED_LOCAL_NUMBER)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .countryName(UPDATED_COUNTRY_NAME)
            .flag(UPDATED_FLAG)
            .flagContentType(UPDATED_FLAG_CONTENT_TYPE);

        restTiersLocationMockMvc.perform(put("/api/tiers-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTiersLocation)))
            .andExpect(status().isOk());

        // Validate the TiersLocation in the database
        List<TiersLocation> tiersLocationList = tiersLocationRepository.findAll();
        assertThat(tiersLocationList).hasSize(databaseSizeBeforeUpdate);
        TiersLocation testTiersLocation = tiersLocationList.get(tiersLocationList.size() - 1);
        assertThat(testTiersLocation.getLocalNumber()).isEqualTo(UPDATED_LOCAL_NUMBER);
        assertThat(testTiersLocation.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testTiersLocation.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testTiersLocation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testTiersLocation.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testTiersLocation.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testTiersLocation.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testTiersLocation.getFlagContentType()).isEqualTo(UPDATED_FLAG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTiersLocation() throws Exception {
        int databaseSizeBeforeUpdate = tiersLocationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTiersLocationMockMvc.perform(put("/api/tiers-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiersLocation)))
            .andExpect(status().isBadRequest());

        // Validate the TiersLocation in the database
        List<TiersLocation> tiersLocationList = tiersLocationRepository.findAll();
        assertThat(tiersLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTiersLocation() throws Exception {
        // Initialize the database
        tiersLocationService.save(tiersLocation);

        int databaseSizeBeforeDelete = tiersLocationRepository.findAll().size();

        // Delete the tiersLocation
        restTiersLocationMockMvc.perform(delete("/api/tiers-locations/{id}", tiersLocation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TiersLocation> tiersLocationList = tiersLocationRepository.findAll();
        assertThat(tiersLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
