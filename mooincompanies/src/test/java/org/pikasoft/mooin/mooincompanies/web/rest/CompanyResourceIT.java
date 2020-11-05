package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.MooincompaniesApp;
import org.pikasoft.mooin.mooincompanies.domain.Company;
import org.pikasoft.mooin.mooincompanies.repository.CompanyRepository;
import org.pikasoft.mooin.mooincompanies.service.CompanyService;

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

import org.pikasoft.mooin.mooincompanies.domain.enumeration.CompanyBusinessTypeEnum;
/**
 * Integration tests for the {@link CompanyResource} REST controller.
 */
@SpringBootTest(classes = MooincompaniesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompanyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PHONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_1 = "Q$@g=3ZS.w\"kNS<";
    private static final String UPDATED_EMAIL_1 = "6|UuD@opl2.1{";

    private static final String DEFAULT_EMAIL_2 = "4w@^L&.te}";
    private static final String UPDATED_EMAIL_2 = "<w@:3|-i.K[";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final CompanyBusinessTypeEnum DEFAULT_BUSINESS_TYPE = CompanyBusinessTypeEnum.SARL;
    private static final CompanyBusinessTypeEnum UPDATED_BUSINESS_TYPE = CompanyBusinessTypeEnum.SUARL;

    private static final String DEFAULT_CURRENCY_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_UNIT = "BBBBBBBBBB";

    private static final Integer DEFAULT_CURRENCY_QUOTIENT = 1;
    private static final Integer UPDATED_CURRENCY_QUOTIENT = 2;

    private static final String DEFAULT_COMMERCIAL_REGISTER = "AAAAAAAAAA";
    private static final String UPDATED_COMMERCIAL_REGISTER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVATED = false;
    private static final Boolean UPDATED_IS_ACTIVATED = true;

    private static final String DEFAULT_THEME_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_THEME_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_LINKEDIN = "AAAAAAAAAA";
    private static final String UPDATED_LINKEDIN = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER = "BBBBBBBBBB";

    private static final String DEFAULT_INSTAGRAM = "AAAAAAAAAA";
    private static final String UPDATED_INSTAGRAM = "BBBBBBBBBB";

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyMockMvc;

    private Company company;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createEntity(EntityManager em) {
        Company company = new Company()
            .name(DEFAULT_NAME)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .phone1(DEFAULT_PHONE_1)
            .phone2(DEFAULT_PHONE_2)
            .fax(DEFAULT_FAX)
            .email1(DEFAULT_EMAIL_1)
            .email2(DEFAULT_EMAIL_2)
            .description(DEFAULT_DESCRIPTION)
            .businessType(DEFAULT_BUSINESS_TYPE)
            .currencyUnit(DEFAULT_CURRENCY_UNIT)
            .currencyQuotient(DEFAULT_CURRENCY_QUOTIENT)
            .commercialRegister(DEFAULT_COMMERCIAL_REGISTER)
            .isActivated(DEFAULT_IS_ACTIVATED)
            .themeColor(DEFAULT_THEME_COLOR)
            .facebook(DEFAULT_FACEBOOK)
            .linkedin(DEFAULT_LINKEDIN)
            .twitter(DEFAULT_TWITTER)
            .instagram(DEFAULT_INSTAGRAM);
        return company;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createUpdatedEntity(EntityManager em) {
        Company company = new Company()
            .name(UPDATED_NAME)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .fax(UPDATED_FAX)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2)
            .description(UPDATED_DESCRIPTION)
            .businessType(UPDATED_BUSINESS_TYPE)
            .currencyUnit(UPDATED_CURRENCY_UNIT)
            .currencyQuotient(UPDATED_CURRENCY_QUOTIENT)
            .commercialRegister(UPDATED_COMMERCIAL_REGISTER)
            .isActivated(UPDATED_IS_ACTIVATED)
            .themeColor(UPDATED_THEME_COLOR)
            .facebook(UPDATED_FACEBOOK)
            .linkedin(UPDATED_LINKEDIN)
            .twitter(UPDATED_TWITTER)
            .instagram(UPDATED_INSTAGRAM);
        return company;
    }

    @BeforeEach
    public void initTest() {
        company = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();
        // Create the Company
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompany.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testCompany.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testCompany.getPhone1()).isEqualTo(DEFAULT_PHONE_1);
        assertThat(testCompany.getPhone2()).isEqualTo(DEFAULT_PHONE_2);
        assertThat(testCompany.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testCompany.getEmail1()).isEqualTo(DEFAULT_EMAIL_1);
        assertThat(testCompany.getEmail2()).isEqualTo(DEFAULT_EMAIL_2);
        assertThat(testCompany.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompany.getBusinessType()).isEqualTo(DEFAULT_BUSINESS_TYPE);
        assertThat(testCompany.getCurrencyUnit()).isEqualTo(DEFAULT_CURRENCY_UNIT);
        assertThat(testCompany.getCurrencyQuotient()).isEqualTo(DEFAULT_CURRENCY_QUOTIENT);
        assertThat(testCompany.getCommercialRegister()).isEqualTo(DEFAULT_COMMERCIAL_REGISTER);
        assertThat(testCompany.isIsActivated()).isEqualTo(DEFAULT_IS_ACTIVATED);
        assertThat(testCompany.getThemeColor()).isEqualTo(DEFAULT_THEME_COLOR);
        assertThat(testCompany.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testCompany.getLinkedin()).isEqualTo(DEFAULT_LINKEDIN);
        assertThat(testCompany.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testCompany.getInstagram()).isEqualTo(DEFAULT_INSTAGRAM);
    }

    @Test
    @Transactional
    public void createCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company with an existing ID
        company.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setName(null);

        // Create the Company, which fails.


        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1)))
            .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1)))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].businessType").value(hasItem(DEFAULT_BUSINESS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].currencyUnit").value(hasItem(DEFAULT_CURRENCY_UNIT)))
            .andExpect(jsonPath("$.[*].currencyQuotient").value(hasItem(DEFAULT_CURRENCY_QUOTIENT)))
            .andExpect(jsonPath("$.[*].commercialRegister").value(hasItem(DEFAULT_COMMERCIAL_REGISTER)))
            .andExpect(jsonPath("$.[*].isActivated").value(hasItem(DEFAULT_IS_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].themeColor").value(hasItem(DEFAULT_THEME_COLOR)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].linkedin").value(hasItem(DEFAULT_LINKEDIN)))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER)))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM)));
    }
    
    @Test
    @Transactional
    public void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.phone1").value(DEFAULT_PHONE_1))
            .andExpect(jsonPath("$.phone2").value(DEFAULT_PHONE_2))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.email1").value(DEFAULT_EMAIL_1))
            .andExpect(jsonPath("$.email2").value(DEFAULT_EMAIL_2))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.businessType").value(DEFAULT_BUSINESS_TYPE.toString()))
            .andExpect(jsonPath("$.currencyUnit").value(DEFAULT_CURRENCY_UNIT))
            .andExpect(jsonPath("$.currencyQuotient").value(DEFAULT_CURRENCY_QUOTIENT))
            .andExpect(jsonPath("$.commercialRegister").value(DEFAULT_COMMERCIAL_REGISTER))
            .andExpect(jsonPath("$.isActivated").value(DEFAULT_IS_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.themeColor").value(DEFAULT_THEME_COLOR))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK))
            .andExpect(jsonPath("$.linkedin").value(DEFAULT_LINKEDIN))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER))
            .andExpect(jsonPath("$.instagram").value(DEFAULT_INSTAGRAM));
    }
    @Test
    @Transactional
    public void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompany() throws Exception {
        // Initialize the database
        companyService.save(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findById(company.getId()).get();
        // Disconnect from session so that the updates on updatedCompany are not directly saved in db
        em.detach(updatedCompany);
        updatedCompany
            .name(UPDATED_NAME)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .fax(UPDATED_FAX)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2)
            .description(UPDATED_DESCRIPTION)
            .businessType(UPDATED_BUSINESS_TYPE)
            .currencyUnit(UPDATED_CURRENCY_UNIT)
            .currencyQuotient(UPDATED_CURRENCY_QUOTIENT)
            .commercialRegister(UPDATED_COMMERCIAL_REGISTER)
            .isActivated(UPDATED_IS_ACTIVATED)
            .themeColor(UPDATED_THEME_COLOR)
            .facebook(UPDATED_FACEBOOK)
            .linkedin(UPDATED_LINKEDIN)
            .twitter(UPDATED_TWITTER)
            .instagram(UPDATED_INSTAGRAM);

        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompany)))
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompany.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testCompany.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testCompany.getPhone1()).isEqualTo(UPDATED_PHONE_1);
        assertThat(testCompany.getPhone2()).isEqualTo(UPDATED_PHONE_2);
        assertThat(testCompany.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testCompany.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testCompany.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testCompany.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompany.getBusinessType()).isEqualTo(UPDATED_BUSINESS_TYPE);
        assertThat(testCompany.getCurrencyUnit()).isEqualTo(UPDATED_CURRENCY_UNIT);
        assertThat(testCompany.getCurrencyQuotient()).isEqualTo(UPDATED_CURRENCY_QUOTIENT);
        assertThat(testCompany.getCommercialRegister()).isEqualTo(UPDATED_COMMERCIAL_REGISTER);
        assertThat(testCompany.isIsActivated()).isEqualTo(UPDATED_IS_ACTIVATED);
        assertThat(testCompany.getThemeColor()).isEqualTo(UPDATED_THEME_COLOR);
        assertThat(testCompany.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testCompany.getLinkedin()).isEqualTo(UPDATED_LINKEDIN);
        assertThat(testCompany.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testCompany.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
    }

    @Test
    @Transactional
    public void updateNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompany() throws Exception {
        // Initialize the database
        companyService.save(company);

        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Delete the company
        restCompanyMockMvc.perform(delete("/api/companies/{id}", company.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
