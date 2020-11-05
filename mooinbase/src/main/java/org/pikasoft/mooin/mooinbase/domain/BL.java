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

import org.pikasoft.mooin.mooinbase.domain.enumeration.BLStatusEnum;

import org.pikasoft.mooin.mooinbase.domain.enumeration.BLTypeEnum;

/**
 * BL entity.\n@author The Moo3in team.
 */
@ApiModel(description = "BL entity.\n@author The Moo3in team.")
@Entity
@Table(name = "bl")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BL implements Serializable {

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
    private BLStatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private BLTypeEnum type;

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

    @OneToMany(mappedBy = "bl")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<BLItem> blItems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "bLS", allowSetters = true)
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

    public BL number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getReference() {
        return reference;
    }

    public BL reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BLStatusEnum getStatus() {
        return status;
    }

    public BL status(BLStatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(BLStatusEnum status) {
        this.status = status;
    }

    public BLTypeEnum getType() {
        return type;
    }

    public BL type(BLTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(BLTypeEnum type) {
        this.type = type;
    }

    public BigDecimal getTotalHT() {
        return totalHT;
    }

    public BL totalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
        return this;
    }

    public void setTotalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
    }

    public BigDecimal getTotalTVA() {
        return totalTVA;
    }

    public BL totalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
        return this;
    }

    public void setTotalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
    }

    public BigDecimal getTotaTTC() {
        return totaTTC;
    }

    public BL totaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
        return this;
    }

    public void setTotaTTC(BigDecimal totaTTC) {
        this.totaTTC = totaTTC;
    }

    public LocalDate getDate() {
        return date;
    }

    public BL date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public BL dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean isIsConverted() {
        return isConverted;
    }

    public BL isConverted(Boolean isConverted) {
        this.isConverted = isConverted;
        return this;
    }

    public void setIsConverted(Boolean isConverted) {
        this.isConverted = isConverted;
    }

    public Long getCompanyID() {
        return companyID;
    }

    public BL companyID(Long companyID) {
        this.companyID = companyID;
        return this;
    }

    public void setCompanyID(Long companyID) {
        this.companyID = companyID;
    }

    public Set<BLItem> getBlItems() {
        return blItems;
    }

    public BL blItems(Set<BLItem> bLItems) {
        this.blItems = bLItems;
        return this;
    }

    public BL addBlItem(BLItem bLItem) {
        this.blItems.add(bLItem);
        bLItem.setBl(this);
        return this;
    }

    public BL removeBlItem(BLItem bLItem) {
        this.blItems.remove(bLItem);
        bLItem.setBl(null);
        return this;
    }

    public void setBlItems(Set<BLItem> bLItems) {
        this.blItems = bLItems;
    }

    public Tiers getTiers() {
        return tiers;
    }

    public BL tiers(Tiers tiers) {
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
        if (!(o instanceof BL)) {
            return false;
        }
        return id != null && id.equals(((BL) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BL{" +
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
