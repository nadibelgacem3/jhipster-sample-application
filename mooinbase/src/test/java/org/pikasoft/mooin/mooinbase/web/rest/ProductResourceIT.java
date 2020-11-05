package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.MooinbaseApp;
import org.pikasoft.mooin.mooinbase.domain.Product;
import org.pikasoft.mooin.mooinbase.repository.ProductRepository;
import org.pikasoft.mooin.mooinbase.service.ProductService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.pikasoft.mooin.mooinbase.domain.enumeration.ProductUnitEnum;
import org.pikasoft.mooin.mooinbase.domain.enumeration.Size;
/**
 * Integration tests for the {@link ProductResource} REST controller.
 */
@SpringBootTest(classes = MooinbaseApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductResourceIT {

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_PROVIDER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final BigDecimal DEFAULT_PURCHASE_UNIT_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_PURCHASE_UNIT_PRICE = new BigDecimal(1);

    private static final Integer DEFAULT_SALE_UNIT_PRICE = 0;
    private static final Integer UPDATED_SALE_UNIT_PRICE = 1;

    private static final Double DEFAULT_STOCKLIMIT = 1D;
    private static final Double UPDATED_STOCKLIMIT = 2D;

    private static final Boolean DEFAULT_STOCKLIMIT_ALERT = false;
    private static final Boolean UPDATED_STOCKLIMIT_ALERT = true;

    private static final ProductUnitEnum DEFAULT_UNIT_TYPE = ProductUnitEnum.NONE;
    private static final ProductUnitEnum UPDATED_UNIT_TYPE = ProductUnitEnum.METER;

    private static final Size DEFAULT_SIZE = Size.NONE;
    private static final Size UPDATED_SIZE = Size.S;

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_IS_DISPLAYED_IN_CASHIER = false;
    private static final Boolean UPDATED_IS_DISPLAYED_IN_CASHIER = true;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMockMvc;

    private Product product;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .companyID(DEFAULT_COMPANY_ID)
            .reference(DEFAULT_REFERENCE)
            .referenceProvider(DEFAULT_REFERENCE_PROVIDER)
            .name(DEFAULT_NAME)
            .quantity(DEFAULT_QUANTITY)
            .purchaseUnitPrice(DEFAULT_PURCHASE_UNIT_PRICE)
            .saleUnitPrice(DEFAULT_SALE_UNIT_PRICE)
            .stocklimit(DEFAULT_STOCKLIMIT)
            .stocklimitAlert(DEFAULT_STOCKLIMIT_ALERT)
            .unitType(DEFAULT_UNIT_TYPE)
            .size(DEFAULT_SIZE)
            .color(DEFAULT_COLOR)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .isDisplayedInCashier(DEFAULT_IS_DISPLAYED_IN_CASHIER);
        return product;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product()
            .companyID(UPDATED_COMPANY_ID)
            .reference(UPDATED_REFERENCE)
            .referenceProvider(UPDATED_REFERENCE_PROVIDER)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .purchaseUnitPrice(UPDATED_PURCHASE_UNIT_PRICE)
            .saleUnitPrice(UPDATED_SALE_UNIT_PRICE)
            .stocklimit(UPDATED_STOCKLIMIT)
            .stocklimitAlert(UPDATED_STOCKLIMIT_ALERT)
            .unitType(UPDATED_UNIT_TYPE)
            .size(UPDATED_SIZE)
            .color(UPDATED_COLOR)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .isDisplayedInCashier(UPDATED_IS_DISPLAYED_IN_CASHIER);
        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();
        // Create the Product
        restProductMockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getCompanyID()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testProduct.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testProduct.getReferenceProvider()).isEqualTo(DEFAULT_REFERENCE_PROVIDER);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProduct.getPurchaseUnitPrice()).isEqualTo(DEFAULT_PURCHASE_UNIT_PRICE);
        assertThat(testProduct.getSaleUnitPrice()).isEqualTo(DEFAULT_SALE_UNIT_PRICE);
        assertThat(testProduct.getStocklimit()).isEqualTo(DEFAULT_STOCKLIMIT);
        assertThat(testProduct.isStocklimitAlert()).isEqualTo(DEFAULT_STOCKLIMIT_ALERT);
        assertThat(testProduct.getUnitType()).isEqualTo(DEFAULT_UNIT_TYPE);
        assertThat(testProduct.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testProduct.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testProduct.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testProduct.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testProduct.isIsDisplayedInCashier()).isEqualTo(DEFAULT_IS_DISPLAYED_IN_CASHIER);
    }

    @Test
    @Transactional
    public void createProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product with an existing ID
        product.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setName(null);

        // Create the Product, which fails.


        restProductMockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setQuantity(null);

        // Create the Product, which fails.


        restProductMockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPurchaseUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setPurchaseUnitPrice(null);

        // Create the Product, which fails.


        restProductMockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSaleUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setSaleUnitPrice(null);

        // Create the Product, which fails.


        restProductMockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyID").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].referenceProvider").value(hasItem(DEFAULT_REFERENCE_PROVIDER)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].purchaseUnitPrice").value(hasItem(DEFAULT_PURCHASE_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].saleUnitPrice").value(hasItem(DEFAULT_SALE_UNIT_PRICE)))
            .andExpect(jsonPath("$.[*].stocklimit").value(hasItem(DEFAULT_STOCKLIMIT.doubleValue())))
            .andExpect(jsonPath("$.[*].stocklimitAlert").value(hasItem(DEFAULT_STOCKLIMIT_ALERT.booleanValue())))
            .andExpect(jsonPath("$.[*].unitType").value(hasItem(DEFAULT_UNIT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].isDisplayedInCashier").value(hasItem(DEFAULT_IS_DISPLAYED_IN_CASHIER.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.companyID").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.referenceProvider").value(DEFAULT_REFERENCE_PROVIDER))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.purchaseUnitPrice").value(DEFAULT_PURCHASE_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.saleUnitPrice").value(DEFAULT_SALE_UNIT_PRICE))
            .andExpect(jsonPath("$.stocklimit").value(DEFAULT_STOCKLIMIT.doubleValue()))
            .andExpect(jsonPath("$.stocklimitAlert").value(DEFAULT_STOCKLIMIT_ALERT.booleanValue()))
            .andExpect(jsonPath("$.unitType").value(DEFAULT_UNIT_TYPE.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.toString()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.isDisplayedInCashier").value(DEFAULT_IS_DISPLAYED_IN_CASHIER.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productService.save(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .companyID(UPDATED_COMPANY_ID)
            .reference(UPDATED_REFERENCE)
            .referenceProvider(UPDATED_REFERENCE_PROVIDER)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .purchaseUnitPrice(UPDATED_PURCHASE_UNIT_PRICE)
            .saleUnitPrice(UPDATED_SALE_UNIT_PRICE)
            .stocklimit(UPDATED_STOCKLIMIT)
            .stocklimitAlert(UPDATED_STOCKLIMIT_ALERT)
            .unitType(UPDATED_UNIT_TYPE)
            .size(UPDATED_SIZE)
            .color(UPDATED_COLOR)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .isDisplayedInCashier(UPDATED_IS_DISPLAYED_IN_CASHIER);

        restProductMockMvc.perform(put("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduct)))
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getCompanyID()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testProduct.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testProduct.getReferenceProvider()).isEqualTo(UPDATED_REFERENCE_PROVIDER);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProduct.getPurchaseUnitPrice()).isEqualTo(UPDATED_PURCHASE_UNIT_PRICE);
        assertThat(testProduct.getSaleUnitPrice()).isEqualTo(UPDATED_SALE_UNIT_PRICE);
        assertThat(testProduct.getStocklimit()).isEqualTo(UPDATED_STOCKLIMIT);
        assertThat(testProduct.isStocklimitAlert()).isEqualTo(UPDATED_STOCKLIMIT_ALERT);
        assertThat(testProduct.getUnitType()).isEqualTo(UPDATED_UNIT_TYPE);
        assertThat(testProduct.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testProduct.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testProduct.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProduct.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testProduct.isIsDisplayedInCashier()).isEqualTo(UPDATED_IS_DISPLAYED_IN_CASHIER);
    }

    @Test
    @Transactional
    public void updateNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc.perform(put("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productService.save(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
