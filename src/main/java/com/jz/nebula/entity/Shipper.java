package com.jz.nebula.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="shipper", schema="public")
public class Shipper implements Serializable {
	
  /**
	 * 
	 */
	private static final long serialVersionUID = 3952309942343713378L;
	
	@Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  private String name;
  private String contact;
  
  
  public Shipper() {}

  public Shipper(String name) {
    this.name = name;
  }

  public Long getId() {
	return id;
  }

  public void setId(Long id) {
	this.id = id;
  }

  public String getName() {
	  return name;
  }

	public void setName(String name) {
		this.name = name;
	}
	
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Override
  public String toString() {
    return String.format(
    		"Customer[id=%d, name='%s']",
    id, name);
  }

}