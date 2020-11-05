package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.MooinbaseApp;
import org.pikasoft.mooin.mooinbase.domain.Movement;
import org.pikasoft.mooin.mooinbase.repository.MovementRepository;
import org.pikasoft.mooin.mooinbase.service.MovementService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.pikasoft.mooin.mooinbase.domain.enumeration.MovementTypeEnum;
import org.pikasoft.mooin.mooinbase.domain.enumeration.MovementReasonEnum;
/**
 * Integration tests for the {@link MovementResource} REST controller.
 */
@SpringBootTest(classes = MooinbaseApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MovementResourceIT {

    private static final MovementTypeEnum DEFAULT_TYPE = MovementTypeEnum.INPUT;
    private static final MovementTypeEnum UPDATED_TYPE = MovementTypeEnum.OUTPUT;

    private static final MovementReasonEnum DEFAULT_REASON = MovementReasonEnum.INVOICE;
    private static final MovementReasonEnum UPDATED_REASON = MovementReasonEnum.CAISSE;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_BILL_ID = 1L;
    private static final Long UPDATED_BILL_ID = 2L;

    private static final Long DEFAULT_TIERS_ID = 1L;
    private static final Long UPDATED_TIERS_ID = 2L;

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);

    private static final Long DEFAULT_COMPANY_USER_ID = 1L;
    private static final Long UPDATED_COMPANY_USER_ID = 2L;

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private MovementService movementService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMovementMockMvc;

    private Movement movement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movement createEntity(EntityManager em) {
        Movement movement = new Movement()
            .type(DEFAULT_TYPE)
            .reason(DEFAULT_REASON)
            .date(DEFAULT_DATE)
            .billID(DEFAULT_BILL_ID)
            .tiersID(DEFAULT_TIERS_ID)
            .quantity(DEFAULT_QUANTITY)
            .companyUserID(DEFAULT_COMPANY_USER_ID);
        return movement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movement createUpdatedEntity(EntityManager em) {
        Movement movement = new Movement()
            .type(UPDATED_TYPE)
            .reason(UPDATED_REASON)
            .date(UPDATED_DATE)
            .billID(UPDATED_BILL_ID)
            .tiersID(UPDATED_TIERS_ID)
            .quantity(UPDATED_QUANTITY)
            .companyUserID(UPDATED_COMPANY_USER_ID);
        return movement;
    }

    @BeforeEach
    public void initTest() {
        movement = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovement() throws Exception {
        int databaseSizeBeforeCreate = movementRepository.findAll().size();
        // Create the Movement
        restMovementMockMvc.perform(post("/api/movements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movement)))
            .andExpect(status().isCreated());

        // Validate the Movement in the database
        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeCreate + 1);
        Movement testMovement = movementList.get(movementList.size() - 1);
        assertThat(testMovement.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMovement.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testMovement.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMovement.getBillID()).isEqualTo(DEFAULT_BILL_ID);
        assertThat(testMovement.getTiersID()).isEqualTo(DEFAULT_TIERS_ID);
        assertThat(testMovement.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testMovement.getCompanyUserID()).isEqualTo(DEFAULT_COMPANY_USER_ID);
    }

    @Test
    @Transactional
    public void createMovementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movementRepository.findAll().size();

        // Create the Movement with an existing ID
        movement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovementMockMvc.perform(post("/api/movements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movement)))
            .andExpect(status().isBadRequest());

        // Validate the Movement in the database
        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = movementRepository.findAll().size();
        // set the field null
        movement.setType(null);

        // Create the Movement, which fails.


        restMovementMockMvc.perform(post("/api/movements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movement)))
            .andExpect(status().isBadRequest());

        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = movementRepository.findAll().size();
        // set the field null
        movement.setReason(null);

        // Create the Movement, which fails.


        restMovementMockMvc.perform(post("/api/movements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movement)))
            .andExpect(status().isBadRequest());

        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = movementRepository.findAll().size();
        // set the field null
        movement.setDate(null);

        // Create the Movement, which fails.


        restMovementMockMvc.perform(post("/api/movements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movement)))
            .andExpect(status().isBadRequest());

        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = movementRepository.findAll().size();
        // set the field null
        movement.setQuantity(null);

        // Create the Movement, which fails.


        restMovementMockMvc.perform(post("/api/movements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movement)))
            .andExpect(status().isBadRequest());

        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMovements() throws Exception {
        // Initialize the database
        movementRepository.saveAndFlush(movement);

        // Get all the movementList
        restMovementMockMvc.perform(get("/api/movements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movement.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].billID").value(hasItem(DEFAULT_BILL_ID.intValue())))
            .andExpect(jsonPath("$.[*].tiersID").value(hasItem(DEFAULT_TIERS_ID.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].companyUserID").value(hasItem(DEFAULT_COMPANY_USER_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getMovement() throws Exception {
        // Initialize the database
        movementRepository.saveAndFlush(movement);

        // Get the movement
        restMovementMockMvc.perform(get("/api/movements/{id}", movement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movement.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.billID").value(DEFAULT_BILL_ID.intValue()))
            .andExpect(jsonPath("$.tiersID").value(DEFAULT_TIERS_ID.intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.companyUserID").value(DEFAULT_COMPANY_USER_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingMovement() throws Exception {
        // Get the movement
        restMovementMockMvc.perform(get("/api/movements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovement() throws Exception {
        // Initialize the database
        movementService.save(movement);

        int databaseSizeBeforeUpdate = movementRepository.findAll().size();

        // Update the movement
        Movement updatedMovement = movementRepository.findById(movement.getId()).get();
        // Disconnect from session so that the updates on updatedMovement are not directly saved in db
        em.detach(updatedMovement);
        updatedMovement
            .type(UPDATED_TYPE)
            .reason(UPDATED_REASON)
            .date(UPDATED_DATE)
            .billID(UPDATED_BILL_ID)
            .tiersID(UPDATED_TIERS_ID)
            .quantity(UPDATED_QUANTITY)
            .companyUserID(UPDATED_COMPANY_USER_ID);

        restMovementMockMvc.perform(put("/api/movements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMovement)))
            .andExpect(status().isOk());

        // Validate the Movement in the database
        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeUpdate);
        Movement testMovement = movementList.get(movementList.size() - 1);
        assertThat(testMovement.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMovement.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testMovement.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMovement.getBillID()).isEqualTo(UPDATED_BILL_ID);
        assertThat(testMovement.getTiersID()).isEqualTo(UPDATED_TIERS_ID);
        assertThat(testMovement.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMovement.getCompanyUserID()).isEqualTo(UPDATED_COMPANY_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingMovement() throws Exception {
        int databaseSizeBeforeUpdate = movementRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovementMockMvc.perform(put("/api/movements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movement)))
            .andExpect(status().isBadRequest());

        // Validate the Movement in the database
        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMovement() throws Exception {
        // Initialize the database
        movementService.save(movement);

        int databaseSizeBeforeDelete = movementRepository.findAll().size();

        // Delete the movement
        restMovementMockMvc.perform(delete("/api/movements/{id}", movement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
