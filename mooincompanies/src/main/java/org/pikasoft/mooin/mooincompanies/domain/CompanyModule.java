package org.pikasoft.mooin.mooincompanies.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * CompanyModule entity.\n@author The Moo3in team.
 */
@ApiModel(description = "CompanyModule entity.\n@author The Moo3in team.")
@Entity
@Table(name = "company_module")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompanyModule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "activated_at", nullable = false)
    private LocalDate activatedAt;

    @NotNull
    @Column(name = "activated_until", nullable = false)
    private LocalDate activatedUntil;

    @NotNull
    @Column(name = "is_activated", nullable = false)
    private Boolean isActivated;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "companyModule")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CompanyBillPaymentItem> companyBillPaymentItems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "companyModules", allowSetters = true)
    private Company company;

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

    public CompanyModule name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getActivatedAt() {
        return activatedAt;
    }

    public CompanyModule activatedAt(LocalDate activatedAt) {
        this.activatedAt = activatedAt;
        return this;
    }

    public void setActivatedAt(LocalDate activatedAt) {
        this.activatedAt = activatedAt;
    }

    public LocalDate getActivatedUntil() {
        return activatedUntil;
    }

    public CompanyModule activatedUntil(LocalDate activatedUntil) {
        this.activatedUntil = activatedUntil;
        return this;
    }

    public void setActivatedUntil(LocalDate activatedUntil) {
        this.activatedUntil = activatedUntil;
    }

    public Boolean isIsActivated() {
        return isActivated;
    }

    public CompanyModule isActivated(Boolean isActivated) {
        this.isActivated = isActivated;
        return this;
    }

    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CompanyModule price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<CompanyBillPaymentItem> getCompanyBillPaymentItems() {
        return companyBillPaymentItems;
    }

    public CompanyModule companyBillPaymentItems(Set<CompanyBillPaymentItem> companyBillPaymentItems) {
        this.companyBillPaymentItems = companyBillPaymentItems;
        return this;
    }

    public CompanyModule addCompanyBillPaymentItem(CompanyBillPaymentItem companyBillPaymentItem) {
        this.companyBillPaymentItems.add(companyBillPaymentItem);
        companyBillPaymentItem.setCompanyModule(this);
        return this;
    }

    public CompanyModule removeCompanyBillPaymentItem(CompanyBillPaymentItem companyBillPaymentItem) {
        this.companyBillPaymentItems.remove(companyBillPaymentItem);
        companyBillPaymentItem.setCompanyModule(null);
        return this;
    }

    public void setCompanyBillPaymentItems(Set<CompanyBillPaymentItem> companyBillPaymentItems) {
        this.companyBillPaymentItems = companyBillPaymentItems;
    }

    public Company getCompany() {
        return company;
    }

    public CompanyModule company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyModule)) {
            return false;
        }
        return id != null && id.equals(((CompanyModule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyModule{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", activatedAt='" + getActivatedAt() + "'" +
            ", activatedUntil='" + getActivatedUntil() + "'" +
            ", isActivated='" + isIsActivated() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
