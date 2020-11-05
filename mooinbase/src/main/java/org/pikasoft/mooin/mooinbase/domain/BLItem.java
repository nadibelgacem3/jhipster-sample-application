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
 * BLItem entity.\n@author The Moo3in team.
 */
@ApiModel(description = "BLItem entity.\n@author The Moo3in team.")
@Entity
@Table(name = "bl_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BLItem implements Serializable {

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
    @JsonIgnoreProperties(value = "bLItems", allowSetters = true)
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties(value = "blItems", allowSetters = true)
    private BL bl;

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

    public BLItem quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Integer getDiscountRate() {
        return discountRate;
    }

    public BLItem discountRate(Integer discountRate) {
        this.discountRate = discountRate;
        return this;
    }

    public void setDiscountRate(Integer discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getTotalHT() {
        return totalHT;
    }

    public BLItem totalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
        return this;
    }

    public void setTotalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
    }

    public BigDecimal getTotalTVA() {
        return totalTVA;
    }

    public BLItem totalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
        return this;
    }

    public void setTotalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
    }

    public BigDecimal getTotaTTC() {
        return totaTTC;
    }

    public BLItem totaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
        return this;
    }

    public void setTotaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
    }

    public Product getProduct() {
        return product;
    }

    public BLItem product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BL getBl() {
        return bl;
    }

    public BLItem bl(BL bL) {
        this.bl = bL;
        return this;
    }

    public void setBl(BL bL) {
        this.bl = bL;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BLItem)) {
            return false;
        }
        return id != null && id.equals(((BLItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BLItem{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", discountRate=" + getDiscountRate() +
            ", totalHT=" + getTotalHT() +
            ", totalTVA=" + getTotalTVA() +
            ", totaTTC=" + getTotaTTC() +
            "}";
    }
}
