package com.jz.nebula.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jz.nebula.entity.Shipper;

public interface ShipperRepository extends CrudRepository<Shipper, Long> {

    List<Shipper> findByName(String name);
}