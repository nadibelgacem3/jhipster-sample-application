package org.pikasoft.mooin.mooinbase.domain;

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

import org.pikasoft.mooin.mooinbase.domain.enumeration.InvoiceStatusEnum;

import org.pikasoft.mooin.mooinbase.domain.enumeration.InvoiceTypeEnum;

/**
 * Invoice entity.\n@author The Moo3in team.
 */
@ApiModel(description = "Invoice entity.\n@author The Moo3in team.")
@Entity
@Table(name = "invoice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Invoice implements Serializable {

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
    private InvoiceStatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private InvoiceTypeEnum type;

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

    @Column(name = "company_id")
    private Long companyID;

    @OneToMany(mappedBy = "invoice")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<InvoiceItem> invoiceItems = new HashSet<>();

    @OneToMany(mappedBy = "invoice")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<InvoicePaymentHistory> invoicePaymentHistories = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "invoices", allowSetters = true)
    private Tiers tiers;

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

    public Invoice number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getReference() {
        return reference;
    }

    public Invoice reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public InvoiceStatusEnum getStatus() {
        return status;
    }

    public Invoice status(InvoiceStatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(InvoiceStatusEnum status) {
        this.status = status;
    }

    public InvoiceTypeEnum getType() {
        return type;
    }

    public Invoice type(InvoiceTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(InvoiceTypeEnum type) {
        this.type = type;
    }

    public BigDecimal getTotalHT() {
        return totalHT;
    }

    public Invoice totalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
        return this;
    }

    public void setTotalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
    }

    public BigDecimal getTotalTVA() {
        return totalTVA;
    }

    public Invoice totalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
        return this;
    }

    public void setTotalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
    }

    public BigDecimal getTotaTTC() {
        return totaTTC;
    }

    public Invoice totaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
        return this;
    }

    public void setTotaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
    }

    public LocalDate getDate() {
        return date;
    }

    public Invoice date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Invoice dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Long getCompanyID() {
        return companyID;
    }

    public Invoice companyID(Long companyID) {
        this.companyID = companyID;
        return this;
    }

    public void setCompanyID(Long companyID) {
        this.companyID = companyID;
    }

    public Set<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public Invoice invoiceItems(Set<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
        return this;
    }

    public Invoice addInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItems.add(invoiceItem);
        invoiceItem.setInvoice(this);
        return this;
    }

    public Invoice removeInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItems.remove(invoiceItem);
        invoiceItem.setInvoice(null);
        return this;
    }

    public void setInvoiceItems(Set<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public Set<InvoicePaymentHistory> getInvoicePaymentHistories() {
        return invoicePaymentHistories;
    }

    public Invoice invoicePaymentHistories(Set<InvoicePaymentHistory> invoicePaymentHistories) {
        this.invoicePaymentHistories = invoicePaymentHistories;
        return this;
    }

    public Invoice addInvoicePaymentHistory(InvoicePaymentHistory invoicePaymentHistory) {
        this.invoicePaymentHistories.add(invoicePaymentHistory);
        invoicePaymentHistory.setInvoice(this);
        return this;
    }

    public Invoice removeInvoicePaymentHistory(InvoicePaymentHistory invoicePaymentHistory) {
        this.invoicePaymentHistories.remove(invoicePaymentHistory);
        invoicePaymentHistory.setInvoice(null);
        return this;
    }

    public void setInvoicePaymentHistories(Set<InvoicePaymentHistory> invoicePaymentHistories) {
        this.invoicePaymentHistories = invoicePaymentHistories;
    }

    public Tiers getTiers() {
        return tiers;
    }

    public Invoice tiers(Tiers tiers) {
        this.tiers = tiers;
        return this;
    }

    public void setTiers(Tiers tiers) {
        this.tiers = tiers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        return id != null && id.equals(((Invoice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", reference='" + getReference() + "'" +
            ", status='" + getStatus() + "'" +
            ", type='" + getType() + "'" +
            ", totalHT=" + getTotalHT() +
            ", totalTVA=" + getTotalTVA() +
            ", totaTTC=" + getTotaTTC() +
            ", date='" + getDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", companyID=" + getCompanyID() +
            "}";
    }
}
