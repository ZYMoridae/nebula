package com.jz.nebula.entity.product;

import java.io.Serializable;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.jz.nebula.entity.Vendor;
import com.jz.nebula.entity.sku.Sku;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "product", schema = "public")
public class Product implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -9014984408862403126L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "vendor_id")
    private Long vendorId;

    @OneToOne
    @JoinColumn(name = "vendor_id", insertable = false, updatable = false)
    private Vendor vendor;

    private double price;

    @Column(name = "units_in_stock")
    private int unitsInStock;

    private String name;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Set<ProductMeta> productMetas;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private ProductCategory productCategory;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "category_id")
    private Long categoryId;

    private String description;

    /**
     * NOTE: Add fetch mode subselect to skus
     * https://stackoverflow.com/questions/5737747/entity-manager-returns-duplicate-copies-of-a-onetomany-related-entity
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @OrderBy("id ASC")
//    @Fetch(FetchMode.SUBSELECT)
    private Set<Sku> skus;

    public Product() {
    }

    public Set<Sku> getSkus() {
        return this.skus;
    }

    public void setSkus(Set<Sku> skus) {
        this.skus = skus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Set<ProductMeta> getProductMetas() {
        return productMetas;
    }

    public void setProductMetas(Set<ProductMeta> productMetas) {
        this.productMetas = productMetas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", vendorId=" + vendorId +
                ", vendor=" + vendor +
                ", price=" + price +
                ", unitsInStock=" + unitsInStock +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", productMetas=" + productMetas +
                ", productCategory=" + productCategory +
                ", categoryId=" + categoryId +
                ", description='" + description + '\'' +
                '}';
    }
}
