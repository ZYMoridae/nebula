package com.jz.nebula.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.Shipper;

public interface ShipperRepository extends PagingAndSortingRepository<Shipper, Long> {
	List<Shipper> findByName(String name);
}