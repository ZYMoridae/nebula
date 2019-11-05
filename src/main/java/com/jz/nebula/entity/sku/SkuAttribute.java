package com.jz.nebula.entity.sku;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "sku_attribute", schema = "public")
public class SkuAttribute implements Serializable, Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sku_code")
    private String skuCode;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "sku_attribute_category_id")
    private Long skuAttributeCategoryId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "sku_attribute_category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SkuAttributeCategory skuAttributeCategory;

    private String value;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;

    @Override
    public String toString() {
        return "SkuAttribute{" +
                "id=" + id +
                ", skuCode='" + skuCode + '\'' +
                ", skuAttributeCategoryId=" + skuAttributeCategoryId +
                ", skuAttributeCategory=" + skuAttributeCategory +
                ", value='" + value + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        SkuAttribute _o = (SkuAttribute) o;
        return this.id > _o.id ? 1 : 0;
    }
}
