package org.pikasoft.mooin.mooincompanies.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * CompanyBankAccount entity.\n@author The Moo3in team.
 */
@ApiModel(description = "CompanyBankAccount entity.\n@author The Moo3in team.")
@Entity
@Table(name = "company_bank_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompanyBankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "reference")
    private String reference;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "iban")
    private String iban;

    @Column(name = "swift")
    private String swift;

    @Column(name = "type")
    private String type;

    @OneToOne(mappedBy = "companyBankAccount")
    @JsonIgnore
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public CompanyBankAccount reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getBankName() {
        return bankName;
    }

    public CompanyBankAccount bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public CompanyBankAccount bankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
        return this;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getIban() {
        return iban;
    }

    public CompanyBankAccount iban(String iban) {
        this.iban = iban;
        return this;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getSwift() {
        return swift;
    }

    public CompanyBankAccount swift(String swift) {
        this.swift = swift;
        return this;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getType() {
        return type;
    }

    public CompanyBankAccount type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Company getCompany() {
        return company;
    }

    public CompanyBankAccount company(Company company) {
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
        if (!(o instanceof CompanyBankAccount)) {
            return false;
        }
        return id != null && id.equals(((CompanyBankAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyBankAccount{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", bankAccountNumber='" + getBankAccountNumber() + "'" +
            ", iban='" + getIban() + "'" +
            ", swift='" + getSwift() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
