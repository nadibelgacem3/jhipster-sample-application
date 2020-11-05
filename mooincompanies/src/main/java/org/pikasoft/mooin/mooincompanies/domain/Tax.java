package org.pikasoft.mooin.mooincompanies.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * Tax entity.\n@author The Moo3in team.
 */
@ApiModel(description = "Tax entity.\n@author The Moo3in team.")
@Entity
@Table(name = "tax")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tax implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties(value = "taxes", allowSetters = true)
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

    public Tax name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsValued() {
        return isValued;
    }

    public Tax isValued(Boolean isValued) {
        this.isValued = isValued;
        return this;
    }

    public void setIsValued(Boolean isValued) {
        this.isValued = isValued;
    }

    public Boolean isIsPercentage() {
        return isPercentage;
    }

    public Tax isPercentage(Boolean isPercentage) {
        this.isPercentage = isPercentage;
        return this;
    }

    public void setIsPercentage(Boolean isPercentage) {
        this.isPercentage = isPercentage;
    }

    public Double getValue() {
        return value;
    }

    public Tax value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Company getCompany() {
        return company;
    }

    public Tax company(Company company) {
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
        if (!(o instanceof Tax)) {
            return false;
        }
        return id != null && id.equals(((Tax) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tax{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isValued='" + isIsValued() + "'" +
            ", isPercentage='" + isIsPercentage() + "'" +
            ", value=" + getValue() +
            "}";
    }
}
