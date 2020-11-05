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
import java.util.HashSet;
import java.util.Set;

import org.pikasoft.mooin.mooincompanies.domain.enumeration.CompanyPaymentMethod;

import org.pikasoft.mooin.mooincompanies.domain.enumeration.CompanyModulePaymentStatus;

/**
 * CompanyBillPayment entity.\n@author The Moo3in team.
 */
@ApiModel(description = "CompanyBillPayment entity.\n@author The Moo3in team.")
@Entity
@Table(name = "company_bill_payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompanyBillPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "details")
    private String details;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private CompanyPaymentMethod paymentMethod;

    @Column(name = "bank_checkor_trait_number")
    private String bankCheckorTraitNumber;

    @Lob
    @Column(name = "image_justif")
    private byte[] imageJustif;

    @Column(name = "image_justif_content_type")
    private String imageJustifContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CompanyModulePaymentStatus status;

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

    @Column(name = "with_tva")
    private Boolean withTVA;

    @Column(name = "with_tax")
    private Boolean withTax;

    @OneToMany(mappedBy = "companyBillPayment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CompanyBillPaymentItem> companyBillPaymentItems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "companyBillPayments", allowSetters = true)
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

    public CompanyBillPayment number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDetails() {
        return details;
    }

    public CompanyBillPayment details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public CompanyBillPayment paymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public CompanyPaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public CompanyBillPayment paymentMethod(CompanyPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(CompanyPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getBankCheckorTraitNumber() {
        return bankCheckorTraitNumber;
    }

    public CompanyBillPayment bankCheckorTraitNumber(String bankCheckorTraitNumber) {
        this.bankCheckorTraitNumber = bankCheckorTraitNumber;
        return this;
    }

    public void setBankCheckorTraitNumber(String bankCheckorTraitNumber) {
        this.bankCheckorTraitNumber = bankCheckorTraitNumber;
    }

    public byte[] getImageJustif() {
        return imageJustif;
    }

    public CompanyBillPayment imageJustif(byte[] imageJustif) {
        this.imageJustif = imageJustif;
        return this;
    }

    public void setImageJustif(byte[] imageJustif) {
        this.imageJustif = imageJustif;
    }

    public String getImageJustifContentType() {
        return imageJustifContentType;
    }

    public CompanyBillPayment imageJustifContentType(String imageJustifContentType) {
        this.imageJustifContentType = imageJustifContentType;
        return this;
    }

    public void setImageJustifContentType(String imageJustifContentType) {
        this.imageJustifContentType = imageJustifContentType;
    }

    public CompanyModulePaymentStatus getStatus() {
        return status;
    }

    public CompanyBillPayment status(CompanyModulePaymentStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(CompanyModulePaymentStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalHT() {
        return totalHT;
    }

    public CompanyBillPayment totalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
        return this;
    }

    public void setTotalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
    }

    public BigDecimal getTotalTVA() {
        return totalTVA;
    }

    public CompanyBillPayment totalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
        return this;
    }

    public void setTotalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
    }

    public BigDecimal getTotaTTC() {
        return totaTTC;
    }

    public CompanyBillPayment totaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
        return this;
    }

    public void setTotaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
    }

    public LocalDate getDate() {
        return date;
    }

    public CompanyBillPayment date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public CompanyBillPayment dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean isWithTVA() {
        return withTVA;
    }

    public CompanyBillPayment withTVA(Boolean withTVA) {
        this.withTVA = withTVA;
        return this;
    }

    public void setWithTVA(Boolean withTVA) {
        this.withTVA = withTVA;
    }

    public Boolean isWithTax() {
        return withTax;
    }

    public CompanyBillPayment withTax(Boolean withTax) {
        this.withTax = withTax;
        return this;
    }

    public void setWithTax(Boolean withTax) {
        this.withTax = withTax;
    }

    public Set<CompanyBillPaymentItem> getCompanyBillPaymentItems() {
        return companyBillPaymentItems;
    }

    public CompanyBillPayment companyBillPaymentItems(Set<CompanyBillPaymentItem> companyBillPaymentItems) {
        this.companyBillPaymentItems = companyBillPaymentItems;
        return this;
    }

    public CompanyBillPayment addCompanyBillPaymentItem(CompanyBillPaymentItem companyBillPaymentItem) {
        this.companyBillPaymentItems.add(companyBillPaymentItem);
        companyBillPaymentItem.setCompanyBillPayment(this);
        return this;
    }

    public CompanyBillPayment removeCompanyBillPaymentItem(CompanyBillPaymentItem companyBillPaymentItem) {
        this.companyBillPaymentItems.remove(companyBillPaymentItem);
        companyBillPaymentItem.setCompanyBillPayment(null);
        return this;
    }

    public void setCompanyBillPaymentItems(Set<CompanyBillPaymentItem> companyBillPaymentItems) {
        this.companyBillPaymentItems = companyBillPaymentItems;
    }

    public Company getCompany() {
        return company;
    }

    public CompanyBillPayment company(Company company) {
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
        if (!(o instanceof CompanyBillPayment)) {
            return false;
        }
        return id != null && id.equals(((CompanyBillPayment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyBillPayment{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", details='" + getDetails() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", bankCheckorTraitNumber='" + getBankCheckorTraitNumber() + "'" +
            ", imageJustif='" + getImageJustif() + "'" +
            ", imageJustifContentType='" + getImageJustifContentType() + "'" +
            ", status='" + getStatus() + "'" +
            ", totalHT=" + getTotalHT() +
            ", totalTVA=" + getTotalTVA() +
            ", totaTTC=" + getTotaTTC() +
            ", date='" + getDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", withTVA='" + isWithTVA() + "'" +
            ", withTax='" + isWithTax() + "'" +
            "}";
    }
}
