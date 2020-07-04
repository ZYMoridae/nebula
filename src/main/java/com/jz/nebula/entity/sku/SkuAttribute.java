package com.jz.nebula.entity.sku;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name = "sku_attribute", schema = "public")
public class SkuAttribute implements Serializable, Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sku_code")
    private String skuCode;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "sku_attribute_category_id", referencedColumnName = "id")
    private SkuAttributeCategory skuAttributeCategory;

    private String value;

    @JsonIgnore
    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @JsonIgnore
    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;

    @Override
    public int compareTo(Object o) {
        SkuAttribute _o = (SkuAttribute) o;
        return this.id > _o.id ? 1 : 0;
    }
}
