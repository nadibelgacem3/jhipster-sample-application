package org.pikasoft.mooin.mooinecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.pikasoft.mooin.mooinecommerce.domain.enumeration.OrderStatus;

/**
 * OfferOrder entity.\n@author The Moo3in team.
 */
@ApiModel(description = "OfferOrder entity.\n@author The Moo3in team.")
@Entity
@Table(name = "offer_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OfferOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @DecimalMin(value = "1")
    @Column(name = "quantity", precision = 21, scale = 2, nullable = false)
    private BigDecimal quantity;

    @DecimalMin(value = "0")
    @Column(name = "total_ht", precision = 21, scale = 2)
    private BigDecimal totalHT;

    @DecimalMin(value = "0")
    @Column(name = "total_tva", precision = 21, scale = 2)
    private BigDecimal totalTVA;

    @DecimalMin(value = "0")
    @Column(name = "tota_ttc", precision = 21, scale = 2)
    private BigDecimal totaTTC;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "requester_offer_id")
    private Long requesterOfferID;

    @ManyToOne
    @JsonIgnoreProperties(value = "offerOrders", allowSetters = true)
    private Offer offer;

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

    public OfferOrder quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalHT() {
        return totalHT;
    }

    public OfferOrder totalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
        return this;
    }

    public void setTotalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
    }

    public BigDecimal getTotalTVA() {
        return totalTVA;
    }

    public OfferOrder totalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
        return this;
    }

    public void setTotalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
    }

    public BigDecimal getTotaTTC() {
        return totaTTC;
    }

    public OfferOrder totaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
        return this;
    }

    public void setTotaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
    }

    public LocalDate getDate() {
        return date;
    }

    public OfferOrder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OfferOrder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Long getRequesterOfferID() {
        return requesterOfferID;
    }

    public OfferOrder requesterOfferID(Long requesterOfferID) {
        this.requesterOfferID = requesterOfferID;
        return this;
    }

    public void setRequesterOfferID(Long requesterOfferID) {
        this.requesterOfferID = requesterOfferID;
    }

    public Offer getOffer() {
        return offer;
    }

    public OfferOrder offer(Offer offer) {
        this.offer = offer;
        return this;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfferOrder)) {
            return false;
        }
        return id != null && id.equals(((OfferOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfferOrder{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", totalHT=" + getTotalHT() +
            ", totalTVA=" + getTotalTVA() +
            ", totaTTC=" + getTotaTTC() +
            ", date='" + getDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", requesterOfferID=" + getRequesterOfferID() +
            "}";
    }
}
