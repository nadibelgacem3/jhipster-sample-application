package org.pikasoft.mooin.mooincashier.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * CashierProduct entity.\n@author The Moo3in team.
 */
@ApiModel(description = "CashierProduct entity.\n@author The Moo3in team.")
@Entity
@Table(name = "cashier_product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CashierProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private Long productID;

    @NotNull
    @Column(name = "cashier_prod_name", nullable = false)
    private String cashierProdName;

    @NotNull
    @Column(name = "cashier_prod_qty", nullable = false)
    private Double cashierProdQty;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "cashier_prod_purchase_unit_price", nullable = false)
    private Double cashierProdPurchaseUnitPrice;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "cashier_prod_sale_unit_price", nullable = false)
    private Double cashierProdSaleUnitPrice;

    @Column(name = "cashier_prod_stock_limit")
    private Double cashierProdStockLimit;

    @Column(name = "cashier_prod_stock_limit_alert")
    private Boolean cashierProdStockLimitAlert;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductID() {
        return productID;
    }

    public CashierProduct productID(Long productID) {
        this.productID = productID;
        return this;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public String getCashierProdName() {
        return cashierProdName;
    }

    public CashierProduct cashierProdName(String cashierProdName) {
        this.cashierProdName = cashierProdName;
        return this;
    }

    public void setCashierProdName(String cashierProdName) {
        this.cashierProdName = cashierProdName;
    }

    public Double getCashierProdQty() {
        return cashierProdQty;
    }

    public CashierProduct cashierProdQty(Double cashierProdQty) {
        this.cashierProdQty = cashierProdQty;
        return this;
    }

    public void setCashierProdQty(Double cashierProdQty) {
        this.cashierProdQty = cashierProdQty;
    }

    public Double getCashierProdPurchaseUnitPrice() {
        return cashierProdPurchaseUnitPrice;
    }

    public CashierProduct cashierProdPurchaseUnitPrice(Double cashierProdPurchaseUnitPrice) {
        this.cashierProdPurchaseUnitPrice = cashierProdPurchaseUnitPrice;
        return this;
    }

    public void setCashierProdPurchaseUnitPrice(Double cashierProdPurchaseUnitPrice) {
        this.cashierProdPurchaseUnitPrice = cashierProdPurchaseUnitPrice;
    }

    public Double getCashierProdSaleUnitPrice() {
        return cashierProdSaleUnitPrice;
    }

    public CashierProduct cashierProdSaleUnitPrice(Double cashierProdSaleUnitPrice) {
        this.cashierProdSaleUnitPrice = cashierProdSaleUnitPrice;
        return this;
    }

    public void setCashierProdSaleUnitPrice(Double cashierProdSaleUnitPrice) {
        this.cashierProdSaleUnitPrice = cashierProdSaleUnitPrice;
    }

    public Double getCashierProdStockLimit() {
        return cashierProdStockLimit;
    }

    public CashierProduct cashierProdStockLimit(Double cashierProdStockLimit) {
        this.cashierProdStockLimit = cashierProdStockLimit;
        return this;
    }

    public void setCashierProdStockLimit(Double cashierProdStockLimit) {
        this.cashierProdStockLimit = cashierProdStockLimit;
    }

    public Boolean isCashierProdStockLimitAlert() {
        return cashierProdStockLimitAlert;
    }

    public CashierProduct cashierProdStockLimitAlert(Boolean cashierProdStockLimitAlert) {
        this.cashierProdStockLimitAlert = cashierProdStockLimitAlert;
        return this;
    }

    public void setCashierProdStockLimitAlert(Boolean cashierProdStockLimitAlert) {
        this.cashierProdStockLimitAlert = cashierProdStockLimitAlert;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CashierProduct)) {
            return false;
        }
        return id != null && id.equals(((CashierProduct) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CashierProduct{" +
            "id=" + getId() +
            ", productID=" + getProductID() +
            ", cashierProdName='" + getCashierProdName() + "'" +
            ", cashierProdQty=" + getCashierProdQty() +
            ", cashierProdPurchaseUnitPrice=" + getCashierProdPurchaseUnitPrice() +
            ", cashierProdSaleUnitPrice=" + getCashierProdSaleUnitPrice() +
            ", cashierProdStockLimit=" + getCashierProdStockLimit() +
            ", cashierProdStockLimitAlert='" + isCashierProdStockLimitAlert() + "'" +
            "}";
    }
}
