package org.pikasoft.mooin.mooincashier.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * CashierCostumer entity.\n@author The Moo3in team.
 */
@ApiModel(description = "CashierCostumer entity.\n@author The Moo3in team.")
@Entity
@Table(name = "cashier_costumer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CashierCostumer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Pattern(regexp = "^SC[0-9]{3}COM[0-9]{2}$")
    @Column(name = "reference")
    private String reference;

    @NotNull
    @Size(min = 4)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 3)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Size(min = 8)
    @Column(name = "phone_1")
    private String phone1;

    @Size(min = 8)
    @Column(name = "phone_2")
    private String phone2;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email")
    private String email;

    @OneToOne
    @JoinColumn(unique = true)
    private CashierLocation cashierLocation;

    @OneToMany(mappedBy = "cashierCostumer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CashierReceipt> cashierReceipts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "cashierCostumers", allowSetters = true)
    private Cashier cashier;

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

    public CashierCostumer reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getFirstName() {
        return firstName;
    }

    public CashierCostumer firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public CashierCostumer lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone1() {
        return phone1;
    }

    public CashierCostumer phone1(String phone1) {
        this.phone1 = phone1;
        return this;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public CashierCostumer phone2(String phone2) {
        this.phone2 = phone2;
        return this;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public byte[] getImage() {
        return image;
    }

    public CashierCostumer image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public CashierCostumer imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getEmail() {
        return email;
    }

    public CashierCostumer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CashierLocation getCashierLocation() {
        return cashierLocation;
    }

    public CashierCostumer cashierLocation(CashierLocation cashierLocation) {
        this.cashierLocation = cashierLocation;
        return this;
    }

    public void setCashierLocation(CashierLocation cashierLocation) {
        this.cashierLocation = cashierLocation;
    }

    public Set<CashierReceipt> getCashierReceipts() {
        return cashierReceipts;
    }

    public CashierCostumer cashierReceipts(Set<CashierReceipt> cashierReceipts) {
        this.cashierReceipts = cashierReceipts;
        return this;
    }

    public CashierCostumer addCashierReceipt(CashierReceipt cashierReceipt) {
        this.cashierReceipts.add(cashierReceipt);
        cashierReceipt.setCashierCostumer(this);
        return this;
    }

    public CashierCostumer removeCashierReceipt(CashierReceipt cashierReceipt) {
        this.cashierReceipts.remove(cashierReceipt);
        cashierReceipt.setCashierCostumer(null);
        return this;
    }

    public void setCashierReceipts(Set<CashierReceipt> cashierReceipts) {
        this.cashierReceipts = cashierReceipts;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public CashierCostumer cashier(Cashier cashier) {
        this.cashier = cashier;
        return this;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CashierCostumer)) {
            return false;
        }
        return id != null && id.equals(((CashierCostumer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CashierCostumer{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phone1='" + getPhone1() + "'" +
            ", phone2='" + getPhone2() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
