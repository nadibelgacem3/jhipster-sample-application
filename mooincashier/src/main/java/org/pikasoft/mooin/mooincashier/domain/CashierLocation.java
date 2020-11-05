package org.pikasoft.mooin.mooincashier.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * CashierLocation entity.\n@author The Moo3in team.
 */
@ApiModel(description = "CashierLocation entity.\n@author The Moo3in team.")
@Entity
@Table(name = "cashier_location")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CashierLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "local_number")
    private String localNumber;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String stateProvince;

    @Column(name = "country_name")
    private String countryName;

    @Lob
    @Column(name = "flag")
    private byte[] flag;

    @Column(name = "flag_content_type")
    private String flagContentType;

    @OneToOne(mappedBy = "cashierLocation")
    @JsonIgnore
    private Cashier cashier;

    @OneToOne(mappedBy = "cashierLocation")
    @JsonIgnore
    private CashierCostumer cashierCostumer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalNumber() {
        return localNumber;
    }

    public CashierLocation localNumber(String localNumber) {
        this.localNumber = localNumber;
        return this;
    }

    public void setLocalNumber(String localNumber) {
        this.localNumber = localNumber;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public CashierLocation streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public CashierLocation postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public CashierLocation city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public CashierLocation stateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getCountryName() {
        return countryName;
    }

    public CashierLocation countryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public byte[] getFlag() {
        return flag;
    }

    public CashierLocation flag(byte[] flag) {
        this.flag = flag;
        return this;
    }

    public void setFlag(byte[] flag) {
        this.flag = flag;
    }

    public String getFlagContentType() {
        return flagContentType;
    }

    public CashierLocation flagContentType(String flagContentType) {
        this.flagContentType = flagContentType;
        return this;
    }

    public void setFlagContentType(String flagContentType) {
        this.flagContentType = flagContentType;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public CashierLocation cashier(Cashier cashier) {
        this.cashier = cashier;
        return this;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public CashierCostumer getCashierCostumer() {
        return cashierCostumer;
    }

    public CashierLocation cashierCostumer(CashierCostumer cashierCostumer) {
        this.cashierCostumer = cashierCostumer;
        return this;
    }

    public void setCashierCostumer(CashierCostumer cashierCostumer) {
        this.cashierCostumer = cashierCostumer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CashierLocation)) {
            return false;
        }
        return id != null && id.equals(((CashierLocation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CashierLocation{" +
            "id=" + getId() +
            ", localNumber='" + getLocalNumber() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", city='" + getCity() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            ", countryName='" + getCountryName() + "'" +
            ", flag='" + getFlag() + "'" +
            ", flagContentType='" + getFlagContentType() + "'" +
            "}";
    }
}
