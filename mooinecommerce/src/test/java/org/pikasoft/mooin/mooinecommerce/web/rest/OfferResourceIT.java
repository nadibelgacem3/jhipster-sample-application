package org.pikasoft.mooin.mooinecommerce.web.rest;

import org.pikasoft.mooin.mooinecommerce.MooinecommerceApp;
import org.pikasoft.mooin.mooinecommerce.domain.Offer;
import org.pikasoft.mooin.mooinecommerce.repository.OfferRepository;
import org.pikasoft.mooin.mooinecommerce.service.OfferService;

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
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.pikasoft.mooin.mooinecommerce.domain.enumeration.OfferStatus;
/**
 * Integration tests for the {@link OfferResource} REST controller.
 */
@SpringBootTest(classes = MooinecommerceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OfferResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_DATE_BEGIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_BEGIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_END = LocalDate.now(ZoneId.systemDefault());

    private static final Duration DEFAULT_DURATION = Duration.ofHours(6);
    private static final Duration UPDATED_DURATION = Duration.ofHours(12);

    private static final OfferStatus DEFAULT_STATUS = OfferStatus.AVAILABLE;
    private static final OfferStatus UPDATED_STATUS = OfferStatus.OUT_OF_STOCK;

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final BigDecimal DEFAULT_SALE_UNIT_PRICE_BEFORE_DISCOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_SALE_UNIT_PRICE_BEFORE_DISCOUNT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_DISCOUNT_RATE = new BigDecimal(0);
    private static final BigDecimal UPDATED_DISCOUNT_RATE = new BigDecimal(1);

    private static final BigDecimal DEFAULT_SALE_UNIT_PRICE_AFTER_DISCOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_SALE_UNIT_PRICE_AFTER_DISCOUNT = new BigDecimal(1);

    private static final Boolean DEFAULT_WITH_TVA = false;
    private static final Boolean UPDATED_WITH_TVA = true;

    private static final Boolean DEFAULT_WITH_TAX = false;
    private static final Boolean UPDATED_WITH_TAX = true;

    private static final Boolean DEFAULT_IS_ACTIVATED = false;
    private static final Boolean UPDATED_IS_ACTIVATED = true;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OfferService offerService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfferMockMvc;

    private Offer offer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createEntity(EntityManager em) {
        Offer offer = new Offer()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .dateBegin(DEFAULT_DATE_BEGIN)
            .dateEnd(DEFAULT_DATE_END)
            .duration(DEFAULT_DURATION)
            .status(DEFAULT_STATUS)
            .productId(DEFAULT_PRODUCT_ID)
            .saleUnitPriceBeforeDiscount(DEFAULT_SALE_UNIT_PRICE_BEFORE_DISCOUNT)
            .discountRate(DEFAULT_DISCOUNT_RATE)
            .saleUnitPriceAfterDiscount(DEFAULT_SALE_UNIT_PRICE_AFTER_DISCOUNT)
            .withTVA(DEFAULT_WITH_TVA)
            .withTax(DEFAULT_WITH_TAX)
            .isActivated(DEFAULT_IS_ACTIVATED)
            .companyID(DEFAULT_COMPANY_ID);
        return offer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createUpdatedEntity(EntityManager em) {
        Offer offer = new Offer()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .dateBegin(UPDATED_DATE_BEGIN)
            .dateEnd(UPDATED_DATE_END)
            .duration(UPDATED_DURATION)
            .status(UPDATED_STATUS)
            .productId(UPDATED_PRODUCT_ID)
            .saleUnitPriceBeforeDiscount(UPDATED_SALE_UNIT_PRICE_BEFORE_DISCOUNT)
            .discountRate(UPDATED_DISCOUNT_RATE)
            .saleUnitPriceAfterDiscount(UPDATED_SALE_UNIT_PRICE_AFTER_DISCOUNT)
            .withTVA(UPDATED_WITH_TVA)
            .withTax(UPDATED_WITH_TAX)
            .isActivated(UPDATED_IS_ACTIVATED)
            .companyID(UPDATED_COMPANY_ID);
        return offer;
    }

    @BeforeEach
    public void initTest() {
        offer = createEntity(em);
    }

    @Test
    @Transactional
    public void createOffer() throws Exception {
        int databaseSizeBeforeCreate = offerRepository.findAll().size();
        // Create the Offer
        restOfferMockMvc.perform(post("/api/offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offer)))
            .andExpect(status().isCreated());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate + 1);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOffer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOffer.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testOffer.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testOffer.getDateBegin()).isEqualTo(DEFAULT_DATE_BEGIN);
        assertThat(testOffer.getDateEnd()).isEqualTo(DEFAULT_DATE_END);
        assertThat(testOffer.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testOffer.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOffer.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testOffer.getSaleUnitPriceBeforeDiscount()).isEqualTo(DEFAULT_SALE_UNIT_PRICE_BEFORE_DISCOUNT);
        assertThat(testOffer.getDiscountRate()).isEqualTo(DEFAULT_DISCOUNT_RATE);
        assertThat(testOffer.getSaleUnitPriceAfterDiscount()).isEqualTo(DEFAULT_SALE_UNIT_PRICE_AFTER_DISCOUNT);
        assertThat(testOffer.isWithTVA()).isEqualTo(DEFAULT_WITH_TVA);
        assertThat(testOffer.isWithTax()).isEqualTo(DEFAULT_WITH_TAX);
        assertThat(testOffer.isIsActivated()).isEqualTo(DEFAULT_IS_ACTIVATED);
        assertThat(testOffer.getCompanyID()).isEqualTo(DEFAULT_COMPANY_ID);
    }

    @Test
    @Transactional
    public void createOfferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offerRepository.findAll().size();

        // Create the Offer with an existing ID
        offer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferMockMvc.perform(post("/api/offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offer)))
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOffers() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList
        restOfferMockMvc.perform(get("/api/offers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].dateBegin").value(hasItem(DEFAULT_DATE_BEGIN.toString())))
            .andExpect(jsonPath("$.[*].dateEnd").value(hasItem(DEFAULT_DATE_END.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].saleUnitPriceBeforeDiscount").value(hasItem(DEFAULT_SALE_UNIT_PRICE_BEFORE_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].discountRate").value(hasItem(DEFAULT_DISCOUNT_RATE.intValue())))
            .andExpect(jsonPath("$.[*].saleUnitPriceAfterDiscount").value(hasItem(DEFAULT_SALE_UNIT_PRICE_AFTER_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].withTVA").value(hasItem(DEFAULT_WITH_TVA.booleanValue())))
            .andExpect(jsonPath("$.[*].withTax").value(hasItem(DEFAULT_WITH_TAX.booleanValue())))
            .andExpect(jsonPath("$.[*].isActivated").value(hasItem(DEFAULT_IS_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].companyID").value(hasItem(DEFAULT_COMPANY_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get the offer
        restOfferMockMvc.perform(get("/api/offers/{id}", offer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.dateBegin").value(DEFAULT_DATE_BEGIN.toString()))
            .andExpect(jsonPath("$.dateEnd").value(DEFAULT_DATE_END.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.saleUnitPriceBeforeDiscount").value(DEFAULT_SALE_UNIT_PRICE_BEFORE_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.discountRate").value(DEFAULT_DISCOUNT_RATE.intValue()))
            .andExpect(jsonPath("$.saleUnitPriceAfterDiscount").value(DEFAULT_SALE_UNIT_PRICE_AFTER_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.withTVA").value(DEFAULT_WITH_TVA.booleanValue()))
            .andExpect(jsonPath("$.withTax").value(DEFAULT_WITH_TAX.booleanValue()))
            .andExpect(jsonPath("$.isActivated").value(DEFAULT_IS_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.companyID").value(DEFAULT_COMPANY_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingOffer() throws Exception {
        // Get the offer
        restOfferMockMvc.perform(get("/api/offers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffer() throws Exception {
        // Initialize the database
        offerService.save(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer
        Offer updatedOffer = offerRepository.findById(offer.getId()).get();
        // Disconnect from session so that the updates on updatedOffer are not directly saved in db
        em.detach(updatedOffer);
        updatedOffer
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .dateBegin(UPDATED_DATE_BEGIN)
            .dateEnd(UPDATED_DATE_END)
            .duration(UPDATED_DURATION)
            .status(UPDATED_STATUS)
            .productId(UPDATED_PRODUCT_ID)
            .saleUnitPriceBeforeDiscount(UPDATED_SALE_UNIT_PRICE_BEFORE_DISCOUNT)
            .discountRate(UPDATED_DISCOUNT_RATE)
            .saleUnitPriceAfterDiscount(UPDATED_SALE_UNIT_PRICE_AFTER_DISCOUNT)
            .withTVA(UPDATED_WITH_TVA)
            .withTax(UPDATED_WITH_TAX)
            .isActivated(UPDATED_IS_ACTIVATED)
            .companyID(UPDATED_COMPANY_ID);

        restOfferMockMvc.perform(put("/api/offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOffer)))
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOffer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOffer.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testOffer.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testOffer.getDateBegin()).isEqualTo(UPDATED_DATE_BEGIN);
        assertThat(testOffer.getDateEnd()).isEqualTo(UPDATED_DATE_END);
        assertThat(testOffer.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testOffer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOffer.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testOffer.getSaleUnitPriceBeforeDiscount()).isEqualTo(UPDATED_SALE_UNIT_PRICE_BEFORE_DISCOUNT);
        assertThat(testOffer.getDiscountRate()).isEqualTo(UPDATED_DISCOUNT_RATE);
        assertThat(testOffer.getSaleUnitPriceAfterDiscount()).isEqualTo(UPDATED_SALE_UNIT_PRICE_AFTER_DISCOUNT);
        assertThat(testOffer.isWithTVA()).isEqualTo(UPDATED_WITH_TVA);
        assertThat(testOffer.isWithTax()).isEqualTo(UPDATED_WITH_TAX);
        assertThat(testOffer.isIsActivated()).isEqualTo(UPDATED_IS_ACTIVATED);
        assertThat(testOffer.getCompanyID()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferMockMvc.perform(put("/api/offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offer)))
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOffer() throws Exception {
        // Initialize the database
        offerService.save(offer);

        int databaseSizeBeforeDelete = offerRepository.findAll().size();

        // Delete the offer
        restOfferMockMvc.perform(delete("/api/offers/{id}", offer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
