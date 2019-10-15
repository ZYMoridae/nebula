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

@Entity
@Table(name = "cart_item", schema = "public")
public class CartItem implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1485757059766369065L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "product_id")
    private Long productId;

    private int quantity;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "cart_id")
    private Long cartId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;

    @JsonIgnore
    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Convert to OrderItem
     *
     * @return
     */
    public OrderItem toOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(this.productId);
        orderItem.setQuantity(this.quantity);
        orderItem.setUnitPrice(this.product.getPrice());
        return orderItem;
    }

    /**
     * Convert to WishListItem
     *
     * @return
     */
    public WishListItem toWishListItem() {
        WishListItem wishListItem = new WishListItem();
        wishListItem.setProductId(this.productId);
        wishListItem.setQuantity(this.quantity);
        return wishListItem;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", cartId=" + cartId +
                ", product=" + product +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
