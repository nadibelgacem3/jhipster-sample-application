package org.pikasoft.mooin.mooincashier.web.rest;

import org.pikasoft.mooin.mooincashier.MooincashierApp;
import org.pikasoft.mooin.mooincashier.domain.CashierCostumer;
import org.pikasoft.mooin.mooincashier.repository.CashierCostumerRepository;
import org.pikasoft.mooin.mooincashier.service.CashierCostumerService;

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
 * Integration tests for the {@link CashierCostumerResource} REST controller.
 */
@SpringBootTest(classes = MooincashierApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CashierCostumerResourceIT {

    private static final String DEFAULT_REFERENCE = "SC901COM83";
    private static final String UPDATED_REFERENCE = "SC183COM35";

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

    private static final String DEFAULT_EMAIL = "(h?@s=PZD.CAE/";
    private static final String UPDATED_EMAIL = "c8_w!T@nVw.M{XV";

    @Autowired
    private CashierCostumerRepository cashierCostumerRepository;

    @Autowired
    private CashierCostumerService cashierCostumerService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCashierCostumerMockMvc;

    private CashierCostumer cashierCostumer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CashierCostumer createEntity(EntityManager em) {
        CashierCostumer cashierCostumer = new CashierCostumer()
            .reference(DEFAULT_REFERENCE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .phone1(DEFAULT_PHONE_1)
            .phone2(DEFAULT_PHONE_2)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .email(DEFAULT_EMAIL);
        return cashierCostumer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CashierCostumer createUpdatedEntity(EntityManager em) {
        CashierCostumer cashierCostumer = new CashierCostumer()
            .reference(UPDATED_REFERENCE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .email(UPDATED_EMAIL);
        return cashierCostumer;
    }

    @BeforeEach
    public void initTest() {
        cashierCostumer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCashierCostumer() throws Exception {
        int databaseSizeBeforeCreate = cashierCostumerRepository.findAll().size();
        // Create the CashierCostumer
        restCashierCostumerMockMvc.perform(post("/api/cashier-costumers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierCostumer)))
            .andExpect(status().isCreated());

        // Validate the CashierCostumer in the database
        List<CashierCostumer> cashierCostumerList = cashierCostumerRepository.findAll();
        assertThat(cashierCostumerList).hasSize(databaseSizeBeforeCreate + 1);
        CashierCostumer testCashierCostumer = cashierCostumerList.get(cashierCostumerList.size() - 1);
        assertThat(testCashierCostumer.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testCashierCostumer.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCashierCostumer.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCashierCostumer.getPhone1()).isEqualTo(DEFAULT_PHONE_1);
        assertThat(testCashierCostumer.getPhone2()).isEqualTo(DEFAULT_PHONE_2);
        assertThat(testCashierCostumer.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testCashierCostumer.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testCashierCostumer.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createCashierCostumerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cashierCostumerRepository.findAll().size();

        // Create the CashierCostumer with an existing ID
        cashierCostumer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCashierCostumerMockMvc.perform(post("/api/cashier-costumers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierCostumer)))
            .andExpect(status().isBadRequest());

        // Validate the CashierCostumer in the database
        List<CashierCostumer> cashierCostumerList = cashierCostumerRepository.findAll();
        assertThat(cashierCostumerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashierCostumerRepository.findAll().size();
        // set the field null
        cashierCostumer.setFirstName(null);

        // Create the CashierCostumer, which fails.


        restCashierCostumerMockMvc.perform(post("/api/cashier-costumers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierCostumer)))
            .andExpect(status().isBadRequest());

        List<CashierCostumer> cashierCostumerList = cashierCostumerRepository.findAll();
        assertThat(cashierCostumerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashierCostumerRepository.findAll().size();
        // set the field null
        cashierCostumer.setLastName(null);

        // Create the CashierCostumer, which fails.


        restCashierCostumerMockMvc.perform(post("/api/cashier-costumers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierCostumer)))
            .andExpect(status().isBadRequest());

        List<CashierCostumer> cashierCostumerList = cashierCostumerRepository.findAll();
        assertThat(cashierCostumerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCashierCostumers() throws Exception {
        // Initialize the database
        cashierCostumerRepository.saveAndFlush(cashierCostumer);

        // Get all the cashierCostumerList
        restCashierCostumerMockMvc.perform(get("/api/cashier-costumers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cashierCostumer.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1)))
            .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }
    
    @Test
    @Transactional
    public void getCashierCostumer() throws Exception {
        // Initialize the database
        cashierCostumerRepository.saveAndFlush(cashierCostumer);

        // Get the cashierCostumer
        restCashierCostumerMockMvc.perform(get("/api/cashier-costumers/{id}", cashierCostumer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cashierCostumer.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.phone1").value(DEFAULT_PHONE_1))
            .andExpect(jsonPath("$.phone2").value(DEFAULT_PHONE_2))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }
    @Test
    @Transactional
    public void getNonExistingCashierCostumer() throws Exception {
        // Get the cashierCostumer
        restCashierCostumerMockMvc.perform(get("/api/cashier-costumers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCashierCostumer() throws Exception {
        // Initialize the database
        cashierCostumerService.save(cashierCostumer);

        int databaseSizeBeforeUpdate = cashierCostumerRepository.findAll().size();

        // Update the cashierCostumer
        CashierCostumer updatedCashierCostumer = cashierCostumerRepository.findById(cashierCostumer.getId()).get();
        // Disconnect from session so that the updates on updatedCashierCostumer are not directly saved in db
        em.detach(updatedCashierCostumer);
        updatedCashierCostumer
            .reference(UPDATED_REFERENCE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .email(UPDATED_EMAIL);

        restCashierCostumerMockMvc.perform(put("/api/cashier-costumers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCashierCostumer)))
            .andExpect(status().isOk());

        // Validate the CashierCostumer in the database
        List<CashierCostumer> cashierCostumerList = cashierCostumerRepository.findAll();
        assertThat(cashierCostumerList).hasSize(databaseSizeBeforeUpdate);
        CashierCostumer testCashierCostumer = cashierCostumerList.get(cashierCostumerList.size() - 1);
        assertThat(testCashierCostumer.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testCashierCostumer.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCashierCostumer.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCashierCostumer.getPhone1()).isEqualTo(UPDATED_PHONE_1);
        assertThat(testCashierCostumer.getPhone2()).isEqualTo(UPDATED_PHONE_2);
        assertThat(testCashierCostumer.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCashierCostumer.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testCashierCostumer.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingCashierCostumer() throws Exception {
        int databaseSizeBeforeUpdate = cashierCostumerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCashierCostumerMockMvc.perform(put("/api/cashier-costumers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierCostumer)))
            .andExpect(status().isBadRequest());

        // Validate the CashierCostumer in the database
        List<CashierCostumer> cashierCostumerList = cashierCostumerRepository.findAll();
        assertThat(cashierCostumerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCashierCostumer() throws Exception {
        // Initialize the database
        cashierCostumerService.save(cashierCostumer);

        int databaseSizeBeforeDelete = cashierCostumerRepository.findAll().size();

        // Delete the cashierCostumer
        restCashierCostumerMockMvc.perform(delete("/api/cashier-costumers/{id}", cashierCostumer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CashierCostumer> cashierCostumerList = cashierCostumerRepository.findAll();
        assertThat(cashierCostumerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
