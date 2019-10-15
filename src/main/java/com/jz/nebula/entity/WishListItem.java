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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.jz.nebula.entity.product.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "wish_list_item", schema = "public")
public class WishListItem implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6319692679795924951L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "product_id")
    private Long productId;

    private int quantity;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "wish_list_id")
    private Long wishListId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;

    /**
     * Wish list item to cart item
     *
     * @return
     */
    public CartItem toCartItem() {
        CartItem cartItem = new CartItem();
        cartItem.setProductId(this.productId);
        cartItem.setQuantity(this.quantity);
        return cartItem;
    }

    @Override
    public String toString() {
        return "WishListItem{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", wishListId=" + wishListId +
                ", product=" + product +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
