package com.jz.nebula.entity.sku;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name = "sku_attribute_category", schema = "public")
public class SkuAttributeCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @Column(name = "created_at", insertable = false)
    private Date createdAt;

    @JsonIgnore
    @Column(name = "updated_at", insertable = false)
    private Date updatedAt;
}
