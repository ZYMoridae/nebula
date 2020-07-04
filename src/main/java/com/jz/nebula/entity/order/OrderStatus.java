package com.jz.nebula.entity.order;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "order_status", schema = "public")
public class OrderStatus implements Serializable {
    private static final long serialVersionUID = -6849443038191417463L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonIgnore
    @Column(name = "created_at")
    private Date createdAt;
    @JsonIgnore
    @Column(name = "updated_at")
    private Date updatedAt;

    public enum StatusType {
        PENDING(1), PAID(2), REFUND(3);

        public final int value;

        private StatusType(int value) {
            this.value = value;
        }
    }
}
