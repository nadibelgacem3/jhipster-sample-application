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

import org.pikasoft.mooin.mooinbase.domain.enumeration.QuoteStatusEnum;

import org.pikasoft.mooin.mooinbase.domain.enumeration.QuoteTypeEnum;

/**
 * Quote entity.\n@author The Moo3in team.
 */
@ApiModel(description = "Quote entity.\n@author The Moo3in team.")
@Entity
@Table(name = "quote")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Quote implements Serializable {

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
    private QuoteStatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private QuoteTypeEnum type;

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

    @Column(name = "company_id")
    private Long companyID;

    @OneToMany(mappedBy = "quote")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<QuoteItem> quoteitems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "quotes", allowSetters = true)
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

    public Quote number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getReference() {
        return reference;
    }

    public Quote reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public QuoteStatusEnum getStatus() {
        return status;
    }

    public Quote status(QuoteStatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(QuoteStatusEnum status) {
        this.status = status;
    }

    public QuoteTypeEnum getType() {
        return type;
    }

    public Quote type(QuoteTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(QuoteTypeEnum type) {
        this.type = type;
    }

    public BigDecimal getTotalHT() {
        return totalHT;
    }

    public Quote totalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
        return this;
    }

    public void setTotalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
    }

    public BigDecimal getTotalTVA() {
        return totalTVA;
    }

    public Quote totalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
        return this;
    }

    public void setTotalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
    }

    public BigDecimal getTotaTTC() {
        return totaTTC;
    }

    public Quote totaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
        return this;
    }

    public void setTotaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
    }

    public LocalDate getDate() {
        return date;
    }

    public Quote date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Quote dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean isIsConverted() {
        return isConverted;
    }

    public Quote isConverted(Boolean isConverted) {
        this.isConverted = isConverted;
        return this;
    }

    public void setIsConverted(Boolean isConverted) {
        this.isConverted = isConverted;
    }

    public Long getCompanyID() {
        return companyID;
    }

    public Quote companyID(Long companyID) {
        this.companyID = companyID;
        return this;
    }

    public void setCompanyID(Long companyID) {
        this.companyID = companyID;
    }

    public Set<QuoteItem> getQuoteitems() {
        return quoteitems;
    }

    public Quote quoteitems(Set<QuoteItem> quoteItems) {
        this.quoteitems = quoteItems;
        return this;
    }

    public Quote addQuoteitem(QuoteItem quoteItem) {
        this.quoteitems.add(quoteItem);
        quoteItem.setQuote(this);
        return this;
    }

    public Quote removeQuoteitem(QuoteItem quoteItem) {
        this.quoteitems.remove(quoteItem);
        quoteItem.setQuote(null);
        return this;
    }

    public void setQuoteitems(Set<QuoteItem> quoteItems) {
        this.quoteitems = quoteItems;
    }

    public Tiers getTiers() {
        return tiers;
    }

    public Quote tiers(Tiers tiers) {
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
        if (!(o instanceof Quote)) {
            return false;
        }
        return id != null && id.equals(((Quote) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quote{" +
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
            ", isConverted='" + isIsConverted() + "'" +
            ", companyID=" + getCompanyID() +
            "}";
    }
}
