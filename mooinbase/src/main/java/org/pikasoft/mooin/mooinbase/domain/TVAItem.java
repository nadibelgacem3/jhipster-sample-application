package org.pikasoft.mooin.mooinbase.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * TVAItem entity.\n@author The Moo3in team.
 */
@ApiModel(description = "TVAItem entity.\n@author The Moo3in team.")
@Entity
@Table(name = "tva_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TVAItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "percentage_value", nullable = false)
    private Double percentageValue;

    @Column(name = "company_id")
    private Long companyID;

    @Column(name = "tva_id")
    private Long tvaID;

    @ManyToOne
    @JsonIgnoreProperties(value = "tvaItems", allowSetters = true)
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

    public TVAItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPercentageValue() {
        return percentageValue;
    }

    public TVAItem percentageValue(Double percentageValue) {
        this.percentageValue = percentageValue;
        return this;
    }

    public void setPercentageValue(Double percentageValue) {
        this.percentageValue = percentageValue;
    }

    public Long getCompanyID() {
        return companyID;
    }

    public TVAItem companyID(Long companyID) {
        this.companyID = companyID;
        return this;
    }

    public void setCompanyID(Long companyID) {
        this.companyID = companyID;
    }

    public Long getTvaID() {
        return tvaID;
    }

    public TVAItem tvaID(Long tvaID) {
        this.tvaID = tvaID;
        return this;
    }

    public void setTvaID(Long tvaID) {
        this.tvaID = tvaID;
    }

    public Product getProduct() {
        return product;
    }

    public TVAItem product(Product product) {
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
        if (!(o instanceof TVAItem)) {
            return false;
        }
        return id != null && id.equals(((TVAItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TVAItem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", percentageValue=" + getPercentageValue() +
            ", companyID=" + getCompanyID() +
            ", tvaID=" + getTvaID() +
            "}";
    }
}
