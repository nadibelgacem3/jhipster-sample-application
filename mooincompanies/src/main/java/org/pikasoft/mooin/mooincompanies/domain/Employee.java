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

import org.pikasoft.mooin.mooincompanies.domain.enumeration.Gender;

/**
 * Employee entity.\n@author The Moo3in team.
 */
@ApiModel(description = "Employee entity.\n@author The Moo3in team.")
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 3)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "job_title")
    private String jobTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "date_ofborth")
    private LocalDate dateOfborth;

    @Column(name = "date_of_recruitment")
    private LocalDate dateOfRecruitment;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "salary", precision = 21, scale = 2)
    private BigDecimal salary;

    @Column(name = "commission_pct")
    private Double commissionPct;

    @Column(name = "rates")
    private Integer rates;

    @OneToOne
    @JoinColumn(unique = true)
    private EmployeeLocation employeeLocation;

    @OneToMany(mappedBy = "employee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmployeePayment> employeePayments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "employees", allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Employee jobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Gender getGender() {
        return gender;
    }

    public Employee gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfborth() {
        return dateOfborth;
    }

    public Employee dateOfborth(LocalDate dateOfborth) {
        this.dateOfborth = dateOfborth;
        return this;
    }

    public void setDateOfborth(LocalDate dateOfborth) {
        this.dateOfborth = dateOfborth;
    }

    public LocalDate getDateOfRecruitment() {
        return dateOfRecruitment;
    }

    public Employee dateOfRecruitment(LocalDate dateOfRecruitment) {
        this.dateOfRecruitment = dateOfRecruitment;
        return this;
    }

    public void setDateOfRecruitment(LocalDate dateOfRecruitment) {
        this.dateOfRecruitment = dateOfRecruitment;
    }

    public byte[] getImage() {
        return image;
    }

    public Employee image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Employee imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getEmail() {
        return email;
    }

    public Employee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Employee phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public Employee salary(BigDecimal salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Double getCommissionPct() {
        return commissionPct;
    }

    public Employee commissionPct(Double commissionPct) {
        this.commissionPct = commissionPct;
        return this;
    }

    public void setCommissionPct(Double commissionPct) {
        this.commissionPct = commissionPct;
    }

    public Integer getRates() {
        return rates;
    }

    public Employee rates(Integer rates) {
        this.rates = rates;
        return this;
    }

    public void setRates(Integer rates) {
        this.rates = rates;
    }

    public EmployeeLocation getEmployeeLocation() {
        return employeeLocation;
    }

    public Employee employeeLocation(EmployeeLocation employeeLocation) {
        this.employeeLocation = employeeLocation;
        return this;
    }

    public void setEmployeeLocation(EmployeeLocation employeeLocation) {
        this.employeeLocation = employeeLocation;
    }

    public Set<EmployeePayment> getEmployeePayments() {
        return employeePayments;
    }

    public Employee employeePayments(Set<EmployeePayment> employeePayments) {
        this.employeePayments = employeePayments;
        return this;
    }

    public Employee addEmployeePayment(EmployeePayment employeePayment) {
        this.employeePayments.add(employeePayment);
        employeePayment.setEmployee(this);
        return this;
    }

    public Employee removeEmployeePayment(EmployeePayment employeePayment) {
        this.employeePayments.remove(employeePayment);
        employeePayment.setEmployee(null);
        return this;
    }

    public void setEmployeePayments(Set<EmployeePayment> employeePayments) {
        this.employeePayments = employeePayments;
    }

    public Company getCompany() {
        return company;
    }

    public Employee company(Company company) {
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
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", jobTitle='" + getJobTitle() + "'" +
            ", gender='" + getGender() + "'" +
            ", dateOfborth='" + getDateOfborth() + "'" +
            ", dateOfRecruitment='" + getDateOfRecruitment() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", salary=" + getSalary() +
            ", commissionPct=" + getCommissionPct() +
            ", rates=" + getRates() +
            "}";
    }
}
