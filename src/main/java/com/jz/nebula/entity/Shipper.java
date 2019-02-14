package com.jz.nebula.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Shipper {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;

    public Shipper() {}

    public Shipper(String name) {
        this.name = name;
    }
    
    public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
    public String toString() {
        return String.format(
                "Customer[id=%d, name='%s']",
                id, name);
    }

}