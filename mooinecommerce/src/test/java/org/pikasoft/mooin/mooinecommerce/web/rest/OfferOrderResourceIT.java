package org.pikasoft.mooin.mooinecommerce.web.rest;

import org.pikasoft.mooin.mooinecommerce.MooinecommerceApp;
import org.pikasoft.mooin.mooinecommerce.domain.OfferOrder;
import org.pikasoft.mooin.mooinecommerce.repository.OfferOrderRepository;
import org.pikasoft.mooin.mooinecommerce.service.OfferOrderService;

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

import org.pikasoft.mooin.mooinecommerce.domain.enumeration.OrderStatus;
/**
 * Integration tests for the {@link OfferOrderResource} REST controller.
 */
@SpringBootTest(classes = MooinecommerceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OfferOrderResourceIT {

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_HT = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_HT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TOTAL_TVA = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_TVA = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TOTA_TTC = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTA_TTC = new BigDecimal(1);

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final OrderStatus DEFAULT_STATUS = OrderStatus.DRAFT;
    private static final OrderStatus UPDATED_STATUS = OrderStatus.COMPLETED;

    private static final Long DEFAULT_REQUESTER_OFFER_ID = 1L;
    private static final Long UPDATED_REQUESTER_OFFER_ID = 2L;

    @Autowired
    private OfferOrderRepository offerOrderRepository;

    @Autowired
    private OfferOrderService offerOrderService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfferOrderMockMvc;

    private OfferOrder offerOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfferOrder createEntity(EntityManager em) {
        OfferOrder offerOrder = new OfferOrder()
            .quantity(DEFAULT_QUANTITY)
            .totalHT(DEFAULT_TOTAL_HT)
            .totalTVA(DEFAULT_TOTAL_TVA)
            .totaTTC(DEFAULT_TOTA_TTC)
            .date(DEFAULT_DATE)
            .status(DEFAULT_STATUS)
            .requesterOfferID(DEFAULT_REQUESTER_OFFER_ID);
        return offerOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfferOrder createUpdatedEntity(EntityManager em) {
        OfferOrder offerOrder = new OfferOrder()
            .quantity(UPDATED_QUANTITY)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC)
            .date(UPDATED_DATE)
            .status(UPDATED_STATUS)
            .requesterOfferID(UPDATED_REQUESTER_OFFER_ID);
        return offerOrder;
    }

    @BeforeEach
    public void initTest() {
        offerOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createOfferOrder() throws Exception {
        int databaseSizeBeforeCreate = offerOrderRepository.findAll().size();
        // Create the OfferOrder
        restOfferOrderMockMvc.perform(post("/api/offer-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerOrder)))
            .andExpect(status().isCreated());

        // Validate the OfferOrder in the database
        List<OfferOrder> offerOrderList = offerOrderRepository.findAll();
        assertThat(offerOrderList).hasSize(databaseSizeBeforeCreate + 1);
        OfferOrder testOfferOrder = offerOrderList.get(offerOrderList.size() - 1);
        assertThat(testOfferOrder.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOfferOrder.getTotalHT()).isEqualTo(DEFAULT_TOTAL_HT);
        assertThat(testOfferOrder.getTotalTVA()).isEqualTo(DEFAULT_TOTAL_TVA);
        assertThat(testOfferOrder.getTotaTTC()).isEqualTo(DEFAULT_TOTA_TTC);
        assertThat(testOfferOrder.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testOfferOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOfferOrder.getRequesterOfferID()).isEqualTo(DEFAULT_REQUESTER_OFFER_ID);
    }

    @Test
    @Transactional
    public void createOfferOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offerOrderRepository.findAll().size();

        // Create the OfferOrder with an existing ID
        offerOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferOrderMockMvc.perform(post("/api/offer-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerOrder)))
            .andExpect(status().isBadRequest());

        // Validate the OfferOrder in the database
        List<OfferOrder> offerOrderList = offerOrderRepository.findAll();
        assertThat(offerOrderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerOrderRepository.findAll().size();
        // set the field null
        offerOrder.setQuantity(null);

        // Create the OfferOrder, which fails.


        restOfferOrderMockMvc.perform(post("/api/offer-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerOrder)))
            .andExpect(status().isBadRequest());

        List<OfferOrder> offerOrderList = offerOrderRepository.findAll();
        assertThat(offerOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerOrderRepository.findAll().size();
        // set the field null
        offerOrder.setDate(null);

        // Create the OfferOrder, which fails.


        restOfferOrderMockMvc.perform(post("/api/offer-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerOrder)))
            .andExpect(status().isBadRequest());

        List<OfferOrder> offerOrderList = offerOrderRepository.findAll();
        assertThat(offerOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerOrderRepository.findAll().size();
        // set the field null
        offerOrder.setStatus(null);

        // Create the OfferOrder, which fails.


        restOfferOrderMockMvc.perform(post("/api/offer-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerOrder)))
            .andExpect(status().isBadRequest());

        List<OfferOrder> offerOrderList = offerOrderRepository.findAll();
        assertThat(offerOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOfferOrders() throws Exception {
        // Initialize the database
        offerOrderRepository.saveAndFlush(offerOrder);

        // Get all the offerOrderList
        restOfferOrderMockMvc.perform(get("/api/offer-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offerOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].totalHT").value(hasItem(DEFAULT_TOTAL_HT.intValue())))
            .andExpect(jsonPath("$.[*].totalTVA").value(hasItem(DEFAULT_TOTAL_TVA.intValue())))
            .andExpect(jsonPath("$.[*].totaTTC").value(hasItem(DEFAULT_TOTA_TTC.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].requesterOfferID").value(hasItem(DEFAULT_REQUESTER_OFFER_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getOfferOrder() throws Exception {
        // Initialize the database
        offerOrderRepository.saveAndFlush(offerOrder);

        // Get the offerOrder
        restOfferOrderMockMvc.perform(get("/api/offer-orders/{id}", offerOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offerOrder.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.totalHT").value(DEFAULT_TOTAL_HT.intValue()))
            .andExpect(jsonPath("$.totalTVA").value(DEFAULT_TOTAL_TVA.intValue()))
            .andExpect(jsonPath("$.totaTTC").value(DEFAULT_TOTA_TTC.intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.requesterOfferID").value(DEFAULT_REQUESTER_OFFER_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingOfferOrder() throws Exception {
        // Get the offerOrder
        restOfferOrderMockMvc.perform(get("/api/offer-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOfferOrder() throws Exception {
        // Initialize the database
        offerOrderService.save(offerOrder);

        int databaseSizeBeforeUpdate = offerOrderRepository.findAll().size();

        // Update the offerOrder
        OfferOrder updatedOfferOrder = offerOrderRepository.findById(offerOrder.getId()).get();
        // Disconnect from session so that the updates on updatedOfferOrder are not directly saved in db
        em.detach(updatedOfferOrder);
        updatedOfferOrder
            .quantity(UPDATED_QUANTITY)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC)
            .date(UPDATED_DATE)
            .status(UPDATED_STATUS)
            .requesterOfferID(UPDATED_REQUESTER_OFFER_ID);

        restOfferOrderMockMvc.perform(put("/api/offer-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOfferOrder)))
            .andExpect(status().isOk());

        // Validate the OfferOrder in the database
        List<OfferOrder> offerOrderList = offerOrderRepository.findAll();
        assertThat(offerOrderList).hasSize(databaseSizeBeforeUpdate);
        OfferOrder testOfferOrder = offerOrderList.get(offerOrderList.size() - 1);
        assertThat(testOfferOrder.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOfferOrder.getTotalHT()).isEqualTo(UPDATED_TOTAL_HT);
        assertThat(testOfferOrder.getTotalTVA()).isEqualTo(UPDATED_TOTAL_TVA);
        assertThat(testOfferOrder.getTotaTTC()).isEqualTo(UPDATED_TOTA_TTC);
        assertThat(testOfferOrder.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testOfferOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOfferOrder.getRequesterOfferID()).isEqualTo(UPDATED_REQUESTER_OFFER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingOfferOrder() throws Exception {
        int databaseSizeBeforeUpdate = offerOrderRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferOrderMockMvc.perform(put("/api/offer-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerOrder)))
            .andExpect(status().isBadRequest());

        // Validate the OfferOrder in the database
        List<OfferOrder> offerOrderList = offerOrderRepository.findAll();
        assertThat(offerOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOfferOrder() throws Exception {
        // Initialize the database
        offerOrderService.save(offerOrder);

        int databaseSizeBeforeDelete = offerOrderRepository.findAll().size();

        // Delete the offerOrder
        restOfferOrderMockMvc.perform(delete("/api/offer-orders/{id}", offerOrder.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OfferOrder> offerOrderList = offerOrderRepository.findAll();
        assertThat(offerOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
