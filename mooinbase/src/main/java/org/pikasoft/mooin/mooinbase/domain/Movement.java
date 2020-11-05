package org.pikasoft.mooin.mooinbase.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import org.pikasoft.mooin.mooinbase.domain.enumeration.MovementTypeEnum;

import org.pikasoft.mooin.mooinbase.domain.enumeration.MovementReasonEnum;

/**
 * Movement entity.\n@author The Moo3in team.
 */
@ApiModel(description = "Movement entity.\n@author The Moo3in team.")
@Entity
@Table(name = "movement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Movement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MovementTypeEnum type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false)
    private MovementReasonEnum reason;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @Column(name = "bill_id")
    private Long billID;

    @Column(name = "tiers_id")
    private Long tiersID;

    @NotNull
    @Column(name = "quantity", precision = 21, scale = 2, nullable = false)
    private BigDecimal quantity;

    @Column(name = "company_user_id")
    private Long companyUserID;

    @ManyToOne
    @JsonIgnoreProperties(value = "movements", allowSetters = true)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MovementTypeEnum getType() {
        return type;
    }

    public Movement type(MovementTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(MovementTypeEnum type) {
        this.type = type;
    }

    public MovementReasonEnum getReason() {
        return reason;
    }

    public Movement reason(MovementReasonEnum reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(MovementReasonEnum reason) {
        this.reason = reason;
    }

    public Instant getDate() {
        return date;
    }

    public Movement date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getBillID() {
        return billID;
    }

    public Movement billID(Long billID) {
        this.billID = billID;
        return this;
    }

    public void setBillID(Long billID) {
        this.billID = billID;
    }

    public Long getTiersID() {
        return tiersID;
    }

    public Movement tiersID(Long tiersID) {
        this.tiersID = tiersID;
        return this;
    }

    public void setTiersID(Long tiersID) {
        this.tiersID = tiersID;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public Movement quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Long getCompanyUserID() {
        return companyUserID;
    }

    public Movement companyUserID(Long companyUserID) {
        this.companyUserID = companyUserID;
        return this;
    }

    public void setCompanyUserID(Long companyUserID) {
        this.companyUserID = companyUserID;
    }

    public Product getProduct() {
        return product;
    }

    public Movement product(Product product) {
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
        if (!(o instanceof Movement)) {
            return false;
        }
        return id != null && id.equals(((Movement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Movement{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", reason='" + getReason() + "'" +
            ", date='" + getDate() + "'" +
            ", billID=" + getBillID() +
            ", tiersID=" + getTiersID() +
            ", quantity=" + getQuantity() +
            ", companyUserID=" + getCompanyUserID() +
            "}";
    }
}
