package com.jz.nebula.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "user_roles", schema = "public")
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    Role role;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "user_id")
    private Long userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "role_id")
    private Long roleId;

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", user=" + user +
                ", role=" + role +
                ", userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}
