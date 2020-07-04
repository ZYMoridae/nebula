package com.jz.nebula.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user", schema = "public")
public class Vendor implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2705040384638908993L;
    @Id
    private Long id;
    private String username;
    private String telephone;
    private String address1;
    private String address2;
    private String firstname;
    private String lastname;
    private String email;
}
