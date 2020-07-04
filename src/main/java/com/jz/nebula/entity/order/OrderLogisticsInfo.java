package com.jz.nebula.entity.order;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "order_shipping_info", schema = "public")
@Data
public class OrderLogisticsInfo implements Serializable {
    private static final long serialVersionUID = -2708578610878910417L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address1;

    private String address2;

    private String firstname;

    private String lastname;

    private String email;

    @Column(name = "post_code")
    private String postCode;

    private String telephone;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;

    @Column(name = "orders_id")
    private Long ordersId;
}
