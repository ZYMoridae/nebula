package com.jz.nebula.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "user_preference", schema = "public")
public class UserPreference implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8218351166964324107L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "user_id")
	private Long userId;

	private String address1;

	private String address2;

	@Column(name = "postCode")
	private String postCode;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_preference_type_id")
	UserPreferenceType userPreferenceType;

	@Column(name = "user_preference_type_id", insertable = false, updatable = false)
	Long userPreferenceTypeId;

	public UserPreferenceType getUserPreferenceType() {
		return userPreferenceType;
	}

	public void setUserPreferenceType(UserPreferenceType userPreferenceType) {
		this.userPreferenceType = userPreferenceType;
	}

	public Long getUserPreferenceTypeId() {
		return userPreferenceTypeId;
	}

	public void setUserPreferenceTypeId(Long userPreferenceTypeId) {
		this.userPreferenceTypeId = userPreferenceTypeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
}
