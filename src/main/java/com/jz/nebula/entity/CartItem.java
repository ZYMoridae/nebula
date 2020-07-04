package com.jz.nebula.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.jz.nebula.entity.order.OrderItem;
import com.jz.nebula.entity.product.Product;
import lombok.Data;

@Entity
@Table(name = "cart_item", schema = "public")
@Data
public class CartItem implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1485757059766369065L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @Column(name = "sku_code")
    private String skuCode;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "cart_id")
    private Long cartId;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;

    /**
     * Convert to OrderItem
     *
     * @return
     */
    public OrderItem toOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        orderItem.setUnitPrice(product.getPrice());
        return orderItem;
    }

    /**
     * Convert to WishListItem
     *
     * @return
     */
    public WishListItem toWishListItem() {
        WishListItem wishListItem = new WishListItem();
        wishListItem.setProduct(product);
        wishListItem.setQuantity(quantity);
        return wishListItem;
    }
}
