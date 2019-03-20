package com.jz.nebula.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;

@Entity
@Table(name="role", schema="public")
public class Role{
	
	public Role() {}
	
	public final static String ROLE_USER = "ROLE_USER";
	
	public final static String ROLE_VENDOR = "ROLE_VENDOR";
	
	public final static String ROLE_ADMIN = "ROLE_ADMIN";
	
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String code;
  
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}