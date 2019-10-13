package com.jz.nebula.entity.sku;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sku", schema = "public")
public class Sku implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "sku_code")
    @ApiModelProperty(notes = "Stock keeping unit code")
    private String skuCode;

    private Double price;

    private int stock;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "sku_code", nullable = false, updatable = false, insertable = false)
    @ApiModelProperty(notes = "Stock keeping unit attributes. Normally has more than one attribute.")
    private Set<SkuAttribute> skuAttributes;

    @Column(name = "created_user_id")
    @ApiModelProperty(notes = "The user created the stock keeping unit")
    private Long createdUserId;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;
}
