package com.jz.nebula.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.jz.nebula.entity.LogisticsProvider;
import com.jz.nebula.entity.User;
import com.jz.nebula.entity.sku.Sku;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders", schema = "public")
public class Order implements Serializable {
    public static final String PREFIX = "ORD";
    /**
     *
     */
    private static final long serialVersionUID = 5165402274482950113L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "shipper_id")
    private Long shipperId;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "order_status_id")
    private Long orderStatusId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "shipper_id", insertable = false, updatable = false)
    private LogisticsProvider logisticsProvider;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private Set<OrderItem> orderItems;

    @OneToOne
    @JoinColumn(name = "order_status_id", updatable = false, insertable = false)
    private OrderStatus orderStatus;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private String paymentToken;

////    // TODO: One to one
////    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private OrderLogisticsInfo orderShippingInfo;
////
//    public OrderLogisticsInfo getOrderShippingInfo() {
//        return orderShippingInfo;
//    }
//
//    public void setOrderShippingInfo(OrderLogisticsInfo orderShippingInfo) {
//        this.orderShippingInfo = orderShippingInfo;
//    }

    @JsonIgnore
    public Long getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Long orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
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

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonIgnore
    public Long getShipperId() {
        return shipperId;
    }

    public void setShipperId(Long shipperId) {
        this.shipperId = shipperId;
    }

    public LogisticsProvider getLogisticsProvider() {
        return logisticsProvider;
    }

    public void setLogisticsProvider(LogisticsProvider logisticsProvider) {
        this.logisticsProvider = logisticsProvider;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", user=" + user +
                ", shipperId=" + shipperId +
                ", orderStatusId=" + orderStatusId +
                ", logisticsProvider=" + logisticsProvider +
                ", orderItems=" + orderItems +
                ", orderStatus=" + orderStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
