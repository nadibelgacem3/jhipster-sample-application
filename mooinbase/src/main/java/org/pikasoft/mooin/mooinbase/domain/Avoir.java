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
import java.util.HashSet;
import java.util.Set;

import org.pikasoft.mooin.mooinbase.domain.enumeration.AvoirStatusEnum;

import org.pikasoft.mooin.mooinbase.domain.enumeration.AvoirTypeEnum;

/**
 * Avoir entity.\n@author The Moo3in team.
 */
@ApiModel(description = "Avoir entity.\n@author The Moo3in team.")
@Entity
@Table(name = "avoir")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Avoir implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "reference")
    private String reference;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AvoirStatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AvoirTypeEnum type;

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

    @Column(name = "is_converted")
    private Boolean isConverted;

    @Column(name = "company_id")
    private Long companyID;

    @OneToMany(mappedBy = "avoir")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AvoirItem> avoirItems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "avoirs", allowSetters = true)
    private Tiers tiers;

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

    public Avoir number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getReference() {
        return reference;
    }

    public Avoir reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public AvoirStatusEnum getStatus() {
        return status;
    }

    public Avoir status(AvoirStatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(AvoirStatusEnum status) {
        this.status = status;
    }

    public AvoirTypeEnum getType() {
        return type;
    }

    public Avoir type(AvoirTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(AvoirTypeEnum type) {
        this.type = type;
    }

    public BigDecimal getTotalHT() {
        return totalHT;
    }

    public Avoir totalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
        return this;
    }

    public void setTotalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
    }

    public BigDecimal getTotalTVA() {
        return totalTVA;
    }

    public Avoir totalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
        return this;
    }

    public void setTotalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
    }

    public BigDecimal getTotaTTC() {
        return totaTTC;
    }

    public Avoir totaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
        return this;
    }

    public void setTotaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
    }

    public LocalDate getDate() {
        return date;
    }

    public Avoir date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Avoir dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean isIsConverted() {
        return isConverted;
    }

    public Avoir isConverted(Boolean isConverted) {
        this.isConverted = isConverted;
        return this;
    }

    public void setIsConverted(Boolean isConverted) {
        this.isConverted = isConverted;
    }

    public Long getCompanyID() {
        return companyID;
    }

    public Avoir companyID(Long companyID) {
        this.companyID = companyID;
        return this;
    }

    public void setCompanyID(Long companyID) {
        this.companyID = companyID;
    }

    public Set<AvoirItem> getAvoirItems() {
        return avoirItems;
    }

    public Avoir avoirItems(Set<AvoirItem> avoirItems) {
        this.avoirItems = avoirItems;
        return this;
    }

    public Avoir addAvoirItem(AvoirItem avoirItem) {
        this.avoirItems.add(avoirItem);
        avoirItem.setAvoir(this);
        return this;
    }

    public Avoir removeAvoirItem(AvoirItem avoirItem) {
        this.avoirItems.remove(avoirItem);
        avoirItem.setAvoir(null);
        return this;
    }

    public void setAvoirItems(Set<AvoirItem> avoirItems) {
        this.avoirItems = avoirItems;
    }

    public Tiers getTiers() {
        return tiers;
    }

    public Avoir tiers(Tiers tiers) {
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
        if (!(o instanceof Avoir)) {
            return false;
        }
        return id != null && id.equals(((Avoir) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Avoir{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", reference='" + getReference() + "'" +
            ", status='" + getStatus() + "'" +
            ", type='" + getType() + "'" +
            ", totalHT=" + getTotalHT() +
            ", totalTVA=" + getTotalTVA() +
            ", totaTTC=" + getTotaTTC() +
            ", date='" + getDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", isConverted='" + isIsConverted() + "'" +
            ", companyID=" + getCompanyID() +
            "}";
    }
}
