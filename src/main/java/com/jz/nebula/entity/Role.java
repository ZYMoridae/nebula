package com.jz.nebula.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "role", schema = "public")
public class Role implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1174975278194660551L;

    public Role() {
    }

    public final static String ROLE_USER = "ROLE_USER";

    public final static String ROLE_VENDOR = "ROLE_VENDOR";

    public final static String ROLE_ADMIN = "ROLE_ADMIN";

    public final static String ADMIN = "ADMIN";

    public final static String[] DEFAULT_USER_GROUP = {"ROLE_ADMIN", "ROLE_USER", "ROLE_VENDOR"};

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