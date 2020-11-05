package org.pikasoft.mooin.mooincashier.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.pikasoft.mooin.mooincashier.domain.enumeration.CashierReceiptStatusEnum;

/**
 * CashierReceipt entity.\n@author The Moo3in team.
 */
@ApiModel(description = "CashierReceipt entity.\n@author The Moo3in team.")
@Entity
@Table(name = "cashier_receipt")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CashierReceipt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "reference")
    private String reference;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CashierReceiptStatusEnum status;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_ht", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalHT;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_tva", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalTVA;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "tota_ttc", precision = 21, scale = 2, nullable = false)
    private BigDecimal totaTTC;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "is_converted")
    private Boolean isConverted;

    @Column(name = "with_tva")
    private Boolean withTVA;

    @Column(name = "with_tax")
    private Boolean withTax;

    @OneToMany(mappedBy = "cashierReceipt")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CashierReceiptItem> cashierReceiptItems = new HashSet<>();

    @OneToMany(mappedBy = "cashierReceipt")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CashierReceiptPay> cashierReceiptPays = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "cashierReceipts", allowSetters = true)
    private Cashier cashier;

    @ManyToOne
    @JsonIgnoreProperties(value = "cashierReceipts", allowSetters = true)
    private CashierCostumer cashierCostumer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public CashierReceipt number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getReference() {
        return reference;
    }

    public CashierReceipt reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public CashierReceiptStatusEnum getStatus() {
        return status;
    }

    public CashierReceipt status(CashierReceiptStatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(CashierReceiptStatusEnum status) {
        this.status = status;
    }

    public BigDecimal getTotalHT() {
        return totalHT;
    }

    public CashierReceipt totalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
        return this;
    }

    public void setTotalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
    }

    public BigDecimal getTotalTVA() {
        return totalTVA;
    }

    public CashierReceipt totalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
        return this;
    }

    public void setTotalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
    }

    public BigDecimal getTotaTTC() {
        return totaTTC;
    }

    public CashierReceipt totaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
        return this;
    }

    public void setTotaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
    }

    public LocalDate getDate() {
        return date;
    }

    public CashierReceipt date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public CashierReceipt dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean isIsConverted() {
        return isConverted;
    }

    public CashierReceipt isConverted(Boolean isConverted) {
        this.isConverted = isConverted;
        return this;
    }

    public void setIsConverted(Boolean isConverted) {
        this.isConverted = isConverted;
    }

    public Boolean isWithTVA() {
        return withTVA;
    }

    public CashierReceipt withTVA(Boolean withTVA) {
        this.withTVA = withTVA;
        return this;
    }

    public void setWithTVA(Boolean withTVA) {
        this.withTVA = withTVA;
    }

    public Boolean isWithTax() {
        return withTax;
    }

    public CashierReceipt withTax(Boolean withTax) {
        this.withTax = withTax;
        return this;
    }

    public void setWithTax(Boolean withTax) {
        this.withTax = withTax;
    }

    public Set<CashierReceiptItem> getCashierReceiptItems() {
        return cashierReceiptItems;
    }

    public CashierReceipt cashierReceiptItems(Set<CashierReceiptItem> cashierReceiptItems) {
        this.cashierReceiptItems = cashierReceiptItems;
        return this;
    }

    public CashierReceipt addCashierReceiptItem(CashierReceiptItem cashierReceiptItem) {
        this.cashierReceiptItems.add(cashierReceiptItem);
        cashierReceiptItem.setCashierReceipt(this);
        return this;
    }

    public CashierReceipt removeCashierReceiptItem(CashierReceiptItem cashierReceiptItem) {
        this.cashierReceiptItems.remove(cashierReceiptItem);
        cashierReceiptItem.setCashierReceipt(null);
        return this;
    }

    public void setCashierReceiptItems(Set<CashierReceiptItem> cashierReceiptItems) {
        this.cashierReceiptItems = cashierReceiptItems;
    }

    public Set<CashierReceiptPay> getCashierReceiptPays() {
        return cashierReceiptPays;
    }

    public CashierReceipt cashierReceiptPays(Set<CashierReceiptPay> cashierReceiptPays) {
        this.cashierReceiptPays = cashierReceiptPays;
        return this;
    }

    public CashierReceipt addCashierReceiptPay(CashierReceiptPay cashierReceiptPay) {
        this.cashierReceiptPays.add(cashierReceiptPay);
        cashierReceiptPay.setCashierReceipt(this);
        return this;
    }

    public CashierReceipt removeCashierReceiptPay(CashierReceiptPay cashierReceiptPay) {
        this.cashierReceiptPays.remove(cashierReceiptPay);
        cashierReceiptPay.setCashierReceipt(null);
        return this;
    }

    public void setCashierReceiptPays(Set<CashierReceiptPay> cashierReceiptPays) {
        this.cashierReceiptPays = cashierReceiptPays;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public CashierReceipt cashier(Cashier cashier) {
        this.cashier = cashier;
        return this;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public CashierCostumer getCashierCostumer() {
        return cashierCostumer;
    }

    public CashierReceipt cashierCostumer(CashierCostumer cashierCostumer) {
        this.cashierCostumer = cashierCostumer;
        return this;
    }

    public void setCashierCostumer(CashierCostumer cashierCostumer) {
        this.cashierCostumer = cashierCostumer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CashierReceipt)) {
            return false;
        }
        return id != null && id.equals(((CashierReceipt) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CashierReceipt{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", reference='" + getReference() + "'" +
            ", status='" + getStatus() + "'" +
            ", totalHT=" + getTotalHT() +
            ", totalTVA=" + getTotalTVA() +
            ", totaTTC=" + getTotaTTC() +
            ", date='" + getDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", isConverted='" + isIsConverted() + "'" +
            ", withTVA='" + isWithTVA() + "'" +
            ", withTax='" + isWithTax() + "'" +
            "}";
    }
}
