package org.pikasoft.mooin.mooinbase.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * AvoirItem entity.\n@author The Moo3in team.
 */
@ApiModel(description = "AvoirItem entity.\n@author The Moo3in team.")
@Entity
@Table(name = "avoir_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AvoirItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @DecimalMin(value = "0")
    @Column(name = "quantity", precision = 21, scale = 2)
    private BigDecimal quantity;

    @Min(value = 0)
    @Column(name = "discount_rate")
    private Integer discountRate;

    @DecimalMin(value = "0")
    @Column(name = "total_ht", precision = 21, scale = 2)
    private BigDecimal totalHT;

    @DecimalMin(value = "0")
    @Column(name = "total_tva", precision = 21, scale = 2)
    private BigDecimal totalTVA;

    @DecimalMin(value = "0")
    @Column(name = "tota_ttc", precision = 21, scale = 2)
    private BigDecimal totaTTC;

    @ManyToOne
    @JsonIgnoreProperties(value = "avoirItems", allowSetters = true)
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties(value = "avoirItems", allowSetters = true)
    private Avoir avoir;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public AvoirItem quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Integer getDiscountRate() {
        return discountRate;
    }

    public AvoirItem discountRate(Integer discountRate) {
        this.discountRate = discountRate;
        return this;
    }

    public void setDiscountRate(Integer discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getTotalHT() {
        return totalHT;
    }

    public AvoirItem totalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
        return this;
    }

    public void setTotalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
    }

    public BigDecimal getTotalTVA() {
        return totalTVA;
    }

    public AvoirItem totalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
        return this;
    }

    public void setTotalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
    }

    public BigDecimal getTotaTTC() {
        return totaTTC;
    }

    public AvoirItem totaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
        return this;
    }

    public void setTotaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
    }

    public Product getProduct() {
        return product;
    }

    public AvoirItem product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Avoir getAvoir() {
        return avoir;
    }

    public AvoirItem avoir(Avoir avoir) {
        this.avoir = avoir;
        return this;
    }

    public void setAvoir(Avoir avoir) {
        this.avoir = avoir;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvoirItem)) {
            return false;
        }
        return id != null && id.equals(((AvoirItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvoirItem{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", discountRate=" + getDiscountRate() +
            ", totalHT=" + getTotalHT() +
            ", totalTVA=" + getTotalTVA() +
            ", totaTTC=" + getTotaTTC() +
            "}";
    }
}
