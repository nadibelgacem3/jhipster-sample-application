package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.MooinbaseApp;
import org.pikasoft.mooin.mooinbase.domain.QuoteItem;
import org.pikasoft.mooin.mooinbase.repository.QuoteItemRepository;
import org.pikasoft.mooin.mooinbase.service.QuoteItemService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link QuoteItemResource} REST controller.
 */
@SpringBootTest(classes = MooinbaseApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class QuoteItemResourceIT {

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(0);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(1);

    private static final Integer DEFAULT_DISCOUNT_RATE = 0;
    private static final Integer UPDATED_DISCOUNT_RATE = 1;

    private static final BigDecimal DEFAULT_TOTAL_HT = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_HT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TOTAL_TVA = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_TVA = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TOTA_TTC = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTA_TTC = new BigDecimal(1);

    @Autowired
    private QuoteItemRepository quoteItemRepository;

    @Autowired
    private QuoteItemService quoteItemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuoteItemMockMvc;

    private QuoteItem quoteItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuoteItem createEntity(EntityManager em) {
        QuoteItem quoteItem = new QuoteItem()
            .quantity(DEFAULT_QUANTITY)
            .discountRate(DEFAULT_DISCOUNT_RATE)
            .totalHT(DEFAULT_TOTAL_HT)
            .totalTVA(DEFAULT_TOTAL_TVA)
            .totaTTC(DEFAULT_TOTA_TTC);
        return quoteItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuoteItem createUpdatedEntity(EntityManager em) {
        QuoteItem quoteItem = new QuoteItem()
            .quantity(UPDATED_QUANTITY)
            .discountRate(UPDATED_DISCOUNT_RATE)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC);
        return quoteItem;
    }

    @BeforeEach
    public void initTest() {
        quoteItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuoteItem() throws Exception {
        int databaseSizeBeforeCreate = quoteItemRepository.findAll().size();
        // Create the QuoteItem
        restQuoteItemMockMvc.perform(post("/api/quote-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quoteItem)))
            .andExpect(status().isCreated());

        // Validate the QuoteItem in the database
        List<QuoteItem> quoteItemList = quoteItemRepository.findAll();
        assertThat(quoteItemList).hasSize(databaseSizeBeforeCreate + 1);
        QuoteItem testQuoteItem = quoteItemList.get(quoteItemList.size() - 1);
        assertThat(testQuoteItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testQuoteItem.getDiscountRate()).isEqualTo(DEFAULT_DISCOUNT_RATE);
        assertThat(testQuoteItem.getTotalHT()).isEqualTo(DEFAULT_TOTAL_HT);
        assertThat(testQuoteItem.getTotalTVA()).isEqualTo(DEFAULT_TOTAL_TVA);
        assertThat(testQuoteItem.getTotaTTC()).isEqualTo(DEFAULT_TOTA_TTC);
    }

    @Test
    @Transactional
    public void createQuoteItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quoteItemRepository.findAll().size();

        // Create the QuoteItem with an existing ID
        quoteItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuoteItemMockMvc.perform(post("/api/quote-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quoteItem)))
            .andExpect(status().isBadRequest());

        // Validate the QuoteItem in the database
        List<QuoteItem> quoteItemList = quoteItemRepository.findAll();
        assertThat(quoteItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQuoteItems() throws Exception {
        // Initialize the database
        quoteItemRepository.saveAndFlush(quoteItem);

        // Get all the quoteItemList
        restQuoteItemMockMvc.perform(get("/api/quote-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quoteItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].discountRate").value(hasItem(DEFAULT_DISCOUNT_RATE)))
            .andExpect(jsonPath("$.[*].totalHT").value(hasItem(DEFAULT_TOTAL_HT.intValue())))
            .andExpect(jsonPath("$.[*].totalTVA").value(hasItem(DEFAULT_TOTAL_TVA.intValue())))
            .andExpect(jsonPath("$.[*].totaTTC").value(hasItem(DEFAULT_TOTA_TTC.intValue())));
    }
    
    @Test
    @Transactional
    public void getQuoteItem() throws Exception {
        // Initialize the database
        quoteItemRepository.saveAndFlush(quoteItem);

        // Get the quoteItem
        restQuoteItemMockMvc.perform(get("/api/quote-items/{id}", quoteItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quoteItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.discountRate").value(DEFAULT_DISCOUNT_RATE))
            .andExpect(jsonPath("$.totalHT").value(DEFAULT_TOTAL_HT.intValue()))
            .andExpect(jsonPath("$.totalTVA").value(DEFAULT_TOTAL_TVA.intValue()))
            .andExpect(jsonPath("$.totaTTC").value(DEFAULT_TOTA_TTC.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingQuoteItem() throws Exception {
        // Get the quoteItem
        restQuoteItemMockMvc.perform(get("/api/quote-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuoteItem() throws Exception {
        // Initialize the database
        quoteItemService.save(quoteItem);

        int databaseSizeBeforeUpdate = quoteItemRepository.findAll().size();

        // Update the quoteItem
        QuoteItem updatedQuoteItem = quoteItemRepository.findById(quoteItem.getId()).get();
        // Disconnect from session so that the updates on updatedQuoteItem are not directly saved in db
        em.detach(updatedQuoteItem);
        updatedQuoteItem
            .quantity(UPDATED_QUANTITY)
            .discountRate(UPDATED_DISCOUNT_RATE)
            .totalHT(UPDATED_TOTAL_HT)
            .totalTVA(UPDATED_TOTAL_TVA)
            .totaTTC(UPDATED_TOTA_TTC);

        restQuoteItemMockMvc.perform(put("/api/quote-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuoteItem)))
            .andExpect(status().isOk());

        // Validate the QuoteItem in the database
        List<QuoteItem> quoteItemList = quoteItemRepository.findAll();
        assertThat(quoteItemList).hasSize(databaseSizeBeforeUpdate);
        QuoteItem testQuoteItem = quoteItemList.get(quoteItemList.size() - 1);
        assertThat(testQuoteItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testQuoteItem.getDiscountRate()).isEqualTo(UPDATED_DISCOUNT_RATE);
        assertThat(testQuoteItem.getTotalHT()).isEqualTo(UPDATED_TOTAL_HT);
        assertThat(testQuoteItem.getTotalTVA()).isEqualTo(UPDATED_TOTAL_TVA);
        assertThat(testQuoteItem.getTotaTTC()).isEqualTo(UPDATED_TOTA_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingQuoteItem() throws Exception {
        int databaseSizeBeforeUpdate = quoteItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuoteItemMockMvc.perform(put("/api/quote-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quoteItem)))
            .andExpect(status().isBadRequest());

        // Validate the QuoteItem in the database
        List<QuoteItem> quoteItemList = quoteItemRepository.findAll();
        assertThat(quoteItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuoteItem() throws Exception {
        // Initialize the database
        quoteItemService.save(quoteItem);

        int databaseSizeBeforeDelete = quoteItemRepository.findAll().size();

        // Delete the quoteItem
        restQuoteItemMockMvc.perform(delete("/api/quote-items/{id}", quoteItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuoteItem> quoteItemList = quoteItemRepository.findAll();
        assertThat(quoteItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
