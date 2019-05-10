//package com.jz.nebula.entity.api;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import org.hibernate.annotations.Type;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.sql.Date;
//
//@Entity
//@Table(name = "api_info_header", schema = "public")
//public class ApiInfoHeader implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//
//    @Column(name="value")
//    @Type(type="com.jz.nebula.hibernate.GenericArrayUserType")
//    private String[] value;
//
//    @Column(name = "created_at", updatable = false, insertable = false)
//    private Date createdAt;
//
//    @Column(name = "updated_at", updatable = false, insertable = false)
//    private Date updatedAt;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String[] getValue() {
//        return value;
//    }
//
//    public void setValue(String[] value) {
//        this.value = value;
//    }
//
//    @JsonIgnore
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    @JsonIgnore
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//}
