package com.jz.nebula.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;

@Entity
@Builder
@Table(name="role", schema="public")
public class Role{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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