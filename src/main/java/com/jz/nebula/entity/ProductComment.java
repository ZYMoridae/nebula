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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "product_comment", schema = "public")
public class ProductComment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4155776716734201072L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String body;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "user_id")
	private Long usertId;
	
	@OneToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "product_id")
	private Long productId;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "parent_comment_id")
	private Long parentCommentId;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_comment_id",  updatable = false, insertable = false)
	private Set<ProductComment> childrenComments;
	
	@Column(name = "created_at", updatable = false, insertable = false)
	private Date createdAt;

	@Column(name = "updated_at", updatable = false, insertable = false)
	private Date updatedAt;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	@JsonIgnore
	public Long getUsertId() {
		return usertId;
	}

	public void setUsertId(Long usertId) {
		this.usertId = usertId;
	}
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	@JsonIgnore
	public Long getParentCommentId() {
		return parentCommentId;
	}

	public void setParentCommentId(Long parentCommentId) {
		this.parentCommentId = parentCommentId;
	}

	public Set<ProductComment> getChildrenComments() {
		return childrenComments;
	}

	public void setChildrenComments(Set<ProductComment> childrenComments) {
		this.childrenComments = childrenComments;
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
