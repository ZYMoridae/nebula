package com.jz.nebula.entity.order;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.jz.nebula.entity.product.Product;
import com.jz.nebula.entity.sku.Sku;
import lombok.Data;

@Entity
@Table(name = "order_item", schema = "public")
@Data
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 3538947577595353135L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unit_price")
    private double unitPrice;

    private int quantity;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "sku_code")
    private String skuCode;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;

    public double getAmount() {
        return (double) this.quantity * this.product.getPrice();
    }
}
