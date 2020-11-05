package org.pikasoft.mooin.mooinbase.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import org.pikasoft.mooin.mooinbase.domain.enumeration.TiersTypeEnum;

/**
 * Tiers Moo3in Micros-services
 */
@ApiModel(description = "Tiers Moo3in Micros-services")
@Entity
@Table(name = "tiers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tiers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

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

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TiersTypeEnum type;

    @NotNull
    @Column(name = "is_customer", nullable = false)
    private Boolean isCustomer;

    @NotNull
    @Column(name = "is_supplier", nullable = false)
    private Boolean isSupplier;

    @Column(name = "company_id")
    private Long companyID;

    @OneToOne
    @JoinColumn(unique = true)
    private TiersLocation tiersLocation;

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

    public Tiers reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getFirstName() {
        return firstName;
    }

    public Tiers firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Tiers lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone1() {
        return phone1;
    }

    public Tiers phone1(String phone1) {
        this.phone1 = phone1;
        return this;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public Tiers phone2(String phone2) {
        this.phone2 = phone2;
        return this;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public byte[] getImage() {
        return image;
    }

    public Tiers image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Tiers imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getEmail() {
        return email;
    }

    public Tiers email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TiersTypeEnum getType() {
        return type;
    }

    public Tiers type(TiersTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(TiersTypeEnum type) {
        this.type = type;
    }

    public Boolean isIsCustomer() {
        return isCustomer;
    }

    public Tiers isCustomer(Boolean isCustomer) {
        this.isCustomer = isCustomer;
        return this;
    }

    public void setIsCustomer(Boolean isCustomer) {
        this.isCustomer = isCustomer;
    }

    public Boolean isIsSupplier() {
        return isSupplier;
    }

    public Tiers isSupplier(Boolean isSupplier) {
        this.isSupplier = isSupplier;
        return this;
    }

    public void setIsSupplier(Boolean isSupplier) {
        this.isSupplier = isSupplier;
    }

    public Long getCompanyID() {
        return companyID;
    }

    public Tiers companyID(Long companyID) {
        this.companyID = companyID;
        return this;
    }

    public void setCompanyID(Long companyID) {
        this.companyID = companyID;
    }

    public TiersLocation getTiersLocation() {
        return tiersLocation;
    }

    public Tiers tiersLocation(TiersLocation tiersLocation) {
        this.tiersLocation = tiersLocation;
        return this;
    }

    public void setTiersLocation(TiersLocation tiersLocation) {
        this.tiersLocation = tiersLocation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tiers)) {
            return false;
        }
        return id != null && id.equals(((Tiers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tiers{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phone1='" + getPhone1() + "'" +
            ", phone2='" + getPhone2() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", email='" + getEmail() + "'" +
            ", type='" + getType() + "'" +
            ", isCustomer='" + isIsCustomer() + "'" +
            ", isSupplier='" + isIsSupplier() + "'" +
            ", companyID=" + getCompanyID() +
            "}";
    }
}
