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
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "product", schema = "public")
@Data
public class Product implements Serializable {
    private static final long serialVersionUID = -9014984408862403126L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    private double price;

    @Column(name = "units_in_stock")
    private int unitsInStock;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Set<ProductMeta> productMetas;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private ProductCategory productCategory;

    private String description;

    /**
     * NOTE: Add fetch mode subselect to skus
     * https://stackoverflow.com/questions/5737747/entity-manager-returns-duplicate-copies-of-a-onetomany-related-entity
     */
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @OrderBy("id ASC")
    private Set<Sku> skus;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;
}
