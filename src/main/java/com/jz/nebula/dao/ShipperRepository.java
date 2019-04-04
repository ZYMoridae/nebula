package com.jz.nebula.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.Shipper;

public interface ShipperRepository extends PagingAndSortingRepository<Shipper, Long> {
	Shipper findByName(String name);
}