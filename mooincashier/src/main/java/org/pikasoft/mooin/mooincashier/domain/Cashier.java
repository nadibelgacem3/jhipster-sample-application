package org.pikasoft.mooin.mooincashier.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * Cashier entity.\n@author The Moo3in team.
 */
@ApiModel(description = "Cashier entity.\n@author The Moo3in team.")
@Entity
@Table(name = "cashier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cashier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "with_ticket")
    private Boolean withTicket;

    @Column(name = "with_tva")
    private Boolean withTVA;

    @Column(name = "with_tax")
    private Boolean withTax;

    @Column(name = "with_appro")
    private Boolean withAppro;

    @Column(name = "theme_color")
    private String themeColor;

    @Column(name = "is_activated")
    private Boolean isActivated;

    @Column(name = "company_id")
    private Long companyID;

    @OneToOne
    @JoinColumn(unique = true)
    private CashierLocation cashierLocation;

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

    public Cashier name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isWithTicket() {
        return withTicket;
    }

    public Cashier withTicket(Boolean withTicket) {
        this.withTicket = withTicket;
        return this;
    }

    public void setWithTicket(Boolean withTicket) {
        this.withTicket = withTicket;
    }

    public Boolean isWithTVA() {
        return withTVA;
    }

    public Cashier withTVA(Boolean withTVA) {
        this.withTVA = withTVA;
        return this;
    }

    public void setWithTVA(Boolean withTVA) {
        this.withTVA = withTVA;
    }

    public Boolean isWithTax() {
        return withTax;
    }

    public Cashier withTax(Boolean withTax) {
        this.withTax = withTax;
        return this;
    }

    public void setWithTax(Boolean withTax) {
        this.withTax = withTax;
    }

    public Boolean isWithAppro() {
        return withAppro;
    }

    public Cashier withAppro(Boolean withAppro) {
        this.withAppro = withAppro;
        return this;
    }

    public void setWithAppro(Boolean withAppro) {
        this.withAppro = withAppro;
    }

    public String getThemeColor() {
        return themeColor;
    }

    public Cashier themeColor(String themeColor) {
        this.themeColor = themeColor;
        return this;
    }

    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }

    public Boolean isIsActivated() {
        return isActivated;
    }

    public Cashier isActivated(Boolean isActivated) {
        this.isActivated = isActivated;
        return this;
    }

    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }

    public Long getCompanyID() {
        return companyID;
    }

    public Cashier companyID(Long companyID) {
        this.companyID = companyID;
        return this;
    }

    public void setCompanyID(Long companyID) {
        this.companyID = companyID;
    }

    public CashierLocation getCashierLocation() {
        return cashierLocation;
    }

    public Cashier cashierLocation(CashierLocation cashierLocation) {
        this.cashierLocation = cashierLocation;
        return this;
    }

    public void setCashierLocation(CashierLocation cashierLocation) {
        this.cashierLocation = cashierLocation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cashier)) {
            return false;
        }
        return id != null && id.equals(((Cashier) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cashier{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", withTicket='" + isWithTicket() + "'" +
            ", withTVA='" + isWithTVA() + "'" +
            ", withTax='" + isWithTax() + "'" +
            ", withAppro='" + isWithAppro() + "'" +
            ", themeColor='" + getThemeColor() + "'" +
            ", isActivated='" + isIsActivated() + "'" +
            ", companyID=" + getCompanyID() +
            "}";
    }
}
