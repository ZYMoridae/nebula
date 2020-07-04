package com.jz.nebula.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_roles", schema = "public")
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    Role role;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "user_id")
    private Long userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "role_id")
    private Long roleId;
}
