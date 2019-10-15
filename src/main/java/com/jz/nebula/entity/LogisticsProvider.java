package com.jz.nebula.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "shipper", schema = "public")
public class LogisticsProvider implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3952309942343713378L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String contact;

    public LogisticsProvider() {
    }

    public LogisticsProvider(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LogisticsProvider{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}