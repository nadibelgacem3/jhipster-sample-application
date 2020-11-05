package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.MooinbaseApp;
import org.pikasoft.mooin.mooinbase.domain.ProductMark;
import org.pikasoft.mooin.mooinbase.repository.ProductMarkRepository;
import org.pikasoft.mooin.mooinbase.service.ProductMarkService;

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
 * Integration tests for the {@link ProductMarkResource} REST controller.
 */
@SpringBootTest(classes = MooinbaseApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductMarkResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    @Autowired
    private ProductMarkRepository productMarkRepository;

    @Autowired
    private ProductMarkService productMarkService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMarkMockMvc;

    private ProductMark productMark;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductMark createEntity(EntityManager em) {
        ProductMark productMark = new ProductMark()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .companyID(DEFAULT_COMPANY_ID);
        return productMark;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductMark createUpdatedEntity(EntityManager em) {
        ProductMark productMark = new ProductMark()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .companyID(UPDATED_COMPANY_ID);
        return productMark;
    }

    @BeforeEach
    public void initTest() {
        productMark = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductMark() throws Exception {
        int databaseSizeBeforeCreate = productMarkRepository.findAll().size();
        // Create the ProductMark
        restProductMarkMockMvc.perform(post("/api/product-marks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productMark)))
            .andExpect(status().isCreated());

        // Validate the ProductMark in the database
        List<ProductMark> productMarkList = productMarkRepository.findAll();
        assertThat(productMarkList).hasSize(databaseSizeBeforeCreate + 1);
        ProductMark testProductMark = productMarkList.get(productMarkList.size() - 1);
        assertThat(testProductMark.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductMark.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductMark.getCompanyID()).isEqualTo(DEFAULT_COMPANY_ID);
    }

    @Test
    @Transactional
    public void createProductMarkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productMarkRepository.findAll().size();

        // Create the ProductMark with an existing ID
        productMark.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMarkMockMvc.perform(post("/api/product-marks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productMark)))
            .andExpect(status().isBadRequest());

        // Validate the ProductMark in the database
        List<ProductMark> productMarkList = productMarkRepository.findAll();
        assertThat(productMarkList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productMarkRepository.findAll().size();
        // set the field null
        productMark.setName(null);

        // Create the ProductMark, which fails.


        restProductMarkMockMvc.perform(post("/api/product-marks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productMark)))
            .andExpect(status().isBadRequest());

        List<ProductMark> productMarkList = productMarkRepository.findAll();
        assertThat(productMarkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductMarks() throws Exception {
        // Initialize the database
        productMarkRepository.saveAndFlush(productMark);

        // Get all the productMarkList
        restProductMarkMockMvc.perform(get("/api/product-marks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productMark.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].companyID").value(hasItem(DEFAULT_COMPANY_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getProductMark() throws Exception {
        // Initialize the database
        productMarkRepository.saveAndFlush(productMark);

        // Get the productMark
        restProductMarkMockMvc.perform(get("/api/product-marks/{id}", productMark.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productMark.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.companyID").value(DEFAULT_COMPANY_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingProductMark() throws Exception {
        // Get the productMark
        restProductMarkMockMvc.perform(get("/api/product-marks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductMark() throws Exception {
        // Initialize the database
        productMarkService.save(productMark);

        int databaseSizeBeforeUpdate = productMarkRepository.findAll().size();

        // Update the productMark
        ProductMark updatedProductMark = productMarkRepository.findById(productMark.getId()).get();
        // Disconnect from session so that the updates on updatedProductMark are not directly saved in db
        em.detach(updatedProductMark);
        updatedProductMark
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .companyID(UPDATED_COMPANY_ID);

        restProductMarkMockMvc.perform(put("/api/product-marks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductMark)))
            .andExpect(status().isOk());

        // Validate the ProductMark in the database
        List<ProductMark> productMarkList = productMarkRepository.findAll();
        assertThat(productMarkList).hasSize(databaseSizeBeforeUpdate);
        ProductMark testProductMark = productMarkList.get(productMarkList.size() - 1);
        assertThat(testProductMark.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductMark.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductMark.getCompanyID()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingProductMark() throws Exception {
        int databaseSizeBeforeUpdate = productMarkRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMarkMockMvc.perform(put("/api/product-marks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productMark)))
            .andExpect(status().isBadRequest());

        // Validate the ProductMark in the database
        List<ProductMark> productMarkList = productMarkRepository.findAll();
        assertThat(productMarkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductMark() throws Exception {
        // Initialize the database
        productMarkService.save(productMark);

        int databaseSizeBeforeDelete = productMarkRepository.findAll().size();

        // Delete the productMark
        restProductMarkMockMvc.perform(delete("/api/product-marks/{id}", productMark.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductMark> productMarkList = productMarkRepository.findAll();
        assertThat(productMarkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
