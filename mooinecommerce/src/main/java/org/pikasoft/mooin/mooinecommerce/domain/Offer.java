package org.pikasoft.mooin.mooinecommerce.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import org.pikasoft.mooin.mooinecommerce.domain.enumeration.OfferStatus;

/**
 * Offer entity.\n@author The Moo3in team.
 */
@ApiModel(description = "Offer entity.\n@author The Moo3in team.")
@Entity
@Table(name = "offer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "date_begin")
    private LocalDate dateBegin;

    @Column(name = "date_end")
    private LocalDate dateEnd;

    @Column(name = "duration")
    private Duration duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OfferStatus status;

    @Column(name = "product_id")
    private Long productId;

    @DecimalMin(value = "0")
    @Column(name = "sale_unit_price_before_discount", precision = 21, scale = 2)
    private BigDecimal saleUnitPriceBeforeDiscount;

    @DecimalMin(value = "0")
    @Column(name = "discount_rate", precision = 21, scale = 2)
    private BigDecimal discountRate;

    @DecimalMin(value = "0")
    @Column(name = "sale_unit_price_after_discount", precision = 21, scale = 2)
    private BigDecimal saleUnitPriceAfterDiscount;

    @Column(name = "with_tva")
    private Boolean withTVA;

    @Column(name = "with_tax")
    private Boolean withTax;

    @Column(name = "is_activated")
    private Boolean isActivated;

    @Column(name = "company_id")
    private Long companyID;

    @OneToMany(mappedBy = "offer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<OfferOrder> offerOrders = new HashSet<>();

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

    public Offer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Offer description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public Offer image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Offer imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public LocalDate getDateBegin() {
        return dateBegin;
    }

    public Offer dateBegin(LocalDate dateBegin) {
        this.dateBegin = dateBegin;
        return this;
    }

    public void setDateBegin(LocalDate dateBegin) {
        this.dateBegin = dateBegin;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public Offer dateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Duration getDuration() {
        return duration;
    }

    public Offer duration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public Offer status(OfferStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }

    public Long getProductId() {
        return productId;
    }

    public Offer productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getSaleUnitPriceBeforeDiscount() {
        return saleUnitPriceBeforeDiscount;
    }

    public Offer saleUnitPriceBeforeDiscount(BigDecimal saleUnitPriceBeforeDiscount) {
        this.saleUnitPriceBeforeDiscount = saleUnitPriceBeforeDiscount;
        return this;
    }

    public void setSaleUnitPriceBeforeDiscount(BigDecimal saleUnitPriceBeforeDiscount) {
        this.saleUnitPriceBeforeDiscount = saleUnitPriceBeforeDiscount;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public Offer discountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
        return this;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getSaleUnitPriceAfterDiscount() {
        return saleUnitPriceAfterDiscount;
    }

    public Offer saleUnitPriceAfterDiscount(BigDecimal saleUnitPriceAfterDiscount) {
        this.saleUnitPriceAfterDiscount = saleUnitPriceAfterDiscount;
        return this;
    }

    public void setSaleUnitPriceAfterDiscount(BigDecimal saleUnitPriceAfterDiscount) {
        this.saleUnitPriceAfterDiscount = saleUnitPriceAfterDiscount;
    }

    public Boolean isWithTVA() {
        return withTVA;
    }

    public Offer withTVA(Boolean withTVA) {
        this.withTVA = withTVA;
        return this;
    }

    public void setWithTVA(Boolean withTVA) {
        this.withTVA = withTVA;
    }

    public Boolean isWithTax() {
        return withTax;
    }

    public Offer withTax(Boolean withTax) {
        this.withTax = withTax;
        return this;
    }

    public void setWithTax(Boolean withTax) {
        this.withTax = withTax;
    }

    public Boolean isIsActivated() {
        return isActivated;
    }

    public Offer isActivated(Boolean isActivated) {
        this.isActivated = isActivated;
        return this;
    }

    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }

    public Long getCompanyID() {
        return companyID;
    }

    public Offer companyID(Long companyID) {
        this.companyID = companyID;
        return this;
    }

    public void setCompanyID(Long companyID) {
        this.companyID = companyID;
    }

    public Set<OfferOrder> getOfferOrders() {
        return offerOrders;
    }

    public Offer offerOrders(Set<OfferOrder> offerOrders) {
        this.offerOrders = offerOrders;
        return this;
    }

    public Offer addOfferOrder(OfferOrder offerOrder) {
        this.offerOrders.add(offerOrder);
        offerOrder.setOffer(this);
        return this;
    }

    public Offer removeOfferOrder(OfferOrder offerOrder) {
        this.offerOrders.remove(offerOrder);
        offerOrder.setOffer(null);
        return this;
    }

    public void setOfferOrders(Set<OfferOrder> offerOrders) {
        this.offerOrders = offerOrders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offer)) {
            return false;
        }
        return id != null && id.equals(((Offer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Offer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", dateBegin='" + getDateBegin() + "'" +
            ", dateEnd='" + getDateEnd() + "'" +
            ", duration='" + getDuration() + "'" +
            ", status='" + getStatus() + "'" +
            ", productId=" + getProductId() +
            ", saleUnitPriceBeforeDiscount=" + getSaleUnitPriceBeforeDiscount() +
            ", discountRate=" + getDiscountRate() +
            ", saleUnitPriceAfterDiscount=" + getSaleUnitPriceAfterDiscount() +
            ", withTVA='" + isWithTVA() + "'" +
            ", withTax='" + isWithTax() + "'" +
            ", isActivated='" + isIsActivated() + "'" +
            ", companyID=" + getCompanyID() +
            "}";
    }
}
