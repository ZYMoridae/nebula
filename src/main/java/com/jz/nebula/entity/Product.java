package com.jz.nebula.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="product", schema="public")
public class Product implements Serializable{
	public Product() {}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9014984408862403126L;
	
	@Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;

  @OneToOne
  @JoinColumn(name = "vendor_id")   
  Vendor vendor;
	
  private double price;
  
  @Column(name="units_in_stock")
  private int unitsInStock;
  
  private String name;
  
  @JsonIgnore
  @Column(name="created_at")
  private Date createdAt;
  
  @JsonIgnore
  @Column(name="updated_at")
  private Date updatedAt;
  
  @OneToMany(mappedBy="product", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
  private Set<ProductMeta> productMetas;

	public Set<ProductMeta> getProductMetas() {
		return productMetas;
	}

	public void setProductMetas(Set<ProductMeta> productMetas) {
		this.productMetas = productMetas;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getUnitsInStock() {
		return unitsInStock;
	}

	public void setUnitsInStock(int unitsInStock) {
		this.unitsInStock = unitsInStock;
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
}
