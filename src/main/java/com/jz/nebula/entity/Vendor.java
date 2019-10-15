package com.jz.nebula.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
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

    public Vendor() {
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
