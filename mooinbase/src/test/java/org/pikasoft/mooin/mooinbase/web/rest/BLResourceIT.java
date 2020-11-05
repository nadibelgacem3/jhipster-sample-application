package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.MooinbaseApp;
import org.pikasoft.mooin.mooinbase.domain.BL;
import org.pikasoft.mooin.mooinbase.repository.BLRepository;
import org.pikasoft.mooin.mooinbase.service.BLService;

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

import org.pikasoft.mooin.mooinbase.domain.enumeration.BLStatusEnum;
import org.pikasoft.mooin.mooinbase.domain.enumeration.BLTypeEnum;
/**
 * Integration tests for the {@link BLResource} REST controller.
 */
@SpringBootTest(classes = MooinbaseApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BLResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final BLStatusEnum DEFAULT_STATUS = BLStatusEnum.DRAFT;
    private static final BLStatusEnum UPDATED_STATUS = BLStatusEnum.FINALIZED;

    private static final BLTypeEnum DEFAULT_TYPE = BLTypeEnum.SALE;
    private static final BLTypeEnum UPDATED_TYPE = BLTypeEnum.PURCHASE;

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
    private BLRepository bLRepository;

    @Autowired
    private BLService bLService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBLMockMvc;

    private BL bL;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BL createEntity(EntityManager em) {
        BL bL = new BL()
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
        return bL;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BL createUpdatedEntity(EntityManager em) {
        BL bL = new BL()
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
        return bL;
    }

    @BeforeEach
    public void initTest() {
        bL = createEntity(em);
    }

    @Test
    @Transactional
    public void createBL() throws Exception {
        int databaseSizeBeforeCreate = bLRepository.findAll().size();
        // Create the BL
        restBLMockMvc.perform(post("/api/bls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bL)))
            .andExpect(status().isCreated());

        // Validate the BL in the database
        List<BL> bLList = bLRepository.findAll();
        assertThat(bLList).hasSize(databaseSizeBeforeCreate + 1);
        BL testBL = bLList.get(bLList.size() - 1);
        assertThat(testBL.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testBL.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testBL.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBL.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBL.getTotalHT()).isEqualTo(DEFAULT_TOTAL_HT);
        assertThat(testBL.getTotalTVA()).isEqualTo(DEFAULT_TOTAL_TVA);
        assertThat(testBL.getTotaTTC()).isEqualTo(DEFAULT_TOTA_TTC);
        assertThat(testBL.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testBL.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testBL.isIsConverted()).isEqualTo(DEFAULT_IS_CONVERTED);
        assertThat(testBL.getCompanyID()).isEqualTo(DEFAULT_COMPANY_ID);
    }

    @Test
    @Transactional
    public void createBLWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bLRepository.findAll().size();

        // Create the BL with an existing ID
        bL.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBLMockMvc.perform(post("/api/bls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bL)))
            .andExpect(status().isBadRequest());

        // Validate the BL in the database
        List<BL> bLList = bLRepository.findAll();
        assertThat(bLList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTotalHTIsRequired() throws Exception {
        int databaseSizeBeforeTest = bLRepository.findAll().size();
        // set the field null
        bL.setTotalHT(null);

        // Create the BL, which fails.


        restBLMockMvc.perform(post("/api/bls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bL)))
            .andExpect(status().isBadRequest());

        List<BL> bLList = bLRepository.findAll();
        assertThat(bLList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalTVAIsRequired() throws Exception {
        int databaseSizeBeforeTest = bLRepository.findAll().size();
        // set the field null
        bL.setTotalTVA(null);

        // Create the BL, which fails.


        restBLMockMvc.perform(post("/api/bls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bL)))
            .andExpect(status().isBadRequest());

        List<BL> bLList = bLRepository.findAll();
        assertThat(bLList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotaTTCIsRequired() throws Exception {
        int databaseSizeBeforeTest = bLRepository.findAll().size();
        // set the field null
        bL.setTotaTTC(null);

        // Create the BL, which fails.


        restBLMockMvc.perform(post("/api/bls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bL)))
            .andExpect(status().isBadRequest());

        List<BL> bLList = bLRepository.findAll();
        assertThat(bLList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bLRepository.findAll().size();
        // set the field null
        bL.setDate(null);

        // Create the BL, which fails.


        restBLMockMvc.perform(post("/api/bls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bL)))
            .andExpect(status().isBadRequest());

        List<BL> bLList = bLRepository.findAll();
        assertThat(bLList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBLS() throws Exception {
        // Initialize the database
        bLRepository.saveAndFlush(bL);

        // Get all the bLList
        restBLMockMvc.perform(get("/api/bls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bL.getId().intValue())))
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
    public void getBL() throws Exception {
        // Initialize the database
        bLRepository.saveAndFlush(bL);

        // Get the bL
        restBLMockMvc.perform(get("/api/bls/{id}", bL.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bL.getId().intValue()))
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
    public void getNonExistingBL() throws Exception {
        // Get the bL
        restBLMockMvc.perform(get("/api/bls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBL() throws Exception {
        // Initialize the database
        bLService.save(bL);

        int databaseSizeBeforeUpdate = bLRepository.findAll().size();

        // Update the bL
        BL updatedBL = bLRepository.findById(bL.getId()).get();
        // Disconnect from session so that the updates on updatedBL are not directly saved in db
        em.detach(updatedBL);
        updatedBL
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

        restBLMockMvc.perform(put("/api/bls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBL)))
            .andExpect(status().isOk());

        // Validate the BL in the database
        List<BL> bLList = bLRepository.findAll();
        assertThat(bLList).hasSize(databaseSizeBeforeUpdate);
        BL testBL = bLList.get(bLList.size() - 1);
        assertThat(testBL.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testBL.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testBL.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBL.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBL.getTotalHT()).isEqualTo(UPDATED_TOTAL_HT);
        assertThat(testBL.getTotalTVA()).isEqualTo(UPDATED_TOTAL_TVA);
        assertThat(testBL.getTotaTTC()).isEqualTo(UPDATED_TOTA_TTC);
        assertThat(testBL.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testBL.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testBL.isIsConverted()).isEqualTo(UPDATED_IS_CONVERTED);
        assertThat(testBL.getCompanyID()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingBL() throws Exception {
        int databaseSizeBeforeUpdate = bLRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBLMockMvc.perform(put("/api/bls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bL)))
            .andExpect(status().isBadRequest());

        // Validate the BL in the database
        List<BL> bLList = bLRepository.findAll();
        assertThat(bLList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBL() throws Exception {
        // Initialize the database
        bLService.save(bL);

        int databaseSizeBeforeDelete = bLRepository.findAll().size();

        // Delete the bL
        restBLMockMvc.perform(delete("/api/bls/{id}", bL.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BL> bLList = bLRepository.findAll();
        assertThat(bLList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
