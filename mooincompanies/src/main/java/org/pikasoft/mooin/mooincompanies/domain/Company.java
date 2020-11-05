package org.pikasoft.mooin.mooincompanies.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import org.pikasoft.mooin.mooincompanies.domain.enumeration.CompanyBusinessTypeEnum;

/**
 * Company entity.\n@author The Moo3in team.
 */
@ApiModel(description = "Company entity.\n@author The Moo3in team.")
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 4)
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Column(name = "phone_1")
    private String phone1;

    @Column(name = "phone_2")
    private String phone2;

    @Column(name = "fax")
    private String fax;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email_1")
    private String email1;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email_2")
    private String email2;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_type")
    private CompanyBusinessTypeEnum businessType;

    @Column(name = "currency_unit")
    private String currencyUnit;

    @Column(name = "currency_quotient")
    private Integer currencyQuotient;

    @Column(name = "commercial_register")
    private String commercialRegister;

    @Column(name = "is_activated")
    private Boolean isActivated;

    @Column(name = "theme_color")
    private String themeColor;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "linkedin")
    private String linkedin;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "instagram")
    private String instagram;

    @OneToOne
    @JoinColumn(unique = true)
    private CompanyBankAccount companyBankAccount;

    @OneToOne
    @JoinColumn(unique = true)
    private CompanyLocation companyLocation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Company logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Company logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getPhone1() {
        return phone1;
    }

    public Company phone1(String phone1) {
        this.phone1 = phone1;
        return this;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public Company phone2(String phone2) {
        this.phone2 = phone2;
        return this;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getFax() {
        return fax;
    }

    public Company fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail1() {
        return email1;
    }

    public Company email1(String email1) {
        this.email1 = email1;
        return this;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public Company email2(String email2) {
        this.email2 = email2;
        return this;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getDescription() {
        return description;
    }

    public Company description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CompanyBusinessTypeEnum getBusinessType() {
        return businessType;
    }

    public Company businessType(CompanyBusinessTypeEnum businessType) {
        this.businessType = businessType;
        return this;
    }

    public void setBusinessType(CompanyBusinessTypeEnum businessType) {
        this.businessType = businessType;
    }

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public Company currencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
        return this;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public Integer getCurrencyQuotient() {
        return currencyQuotient;
    }

    public Company currencyQuotient(Integer currencyQuotient) {
        this.currencyQuotient = currencyQuotient;
        return this;
    }

    public void setCurrencyQuotient(Integer currencyQuotient) {
        this.currencyQuotient = currencyQuotient;
    }

    public String getCommercialRegister() {
        return commercialRegister;
    }

    public Company commercialRegister(String commercialRegister) {
        this.commercialRegister = commercialRegister;
        return this;
    }

    public void setCommercialRegister(String commercialRegister) {
        this.commercialRegister = commercialRegister;
    }

    public Boolean isIsActivated() {
        return isActivated;
    }

    public Company isActivated(Boolean isActivated) {
        this.isActivated = isActivated;
        return this;
    }

    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }

    public String getThemeColor() {
        return themeColor;
    }

    public Company themeColor(String themeColor) {
        this.themeColor = themeColor;
        return this;
    }

    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }

    public String getFacebook() {
        return facebook;
    }

    public Company facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public Company linkedin(String linkedin) {
        this.linkedin = linkedin;
        return this;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getTwitter() {
        return twitter;
    }

    public Company twitter(String twitter) {
        this.twitter = twitter;
        return this;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public Company instagram(String instagram) {
        this.instagram = instagram;
        return this;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public CompanyBankAccount getCompanyBankAccount() {
        return companyBankAccount;
    }

    public Company companyBankAccount(CompanyBankAccount companyBankAccount) {
        this.companyBankAccount = companyBankAccount;
        return this;
    }

    public void setCompanyBankAccount(CompanyBankAccount companyBankAccount) {
        this.companyBankAccount = companyBankAccount;
    }

    public CompanyLocation getCompanyLocation() {
        return companyLocation;
    }

    public Company companyLocation(CompanyLocation companyLocation) {
        this.companyLocation = companyLocation;
        return this;
    }

    public void setCompanyLocation(CompanyLocation companyLocation) {
        this.companyLocation = companyLocation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", phone1='" + getPhone1() + "'" +
            ", phone2='" + getPhone2() + "'" +
            ", fax='" + getFax() + "'" +
            ", email1='" + getEmail1() + "'" +
            ", email2='" + getEmail2() + "'" +
            ", description='" + getDescription() + "'" +
            ", businessType='" + getBusinessType() + "'" +
            ", currencyUnit='" + getCurrencyUnit() + "'" +
            ", currencyQuotient=" + getCurrencyQuotient() +
            ", commercialRegister='" + getCommercialRegister() + "'" +
            ", isActivated='" + isIsActivated() + "'" +
            ", themeColor='" + getThemeColor() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", linkedin='" + getLinkedin() + "'" +
            ", twitter='" + getTwitter() + "'" +
            ", instagram='" + getInstagram() + "'" +
            "}";
    }
}
