package com.jz.nebula.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "location", schema = "public")
public class Location implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3320421556053429619L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "state_code")
    private String stateCode;

    private String name;

    @Column(name = "postcode")
    private String postCode;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", countryCode='" + countryCode + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", name='" + name + '\'' +
                ", postCode='" + postCode + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
