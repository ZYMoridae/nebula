package com.jz.nebula.entity.product;

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

@Data
@Entity
@Table(name = "product_category", schema = "public")
public class ProductCategory implements Serializable {
    private static final long serialVersionUID = -7576563878222934393L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @JsonIgnore
    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;
}
