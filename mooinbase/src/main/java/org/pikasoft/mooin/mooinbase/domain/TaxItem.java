package org.pikasoft.mooin.mooinbase.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * TaxItem entity.\n@author The Moo3in team.
 */
@ApiModel(description = "TaxItem entity.\n@author The Moo3in team.")
@Entity
@Table(name = "tax_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaxItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "is_valued", nullable = false)
    private Boolean isValued;

    @NotNull
    @Column(name = "is_percentage", nullable = false)
    private Boolean isPercentage;

    @NotNull
    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "company_id")
    private Long companyID;

    @Column(name = "tax_id")
    private Long taxID;

    @ManyToOne
    @JsonIgnoreProperties(value = "taxItems", allowSetters = true)
    private Product product;

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

    public TaxItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsValued() {
        return isValued;
    }

    public TaxItem isValued(Boolean isValued) {
        this.isValued = isValued;
        return this;
    }

    public void setIsValued(Boolean isValued) {
        this.isValued = isValued;
    }

    public Boolean isIsPercentage() {
        return isPercentage;
    }

    public TaxItem isPercentage(Boolean isPercentage) {
        this.isPercentage = isPercentage;
        return this;
    }

    public void setIsPercentage(Boolean isPercentage) {
        this.isPercentage = isPercentage;
    }

    public Double getValue() {
        return value;
    }

    public TaxItem value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Long getCompanyID() {
        return companyID;
    }

    public TaxItem companyID(Long companyID) {
        this.companyID = companyID;
        return this;
    }

    public void setCompanyID(Long companyID) {
        this.companyID = companyID;
    }

    public Long getTaxID() {
        return taxID;
    }

    public TaxItem taxID(Long taxID) {
        this.taxID = taxID;
        return this;
    }

    public void setTaxID(Long taxID) {
        this.taxID = taxID;
    }

    public Product getProduct() {
        return product;
    }

    public TaxItem product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaxItem)) {
            return false;
        }
        return id != null && id.equals(((TaxItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaxItem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isValued='" + isIsValued() + "'" +
            ", isPercentage='" + isIsPercentage() + "'" +
            ", value=" + getValue() +
            ", companyID=" + getCompanyID() +
            ", taxID=" + getTaxID() +
            "}";
    }
}
