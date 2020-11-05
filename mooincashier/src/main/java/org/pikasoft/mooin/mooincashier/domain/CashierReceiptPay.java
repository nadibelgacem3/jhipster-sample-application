package org.pikasoft.mooin.mooincashier.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import org.pikasoft.mooin.mooincashier.domain.enumeration.CashierReceiptPayMeth;

/**
 * CashierReceiptPay entity.\n@author The Moo3in team.
 */
@ApiModel(description = "CashierReceiptPay entity.\n@author The Moo3in team.")
@Entity
@Table(name = "cashier_receipt_pay")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CashierReceiptPay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "details")
    private String details;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "payment_date")
    private Instant paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private CashierReceiptPayMeth paymentMethod;

    @Column(name = "bank_checkor_trait_number")
    private String bankCheckorTraitNumber;

    @Lob
    @Column(name = "image_justif")
    private byte[] imageJustif;

    @Column(name = "image_justif_content_type")
    private String imageJustifContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "cashierReceiptPays", allowSetters = true)
    private CashierReceipt cashierReceipt;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public CashierReceiptPay details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CashierReceiptPay amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public CashierReceiptPay paymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public CashierReceiptPayMeth getPaymentMethod() {
        return paymentMethod;
    }

    public CashierReceiptPay paymentMethod(CashierReceiptPayMeth paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(CashierReceiptPayMeth paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getBankCheckorTraitNumber() {
        return bankCheckorTraitNumber;
    }

    public CashierReceiptPay bankCheckorTraitNumber(String bankCheckorTraitNumber) {
        this.bankCheckorTraitNumber = bankCheckorTraitNumber;
        return this;
    }

    public void setBankCheckorTraitNumber(String bankCheckorTraitNumber) {
        this.bankCheckorTraitNumber = bankCheckorTraitNumber;
    }

    public byte[] getImageJustif() {
        return imageJustif;
    }

    public CashierReceiptPay imageJustif(byte[] imageJustif) {
        this.imageJustif = imageJustif;
        return this;
    }

    public void setImageJustif(byte[] imageJustif) {
        this.imageJustif = imageJustif;
    }

    public String getImageJustifContentType() {
        return imageJustifContentType;
    }

    public CashierReceiptPay imageJustifContentType(String imageJustifContentType) {
        this.imageJustifContentType = imageJustifContentType;
        return this;
    }

    public void setImageJustifContentType(String imageJustifContentType) {
        this.imageJustifContentType = imageJustifContentType;
    }

    public CashierReceipt getCashierReceipt() {
        return cashierReceipt;
    }

    public CashierReceiptPay cashierReceipt(CashierReceipt cashierReceipt) {
        this.cashierReceipt = cashierReceipt;
        return this;
    }

    public void setCashierReceipt(CashierReceipt cashierReceipt) {
        this.cashierReceipt = cashierReceipt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CashierReceiptPay)) {
            return false;
        }
        return id != null && id.equals(((CashierReceiptPay) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CashierReceiptPay{" +
            "id=" + getId() +
            ", details='" + getDetails() + "'" +
            ", amount=" + getAmount() +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", bankCheckorTraitNumber='" + getBankCheckorTraitNumber() + "'" +
            ", imageJustif='" + getImageJustif() + "'" +
            ", imageJustifContentType='" + getImageJustifContentType() + "'" +
            "}";
    }
}
