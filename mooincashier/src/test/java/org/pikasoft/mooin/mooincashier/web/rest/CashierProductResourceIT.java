package org.pikasoft.mooin.mooincashier.web.rest;

import org.pikasoft.mooin.mooincashier.MooincashierApp;
import org.pikasoft.mooin.mooincashier.domain.CashierProduct;
import org.pikasoft.mooin.mooincashier.repository.CashierProductRepository;
import org.pikasoft.mooin.mooincashier.service.CashierProductService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CashierProductResource} REST controller.
 */
@SpringBootTest(classes = MooincashierApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CashierProductResourceIT {

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final String DEFAULT_CASHIER_PROD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CASHIER_PROD_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_CASHIER_PROD_QTY = 1D;
    private static final Double UPDATED_CASHIER_PROD_QTY = 2D;

    private static final Double DEFAULT_CASHIER_PROD_PURCHASE_UNIT_PRICE = 0D;
    private static final Double UPDATED_CASHIER_PROD_PURCHASE_UNIT_PRICE = 1D;

    private static final Double DEFAULT_CASHIER_PROD_SALE_UNIT_PRICE = 0D;
    private static final Double UPDATED_CASHIER_PROD_SALE_UNIT_PRICE = 1D;

    private static final Double DEFAULT_CASHIER_PROD_STOCK_LIMIT = 1D;
    private static final Double UPDATED_CASHIER_PROD_STOCK_LIMIT = 2D;

    private static final Boolean DEFAULT_CASHIER_PROD_STOCK_LIMIT_ALERT = false;
    private static final Boolean UPDATED_CASHIER_PROD_STOCK_LIMIT_ALERT = true;

    @Autowired
    private CashierProductRepository cashierProductRepository;

    @Autowired
    private CashierProductService cashierProductService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCashierProductMockMvc;

    private CashierProduct cashierProduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CashierProduct createEntity(EntityManager em) {
        CashierProduct cashierProduct = new CashierProduct()
            .productID(DEFAULT_PRODUCT_ID)
            .cashierProdName(DEFAULT_CASHIER_PROD_NAME)
            .cashierProdQty(DEFAULT_CASHIER_PROD_QTY)
            .cashierProdPurchaseUnitPrice(DEFAULT_CASHIER_PROD_PURCHASE_UNIT_PRICE)
            .cashierProdSaleUnitPrice(DEFAULT_CASHIER_PROD_SALE_UNIT_PRICE)
            .cashierProdStockLimit(DEFAULT_CASHIER_PROD_STOCK_LIMIT)
            .cashierProdStockLimitAlert(DEFAULT_CASHIER_PROD_STOCK_LIMIT_ALERT);
        return cashierProduct;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CashierProduct createUpdatedEntity(EntityManager em) {
        CashierProduct cashierProduct = new CashierProduct()
            .productID(UPDATED_PRODUCT_ID)
            .cashierProdName(UPDATED_CASHIER_PROD_NAME)
            .cashierProdQty(UPDATED_CASHIER_PROD_QTY)
            .cashierProdPurchaseUnitPrice(UPDATED_CASHIER_PROD_PURCHASE_UNIT_PRICE)
            .cashierProdSaleUnitPrice(UPDATED_CASHIER_PROD_SALE_UNIT_PRICE)
            .cashierProdStockLimit(UPDATED_CASHIER_PROD_STOCK_LIMIT)
            .cashierProdStockLimitAlert(UPDATED_CASHIER_PROD_STOCK_LIMIT_ALERT);
        return cashierProduct;
    }

    @BeforeEach
    public void initTest() {
        cashierProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createCashierProduct() throws Exception {
        int databaseSizeBeforeCreate = cashierProductRepository.findAll().size();
        // Create the CashierProduct
        restCashierProductMockMvc.perform(post("/api/cashier-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierProduct)))
            .andExpect(status().isCreated());

        // Validate the CashierProduct in the database
        List<CashierProduct> cashierProductList = cashierProductRepository.findAll();
        assertThat(cashierProductList).hasSize(databaseSizeBeforeCreate + 1);
        CashierProduct testCashierProduct = cashierProductList.get(cashierProductList.size() - 1);
        assertThat(testCashierProduct.getProductID()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testCashierProduct.getCashierProdName()).isEqualTo(DEFAULT_CASHIER_PROD_NAME);
        assertThat(testCashierProduct.getCashierProdQty()).isEqualTo(DEFAULT_CASHIER_PROD_QTY);
        assertThat(testCashierProduct.getCashierProdPurchaseUnitPrice()).isEqualTo(DEFAULT_CASHIER_PROD_PURCHASE_UNIT_PRICE);
        assertThat(testCashierProduct.getCashierProdSaleUnitPrice()).isEqualTo(DEFAULT_CASHIER_PROD_SALE_UNIT_PRICE);
        assertThat(testCashierProduct.getCashierProdStockLimit()).isEqualTo(DEFAULT_CASHIER_PROD_STOCK_LIMIT);
        assertThat(testCashierProduct.isCashierProdStockLimitAlert()).isEqualTo(DEFAULT_CASHIER_PROD_STOCK_LIMIT_ALERT);
    }

    @Test
    @Transactional
    public void createCashierProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cashierProductRepository.findAll().size();

        // Create the CashierProduct with an existing ID
        cashierProduct.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCashierProductMockMvc.perform(post("/api/cashier-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierProduct)))
            .andExpect(status().isBadRequest());

        // Validate the CashierProduct in the database
        List<CashierProduct> cashierProductList = cashierProductRepository.findAll();
        assertThat(cashierProductList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProductIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashierProductRepository.findAll().size();
        // set the field null
        cashierProduct.setProductID(null);

        // Create the CashierProduct, which fails.


        restCashierProductMockMvc.perform(post("/api/cashier-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierProduct)))
            .andExpect(status().isBadRequest());

        List<CashierProduct> cashierProductList = cashierProductRepository.findAll();
        assertThat(cashierProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCashierProdNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashierProductRepository.findAll().size();
        // set the field null
        cashierProduct.setCashierProdName(null);

        // Create the CashierProduct, which fails.


        restCashierProductMockMvc.perform(post("/api/cashier-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierProduct)))
            .andExpect(status().isBadRequest());

        List<CashierProduct> cashierProductList = cashierProductRepository.findAll();
        assertThat(cashierProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCashierProdQtyIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashierProductRepository.findAll().size();
        // set the field null
        cashierProduct.setCashierProdQty(null);

        // Create the CashierProduct, which fails.


        restCashierProductMockMvc.perform(post("/api/cashier-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierProduct)))
            .andExpect(status().isBadRequest());

        List<CashierProduct> cashierProductList = cashierProductRepository.findAll();
        assertThat(cashierProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCashierProdPurchaseUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashierProductRepository.findAll().size();
        // set the field null
        cashierProduct.setCashierProdPurchaseUnitPrice(null);

        // Create the CashierProduct, which fails.


        restCashierProductMockMvc.perform(post("/api/cashier-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierProduct)))
            .andExpect(status().isBadRequest());

        List<CashierProduct> cashierProductList = cashierProductRepository.findAll();
        assertThat(cashierProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCashierProdSaleUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashierProductRepository.findAll().size();
        // set the field null
        cashierProduct.setCashierProdSaleUnitPrice(null);

        // Create the CashierProduct, which fails.


        restCashierProductMockMvc.perform(post("/api/cashier-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierProduct)))
            .andExpect(status().isBadRequest());

        List<CashierProduct> cashierProductList = cashierProductRepository.findAll();
        assertThat(cashierProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCashierProducts() throws Exception {
        // Initialize the database
        cashierProductRepository.saveAndFlush(cashierProduct);

        // Get all the cashierProductList
        restCashierProductMockMvc.perform(get("/api/cashier-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cashierProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].productID").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].cashierProdName").value(hasItem(DEFAULT_CASHIER_PROD_NAME)))
            .andExpect(jsonPath("$.[*].cashierProdQty").value(hasItem(DEFAULT_CASHIER_PROD_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].cashierProdPurchaseUnitPrice").value(hasItem(DEFAULT_CASHIER_PROD_PURCHASE_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].cashierProdSaleUnitPrice").value(hasItem(DEFAULT_CASHIER_PROD_SALE_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].cashierProdStockLimit").value(hasItem(DEFAULT_CASHIER_PROD_STOCK_LIMIT.doubleValue())))
            .andExpect(jsonPath("$.[*].cashierProdStockLimitAlert").value(hasItem(DEFAULT_CASHIER_PROD_STOCK_LIMIT_ALERT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCashierProduct() throws Exception {
        // Initialize the database
        cashierProductRepository.saveAndFlush(cashierProduct);

        // Get the cashierProduct
        restCashierProductMockMvc.perform(get("/api/cashier-products/{id}", cashierProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cashierProduct.getId().intValue()))
            .andExpect(jsonPath("$.productID").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.cashierProdName").value(DEFAULT_CASHIER_PROD_NAME))
            .andExpect(jsonPath("$.cashierProdQty").value(DEFAULT_CASHIER_PROD_QTY.doubleValue()))
            .andExpect(jsonPath("$.cashierProdPurchaseUnitPrice").value(DEFAULT_CASHIER_PROD_PURCHASE_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.cashierProdSaleUnitPrice").value(DEFAULT_CASHIER_PROD_SALE_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.cashierProdStockLimit").value(DEFAULT_CASHIER_PROD_STOCK_LIMIT.doubleValue()))
            .andExpect(jsonPath("$.cashierProdStockLimitAlert").value(DEFAULT_CASHIER_PROD_STOCK_LIMIT_ALERT.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCashierProduct() throws Exception {
        // Get the cashierProduct
        restCashierProductMockMvc.perform(get("/api/cashier-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCashierProduct() throws Exception {
        // Initialize the database
        cashierProductService.save(cashierProduct);

        int databaseSizeBeforeUpdate = cashierProductRepository.findAll().size();

        // Update the cashierProduct
        CashierProduct updatedCashierProduct = cashierProductRepository.findById(cashierProduct.getId()).get();
        // Disconnect from session so that the updates on updatedCashierProduct are not directly saved in db
        em.detach(updatedCashierProduct);
        updatedCashierProduct
            .productID(UPDATED_PRODUCT_ID)
            .cashierProdName(UPDATED_CASHIER_PROD_NAME)
            .cashierProdQty(UPDATED_CASHIER_PROD_QTY)
            .cashierProdPurchaseUnitPrice(UPDATED_CASHIER_PROD_PURCHASE_UNIT_PRICE)
            .cashierProdSaleUnitPrice(UPDATED_CASHIER_PROD_SALE_UNIT_PRICE)
            .cashierProdStockLimit(UPDATED_CASHIER_PROD_STOCK_LIMIT)
            .cashierProdStockLimitAlert(UPDATED_CASHIER_PROD_STOCK_LIMIT_ALERT);

        restCashierProductMockMvc.perform(put("/api/cashier-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCashierProduct)))
            .andExpect(status().isOk());

        // Validate the CashierProduct in the database
        List<CashierProduct> cashierProductList = cashierProductRepository.findAll();
        assertThat(cashierProductList).hasSize(databaseSizeBeforeUpdate);
        CashierProduct testCashierProduct = cashierProductList.get(cashierProductList.size() - 1);
        assertThat(testCashierProduct.getProductID()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testCashierProduct.getCashierProdName()).isEqualTo(UPDATED_CASHIER_PROD_NAME);
        assertThat(testCashierProduct.getCashierProdQty()).isEqualTo(UPDATED_CASHIER_PROD_QTY);
        assertThat(testCashierProduct.getCashierProdPurchaseUnitPrice()).isEqualTo(UPDATED_CASHIER_PROD_PURCHASE_UNIT_PRICE);
        assertThat(testCashierProduct.getCashierProdSaleUnitPrice()).isEqualTo(UPDATED_CASHIER_PROD_SALE_UNIT_PRICE);
        assertThat(testCashierProduct.getCashierProdStockLimit()).isEqualTo(UPDATED_CASHIER_PROD_STOCK_LIMIT);
        assertThat(testCashierProduct.isCashierProdStockLimitAlert()).isEqualTo(UPDATED_CASHIER_PROD_STOCK_LIMIT_ALERT);
    }

    @Test
    @Transactional
    public void updateNonExistingCashierProduct() throws Exception {
        int databaseSizeBeforeUpdate = cashierProductRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCashierProductMockMvc.perform(put("/api/cashier-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cashierProduct)))
            .andExpect(status().isBadRequest());

        // Validate the CashierProduct in the database
        List<CashierProduct> cashierProductList = cashierProductRepository.findAll();
        assertThat(cashierProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCashierProduct() throws Exception {
        // Initialize the database
        cashierProductService.save(cashierProduct);

        int databaseSizeBeforeDelete = cashierProductRepository.findAll().size();

        // Delete the cashierProduct
        restCashierProductMockMvc.perform(delete("/api/cashier-products/{id}", cashierProduct.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CashierProduct> cashierProductList = cashierProductRepository.findAll();
        assertThat(cashierProductList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
