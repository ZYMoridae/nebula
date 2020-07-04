package com.jz.nebula.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;
import com.jz.nebula.util.View;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "user", schema = "public")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {
    private static final long serialVersionUID = -7813183948065034220L;
    @JsonIgnore
    @OneToMany(targetEntity = UserRole.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    @OrderBy("id ASC")
    Set<UserRole> userRoles;
    //    @ElementCollection(fetch = FetchType.EAGER)
//    @Builder.Default
//    private List<String> roles = new ArrayList<>();
    @Transient
    List<Role> roles;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @JsonView(View.Admin.class)
    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @JsonView(View.Admin.class)
    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;
    private String telephone;
    private String address1;
    private String address2;
    private String firstname;
    private String lastname;
    private String gender;

    // FIXME:
    public boolean isAdmin() {
        return this.getUserRoles().stream().map(userRole -> userRole.getRole().getCode()).collect(Collectors.toList()).contains(Role.ADMIN);
    }

    @PreUpdate
    public void preUpdate() {
        java.util.Date utilDate = new java.util.Date();
        updatedAt = new java.sql.Date(utilDate.getTime());
    }
}
