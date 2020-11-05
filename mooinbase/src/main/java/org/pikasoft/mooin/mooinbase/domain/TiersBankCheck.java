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

import org.pikasoft.mooin.mooinbase.domain.enumeration.BankCheckType;

import org.pikasoft.mooin.mooinbase.domain.enumeration.BankCheckKind;

/**
 * TiersBankCheck entity.\n@author The Moo3in team.
 */
@ApiModel(description = "TiersBankCheck entity.\n@author The Moo3in team.")
@Entity
@Table(name = "tiers_bank_check")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TiersBankCheck implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "number")
    private String number;

    @NotNull
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "bank_check_type", nullable = false)
    private BankCheckType bankCheckType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "bank_check_kind", nullable = false)
    private BankCheckKind bankCheckKind;

    @Column(name = "swift")
    private String swift;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JsonIgnoreProperties(value = "tiersBankChecks", allowSetters = true)
    private Tiers tiers;

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

    public TiersBankCheck name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public TiersBankCheck bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getNumber() {
        return number;
    }

    public TiersBankCheck number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TiersBankCheck amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public TiersBankCheck dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BankCheckType getBankCheckType() {
        return bankCheckType;
    }

    public TiersBankCheck bankCheckType(BankCheckType bankCheckType) {
        this.bankCheckType = bankCheckType;
        return this;
    }

    public void setBankCheckType(BankCheckType bankCheckType) {
        this.bankCheckType = bankCheckType;
    }

    public BankCheckKind getBankCheckKind() {
        return bankCheckKind;
    }

    public TiersBankCheck bankCheckKind(BankCheckKind bankCheckKind) {
        this.bankCheckKind = bankCheckKind;
        return this;
    }

    public void setBankCheckKind(BankCheckKind bankCheckKind) {
        this.bankCheckKind = bankCheckKind;
    }

    public String getSwift() {
        return swift;
    }

    public TiersBankCheck swift(String swift) {
        this.swift = swift;
        return this;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getType() {
        return type;
    }

    public TiersBankCheck type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Tiers getTiers() {
        return tiers;
    }

    public TiersBankCheck tiers(Tiers tiers) {
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
        if (!(o instanceof TiersBankCheck)) {
            return false;
        }
        return id != null && id.equals(((TiersBankCheck) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TiersBankCheck{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", number='" + getNumber() + "'" +
            ", amount=" + getAmount() +
            ", dueDate='" + getDueDate() + "'" +
            ", bankCheckType='" + getBankCheckType() + "'" +
            ", bankCheckKind='" + getBankCheckKind() + "'" +
            ", swift='" + getSwift() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
