package org.pikasoft.mooin.mooincompanies.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.pikasoft.mooin.mooincompanies.domain.enumeration.TransactionCompTypeEnum;

import org.pikasoft.mooin.mooincompanies.domain.enumeration.TransactionCompPaymentMethod;

/**
 * TransactionComp entity.\n@author The Moo3in team.
 */
@ApiModel(description = "TransactionComp entity.\n@author The Moo3in team.")
@Entity
@Table(name = "transaction_comp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TransactionComp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "details")
    private String details;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransactionCompTypeEnum type;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private TransactionCompPaymentMethod paymentMethod;

    @Column(name = "with_tva")
    private Boolean withTVA;

    @DecimalMin(value = "0")
    @Column(name = "total_ht", precision = 21, scale = 2)
    private BigDecimal totalHT;

    @DecimalMin(value = "0")
    @Column(name = "total_tva", precision = 21, scale = 2)
    private BigDecimal totalTVA;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "tota_ttc", precision = 21, scale = 2, nullable = false)
    private BigDecimal totaTTC;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    @JsonIgnoreProperties(value = "transactionComps", allowSetters = true)
    private Company company;

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

    public TransactionComp number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDetails() {
        return details;
    }

    public TransactionComp details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public TransactionCompTypeEnum getType() {
        return type;
    }

    public TransactionComp type(TransactionCompTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(TransactionCompTypeEnum type) {
        this.type = type;
    }

    public TransactionCompPaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public TransactionComp paymentMethod(TransactionCompPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(TransactionCompPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Boolean isWithTVA() {
        return withTVA;
    }

    public TransactionComp withTVA(Boolean withTVA) {
        this.withTVA = withTVA;
        return this;
    }

    public void setWithTVA(Boolean withTVA) {
        this.withTVA = withTVA;
    }

    public BigDecimal getTotalHT() {
        return totalHT;
    }

    public TransactionComp totalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
        return this;
    }

    public void setTotalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
    }

    public BigDecimal getTotalTVA() {
        return totalTVA;
    }

    public TransactionComp totalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
        return this;
    }

    public void setTotalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
    }

    public BigDecimal getTotaTTC() {
        return totaTTC;
    }

    public TransactionComp totaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
        return this;
    }

    public void setTotaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
    }

    public LocalDate getDate() {
        return date;
    }

    public TransactionComp date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Company getCompany() {
        return company;
    }

    public TransactionComp company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionComp)) {
            return false;
        }
        return id != null && id.equals(((TransactionComp) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionComp{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", details='" + getDetails() + "'" +
            ", type='" + getType() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", withTVA='" + isWithTVA() + "'" +
            ", totalHT=" + getTotalHT() +
            ", totalTVA=" + getTotalTVA() +
            ", totaTTC=" + getTotaTTC() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
