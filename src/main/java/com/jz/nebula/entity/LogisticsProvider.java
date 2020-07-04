package com.jz.nebula.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "shipper", schema = "public")
@NoArgsConstructor
public class LogisticsProvider implements Serializable {

    private static final long serialVersionUID = 3952309942343713378L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String contact;

    public LogisticsProvider(String name) {
        this.name = name;
    }

}
