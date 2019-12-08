package com.jz.nebula.entity.sku;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jz.nebula.entity.product.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.*;

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
    @JoinColumn(name = "sku_code", referencedColumnName = "sku_code", updatable = false, insertable = false)
    @ApiModelProperty(notes = "Stock keeping unit attributes. Normally has more than one attribute.")
    @OrderBy("id ASC")
    private Set<SkuAttribute> skuAttributes;

    @Column(name = "created_user_id")
    @ApiModelProperty(notes = "The user created the stock keeping unit")
    private Long createdUserId;

    @JsonIgnore
    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @JsonIgnore
    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;

    @Override
    public String toString() {
        return "Sku{" +
                "id=" + id +
                ", productId=" + productId +
                ", skuCode='" + skuCode + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", skuAttributes=" + skuAttributes +
                ", createdUserId=" + createdUserId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
