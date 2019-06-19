package com.jz.nebula.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_roles", schema = "public")
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    User user;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable=false, updatable=false)
    Role role;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "user_id")
    private Long userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "role_id")
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
