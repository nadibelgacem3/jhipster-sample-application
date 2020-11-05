package org.pikasoft.mooin.mooinbase.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.pikasoft.mooin.mooinbase.domain.enumeration.ProductUnitEnum;

import org.pikasoft.mooin.mooinbase.domain.enumeration.Size;

/**
 * Product entity.\n@author The Moo3in team.
 */
@ApiModel(description = "Product entity.\n@author The Moo3in team.")
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "company_id")
    private Long companyID;

    @Column(name = "reference")
    private String reference;

    @Column(name = "reference_provider")
    private String referenceProvider;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "purchase_unit_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal purchaseUnitPrice;

    @NotNull
    @Min(value = 0)
    @Column(name = "sale_unit_price", nullable = false)
    private Integer saleUnitPrice;

    @Column(name = "stocklimit")
    private Double stocklimit;

    @Column(name = "stocklimit_alert")
    private Boolean stocklimitAlert;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit_type")
    private ProductUnitEnum unitType;

    @Enumerated(EnumType.STRING)
    @Column(name = "size")
    private Size size;

    @Column(name = "color")
    private String color;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "is_displayed_in_cashier")
    private Boolean isDisplayedInCashier;

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Movement> movements = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TaxItem> taxItems = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TVAItem> tvaItems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private ProductCategory productCategory;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private ProductMark productMark;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private Depot depot;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyID() {
        return companyID;
    }

    public Product companyID(Long companyID) {
        this.companyID = companyID;
        return this;
    }

    public void setCompanyID(Long companyID) {
        this.companyID = companyID;
    }

    public String getReference() {
        return reference;
    }

    public Product reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReferenceProvider() {
        return referenceProvider;
    }

    public Product referenceProvider(String referenceProvider) {
        this.referenceProvider = referenceProvider;
        return this;
    }

    public void setReferenceProvider(String referenceProvider) {
        this.referenceProvider = referenceProvider;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Product quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPurchaseUnitPrice() {
        return purchaseUnitPrice;
    }

    public Product purchaseUnitPrice(BigDecimal purchaseUnitPrice) {
        this.purchaseUnitPrice = purchaseUnitPrice;
        return this;
    }

    public void setPurchaseUnitPrice(BigDecimal purchaseUnitPrice) {
        this.purchaseUnitPrice = purchaseUnitPrice;
    }

    public Integer getSaleUnitPrice() {
        return saleUnitPrice;
    }

    public Product saleUnitPrice(Integer saleUnitPrice) {
        this.saleUnitPrice = saleUnitPrice;
        return this;
    }

    public void setSaleUnitPrice(Integer saleUnitPrice) {
        this.saleUnitPrice = saleUnitPrice;
    }

    public Double getStocklimit() {
        return stocklimit;
    }

    public Product stocklimit(Double stocklimit) {
        this.stocklimit = stocklimit;
        return this;
    }

    public void setStocklimit(Double stocklimit) {
        this.stocklimit = stocklimit;
    }

    public Boolean isStocklimitAlert() {
        return stocklimitAlert;
    }

    public Product stocklimitAlert(Boolean stocklimitAlert) {
        this.stocklimitAlert = stocklimitAlert;
        return this;
    }

    public void setStocklimitAlert(Boolean stocklimitAlert) {
        this.stocklimitAlert = stocklimitAlert;
    }

    public ProductUnitEnum getUnitType() {
        return unitType;
    }

    public Product unitType(ProductUnitEnum unitType) {
        this.unitType = unitType;
        return this;
    }

    public void setUnitType(ProductUnitEnum unitType) {
        this.unitType = unitType;
    }

    public Size getSize() {
        return size;
    }

    public Product size(Size size) {
        this.size = size;
        return this;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public Product color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public byte[] getImage() {
        return image;
    }

    public Product image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Product imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Boolean isIsDisplayedInCashier() {
        return isDisplayedInCashier;
    }

    public Product isDisplayedInCashier(Boolean isDisplayedInCashier) {
        this.isDisplayedInCashier = isDisplayedInCashier;
        return this;
    }

    public void setIsDisplayedInCashier(Boolean isDisplayedInCashier) {
        this.isDisplayedInCashier = isDisplayedInCashier;
    }

    public Set<Movement> getMovements() {
        return movements;
    }

    public Product movements(Set<Movement> movements) {
        this.movements = movements;
        return this;
    }

    public Product addMovement(Movement movement) {
        this.movements.add(movement);
        movement.setProduct(this);
        return this;
    }

    public Product removeMovement(Movement movement) {
        this.movements.remove(movement);
        movement.setProduct(null);
        return this;
    }

    public void setMovements(Set<Movement> movements) {
        this.movements = movements;
    }

    public Set<TaxItem> getTaxItems() {
        return taxItems;
    }

    public Product taxItems(Set<TaxItem> taxItems) {
        this.taxItems = taxItems;
        return this;
    }

    public Product addTaxItem(TaxItem taxItem) {
        this.taxItems.add(taxItem);
        taxItem.setProduct(this);
        return this;
    }

    public Product removeTaxItem(TaxItem taxItem) {
        this.taxItems.remove(taxItem);
        taxItem.setProduct(null);
        return this;
    }

    public void setTaxItems(Set<TaxItem> taxItems) {
        this.taxItems = taxItems;
    }

    public Set<TVAItem> getTvaItems() {
        return tvaItems;
    }

    public Product tvaItems(Set<TVAItem> tVAItems) {
        this.tvaItems = tVAItems;
        return this;
    }

    public Product addTvaItem(TVAItem tVAItem) {
        this.tvaItems.add(tVAItem);
        tVAItem.setProduct(this);
        return this;
    }

    public Product removeTvaItem(TVAItem tVAItem) {
        this.tvaItems.remove(tVAItem);
        tVAItem.setProduct(null);
        return this;
    }

    public void setTvaItems(Set<TVAItem> tVAItems) {
        this.tvaItems = tVAItems;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public Product productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public ProductMark getProductMark() {
        return productMark;
    }

    public Product productMark(ProductMark productMark) {
        this.productMark = productMark;
        return this;
    }

    public void setProductMark(ProductMark productMark) {
        this.productMark = productMark;
    }

    public Depot getDepot() {
        return depot;
    }

    public Product depot(Depot depot) {
        this.depot = depot;
        return this;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", companyID=" + getCompanyID() +
            ", reference='" + getReference() + "'" +
            ", referenceProvider='" + getReferenceProvider() + "'" +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", purchaseUnitPrice=" + getPurchaseUnitPrice() +
            ", saleUnitPrice=" + getSaleUnitPrice() +
            ", stocklimit=" + getStocklimit() +
            ", stocklimitAlert='" + isStocklimitAlert() + "'" +
            ", unitType='" + getUnitType() + "'" +
            ", size='" + getSize() + "'" +
            ", color='" + getColor() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", isDisplayedInCashier='" + isIsDisplayedInCashier() + "'" +
            "}";
    }
}
