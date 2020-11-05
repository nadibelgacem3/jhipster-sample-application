package org.pikasoft.mooin.mooincompanies.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

/**
 * EmployeePayment entity.\n@author The Moo3in team.
 */
@ApiModel(description = "EmployeePayment entity.\n@author The Moo3in team.")
@Entity
@Table(name = "employee_payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeePayment implements Serializable {

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

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Lob
    @Column(name = "image_justif")
    private byte[] imageJustif;

    @Column(name = "image_justif_content_type")
    private String imageJustifContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "employeePayments", allowSetters = true)
    private Employee employee;

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

    public EmployeePayment details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public EmployeePayment amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public EmployeePayment paymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public EmployeePayment fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public EmployeePayment toDate(LocalDate toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public byte[] getImageJustif() {
        return imageJustif;
    }

    public EmployeePayment imageJustif(byte[] imageJustif) {
        this.imageJustif = imageJustif;
        return this;
    }

    public void setImageJustif(byte[] imageJustif) {
        this.imageJustif = imageJustif;
    }

    public String getImageJustifContentType() {
        return imageJustifContentType;
    }

    public EmployeePayment imageJustifContentType(String imageJustifContentType) {
        this.imageJustifContentType = imageJustifContentType;
        return this;
    }

    public void setImageJustifContentType(String imageJustifContentType) {
        this.imageJustifContentType = imageJustifContentType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public EmployeePayment employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeePayment)) {
            return false;
        }
        return id != null && id.equals(((EmployeePayment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeePayment{" +
            "id=" + getId() +
            ", details='" + getDetails() + "'" +
            ", amount=" + getAmount() +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", imageJustif='" + getImageJustif() + "'" +
            ", imageJustifContentType='" + getImageJustifContentType() + "'" +
            "}";
    }
}
