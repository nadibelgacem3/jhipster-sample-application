package org.pikasoft.mooin.mooinbase.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * ProductMark entity.\n@author The Moo3in team.
 */
@ApiModel(description = "ProductMark entity.\n@author The Moo3in team.")
@Entity
@Table(name = "product_mark")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductMark implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "company_id")
    private Long companyID;

    @OneToMany(mappedBy = "productMark")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Product> products = new HashSet<>();

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

    public ProductMark name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ProductMark description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCompanyID() {
        return companyID;
    }

    public ProductMark companyID(Long companyID) {
        this.companyID = companyID;
        return this;
    }

    public void setCompanyID(Long companyID) {
        this.companyID = companyID;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public ProductMark products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public ProductMark addProduct(Product product) {
        this.products.add(product);
        product.setProductMark(this);
        return this;
    }

    public ProductMark removeProduct(Product product) {
        this.products.remove(product);
        product.setProductMark(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductMark)) {
            return false;
        }
        return id != null && id.equals(((ProductMark) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductMark{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", companyID=" + getCompanyID() +
            "}";
    }
}
