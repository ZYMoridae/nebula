package com.jz.nebula.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "role", schema = "public")
public class Role implements Serializable {

    public final static String ROLE_USER = "ROLE_USER";
    public final static String ROLE_VENDOR = "ROLE_VENDOR";
    public final static String ROLE_ADMIN = "ROLE_ADMIN";
    public final static String ROLE_TEACHER = "ROLE_TEACHER";
    public final static String ADMIN = "ADMIN";
    public final static String USER = "USER";
    public final static String VENDOR = "VENDOR";
    public final static String TEACHER = "TEACHER";
    public final static String[] DEFAULT_USER_GROUP = {"ROLE_ADMIN", "ROLE_USER", "ROLE_VENDOR"};
    /**
     *
     */
    private static final long serialVersionUID = 1174975278194660551L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    public Role() {
    }

    /**
     * The view role is used for JSON response render
     */
    public enum ViewRole {
        ROLE_USER,
        ROLE_ADMIN,
        ROLE_ANONYMOUS,
        ROLE_TEACHER,
        ROLE_VENDOR
    }
}