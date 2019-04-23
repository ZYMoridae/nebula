package com.jz.nebula.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_preference_type", schema = "public")
public class UserPreferenceType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9213231954940880833L;

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
