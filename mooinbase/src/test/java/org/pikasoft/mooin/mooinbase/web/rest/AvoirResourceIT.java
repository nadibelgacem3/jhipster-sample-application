package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.MooinbaseApp;
import org.pikasoft.mooin.mooinbase.domain.Avoir;
import org.pikasoft.mooin.mooinbase.repository.AvoirRepository;
import org.pikasoft.mooin.mooinbase.service.AvoirService;

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

import org.pikasoft.mooin.mooinbase.domain.enumeration.AvoirStatusEnum;
import org.pikasoft.mooin.mooinbase.domain.enumeration.AvoirTypeEnum;
/**
 * Integration tests for the {@link AvoirResource} REST controller.
 */
@SpringBootTest(classes = MooinbaseApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AvoirResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final AvoirStatusEnum DEFAULT_STATUS = AvoirStatusEnum.DRAFT;
    private static final AvoirStatusEnum UPDATED_STATUS = AvoirStatusEnum.FINALIZED;

    private static final AvoirTypeEnum DEFAULT_TYPE = AvoirTypeEnum.SALE;
    private static final AvoirTypeEnum UPDATED_TYPE = AvoirTypeEnum.PURCHASE;

    private static final BigDecimal DEFAULT_TOTAL_HT = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_HT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TOTAL_TVA = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_TVA = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TOTA_TTC = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTA_TTC = new BigDecimal(1);

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_CONVERTED = false;
    private static final Boolean UPDATED_IS_CONVERTED = true;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    @Autowired
    private AvoirRepository avoirRepository;

    @Autowired
    private AvoirService avoirService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvoirMockMvc;

    private Avoir avoir;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avoir createEntity(EntityManager em) {
        Avoir avoir = new Avoir()
            .number(DEFAULT_NUMBER)
            .reference(DEFAULT_REFERENCE)
            .status(DEFAULT_STATUS)
            .type(DEFAULT_TYPE)
            .totalHT(DEFAULT_TOTAL_HT)
            .totalTVA(DEFAULT_TOTAL_TVA)
            .totaTTC(DEFAULT_TOTA_TTC)
            .date(DEFAULT_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .isConverted(DEFAULT_IS_CONVERTED)
            .companyID(DEFAULT_COMPANY_ID);
        return avoir;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avoir createUpdatedEntity(EntityManager em) {
        Avoir avoir = new Avoir()
            .number(UPDATED_NUMBER)
            .reference(UPDATED_REFERENCE)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC)
            .date(UPDATED_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .isConverted(UPDATED_IS_CONVERTED)
            .companyID(UPDATED_COMPANY_ID);
        return avoir;
    }

    @BeforeEach
    public void initTest() {
        avoir = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvoir() throws Exception {
        int databaseSizeBeforeCreate = avoirRepository.findAll().size();
        // Create the Avoir
        restAvoirMockMvc.perform(post("/api/avoirs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avoir)))
            .andExpect(status().isCreated());

        // Validate the Avoir in the database
        List<Avoir> avoirList = avoirRepository.findAll();
        assertThat(avoirList).hasSize(databaseSizeBeforeCreate + 1);
        Avoir testAvoir = avoirList.get(avoirList.size() - 1);
        assertThat(testAvoir.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testAvoir.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testAvoir.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAvoir.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAvoir.getTotalHT()).isEqualTo(DEFAULT_TOTAL_HT);
        assertThat(testAvoir.getTotalTVA()).isEqualTo(DEFAULT_TOTAL_TVA);
        assertThat(testAvoir.getTotaTTC()).isEqualTo(DEFAULT_TOTA_TTC);
        assertThat(testAvoir.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAvoir.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testAvoir.isIsConverted()).isEqualTo(DEFAULT_IS_CONVERTED);
        assertThat(testAvoir.getCompanyID()).isEqualTo(DEFAULT_COMPANY_ID);
    }

    @Test
    @Transactional
    public void createAvoirWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avoirRepository.findAll().size();

        // Create the Avoir with an existing ID
        avoir.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvoirMockMvc.perform(post("/api/avoirs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avoir)))
            .andExpect(status().isBadRequest());

        // Validate the Avoir in the database
        List<Avoir> avoirList = avoirRepository.findAll();
        assertThat(avoirList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTotalHTIsRequired() throws Exception {
        int databaseSizeBeforeTest = avoirRepository.findAll().size();
        // set the field null
        avoir.setTotalHT(null);

        // Create the Avoir, which fails.


        restAvoirMockMvc.perform(post("/api/avoirs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avoir)))
            .andExpect(status().isBadRequest());

        List<Avoir> avoirList = avoirRepository.findAll();
        assertThat(avoirList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalTVAIsRequired() throws Exception {
        int databaseSizeBeforeTest = avoirRepository.findAll().size();
        // set the field null
        avoir.setTotalTVA(null);

        // Create the Avoir, which fails.


        restAvoirMockMvc.perform(post("/api/avoirs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avoir)))
            .andExpect(status().isBadRequest());

        List<Avoir> avoirList = avoirRepository.findAll();
        assertThat(avoirList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotaTTCIsRequired() throws Exception {
        int databaseSizeBeforeTest = avoirRepository.findAll().size();
        // set the field null
        avoir.setTotaTTC(null);

        // Create the Avoir, which fails.


        restAvoirMockMvc.perform(post("/api/avoirs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avoir)))
            .andExpect(status().isBadRequest());

        List<Avoir> avoirList = avoirRepository.findAll();
        assertThat(avoirList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = avoirRepository.findAll().size();
        // set the field null
        avoir.setDate(null);

        // Create the Avoir, which fails.


        restAvoirMockMvc.perform(post("/api/avoirs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avoir)))
            .andExpect(status().isBadRequest());

        List<Avoir> avoirList = avoirRepository.findAll();
        assertThat(avoirList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvoirs() throws Exception {
        // Initialize the database
        avoirRepository.saveAndFlush(avoir);

        // Get all the avoirList
        restAvoirMockMvc.perform(get("/api/avoirs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avoir.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].totalHT").value(hasItem(DEFAULT_TOTAL_HT.intValue())))
            .andExpect(jsonPath("$.[*].totalTVA").value(hasItem(DEFAULT_TOTAL_TVA.intValue())))
            .andExpect(jsonPath("$.[*].totaTTC").value(hasItem(DEFAULT_TOTA_TTC.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].isConverted").value(hasItem(DEFAULT_IS_CONVERTED.booleanValue())))
            .andExpect(jsonPath("$.[*].companyID").value(hasItem(DEFAULT_COMPANY_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getAvoir() throws Exception {
        // Initialize the database
        avoirRepository.saveAndFlush(avoir);

        // Get the avoir
        restAvoirMockMvc.perform(get("/api/avoirs/{id}", avoir.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avoir.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.totalHT").value(DEFAULT_TOTAL_HT.intValue()))
            .andExpect(jsonPath("$.totalTVA").value(DEFAULT_TOTAL_TVA.intValue()))
            .andExpect(jsonPath("$.totaTTC").value(DEFAULT_TOTA_TTC.intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.isConverted").value(DEFAULT_IS_CONVERTED.booleanValue()))
            .andExpect(jsonPath("$.companyID").value(DEFAULT_COMPANY_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAvoir() throws Exception {
        // Get the avoir
        restAvoirMockMvc.perform(get("/api/avoirs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvoir() throws Exception {
        // Initialize the database
        avoirService.save(avoir);

        int databaseSizeBeforeUpdate = avoirRepository.findAll().size();

        // Update the avoir
        Avoir updatedAvoir = avoirRepository.findById(avoir.getId()).get();
        // Disconnect from session so that the updates on updatedAvoir are not directly saved in db
        em.detach(updatedAvoir);
        updatedAvoir
            .number(UPDATED_NUMBER)
            .reference(UPDATED_REFERENCE)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC)
            .date(UPDATED_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .isConverted(UPDATED_IS_CONVERTED)
            .companyID(UPDATED_COMPANY_ID);

        restAvoirMockMvc.perform(put("/api/avoirs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvoir)))
            .andExpect(status().isOk());

        // Validate the Avoir in the database
        List<Avoir> avoirList = avoirRepository.findAll();
        assertThat(avoirList).hasSize(databaseSizeBeforeUpdate);
        Avoir testAvoir = avoirList.get(avoirList.size() - 1);
        assertThat(testAvoir.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testAvoir.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testAvoir.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAvoir.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAvoir.getTotalHT()).isEqualTo(UPDATED_TOTAL_HT);
        assertThat(testAvoir.getTotalTVA()).isEqualTo(UPDATED_TOTAL_TVA);
        assertThat(testAvoir.getTotaTTC()).isEqualTo(UPDATED_TOTA_TTC);
        assertThat(testAvoir.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAvoir.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testAvoir.isIsConverted()).isEqualTo(UPDATED_IS_CONVERTED);
        assertThat(testAvoir.getCompanyID()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingAvoir() throws Exception {
        int databaseSizeBeforeUpdate = avoirRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvoirMockMvc.perform(put("/api/avoirs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avoir)))
            .andExpect(status().isBadRequest());

        // Validate the Avoir in the database
        List<Avoir> avoirList = avoirRepository.findAll();
        assertThat(avoirList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAvoir() throws Exception {
        // Initialize the database
        avoirService.save(avoir);

        int databaseSizeBeforeDelete = avoirRepository.findAll().size();

        // Delete the avoir
        restAvoirMockMvc.perform(delete("/api/avoirs/{id}", avoir.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Avoir> avoirList = avoirRepository.findAll();
        assertThat(avoirList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
