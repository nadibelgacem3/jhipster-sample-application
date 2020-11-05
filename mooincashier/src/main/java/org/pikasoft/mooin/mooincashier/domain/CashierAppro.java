package org.pikasoft.mooin.mooincashier.domain;

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

import org.pikasoft.mooin.mooincashier.domain.enumeration.CashierApproStatusEnum;

/**
 * Cashierappro entity.\n@author The Moo3in team.
 */
@ApiModel(description = "Cashierappro entity.\n@author The Moo3in team.")
@Entity
@Table(name = "cashier_appro")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CashierAppro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "number")
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CashierApproStatusEnum status;

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

    @Column(name = "is_converted")
    private Boolean isConverted;

    @Column(name = "with_tva")
    private Boolean withTVA;

    @Column(name = "with_tax")
    private Boolean withTax;

    @OneToMany(mappedBy = "cashierAppro")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CashierApproItem> cashierApproItems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "cashierAppros", allowSetters = true)
    private Cashier cashier;

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

    public CashierAppro number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public CashierApproStatusEnum getStatus() {
        return status;
    }

    public CashierAppro status(CashierApproStatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(CashierApproStatusEnum status) {
        this.status = status;
    }

    public BigDecimal getTotalHT() {
        return totalHT;
    }

    public CashierAppro totalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
        return this;
    }

    public void setTotalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
    }

    public BigDecimal getTotalTVA() {
        return totalTVA;
    }

    public CashierAppro totalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
        return this;
    }

    public void setTotalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
    }

    public BigDecimal getTotaTTC() {
        return totaTTC;
    }

    public CashierAppro totaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
        return this;
    }

    public void setTotaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
    }

    public LocalDate getDate() {
        return date;
    }

    public CashierAppro date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean isIsConverted() {
        return isConverted;
    }

    public CashierAppro isConverted(Boolean isConverted) {
        this.isConverted = isConverted;
        return this;
    }

    public void setIsConverted(Boolean isConverted) {
        this.isConverted = isConverted;
    }

    public Boolean isWithTVA() {
        return withTVA;
    }

    public CashierAppro withTVA(Boolean withTVA) {
        this.withTVA = withTVA;
        return this;
    }

    public void setWithTVA(Boolean withTVA) {
        this.withTVA = withTVA;
    }

    public Boolean isWithTax() {
        return withTax;
    }

    public CashierAppro withTax(Boolean withTax) {
        this.withTax = withTax;
        return this;
    }

    public void setWithTax(Boolean withTax) {
        this.withTax = withTax;
    }

    public Set<CashierApproItem> getCashierApproItems() {
        return cashierApproItems;
    }

    public CashierAppro cashierApproItems(Set<CashierApproItem> cashierApproItems) {
        this.cashierApproItems = cashierApproItems;
        return this;
    }

    public CashierAppro addCashierApproItem(CashierApproItem cashierApproItem) {
        this.cashierApproItems.add(cashierApproItem);
        cashierApproItem.setCashierAppro(this);
        return this;
    }

    public CashierAppro removeCashierApproItem(CashierApproItem cashierApproItem) {
        this.cashierApproItems.remove(cashierApproItem);
        cashierApproItem.setCashierAppro(null);
        return this;
    }

    public void setCashierApproItems(Set<CashierApproItem> cashierApproItems) {
        this.cashierApproItems = cashierApproItems;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public CashierAppro cashier(Cashier cashier) {
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
        if (!(o instanceof CashierAppro)) {
            return false;
        }
        return id != null && id.equals(((CashierAppro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CashierAppro{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", status='" + getStatus() + "'" +
            ", totalHT=" + getTotalHT() +
            ", totalTVA=" + getTotalTVA() +
            ", totaTTC=" + getTotaTTC() +
            ", date='" + getDate() + "'" +
            ", isConverted='" + isIsConverted() + "'" +
            ", withTVA='" + isWithTVA() + "'" +
            ", withTax='" + isWithTax() + "'" +
            "}";
    }
}
