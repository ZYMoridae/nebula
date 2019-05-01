package com.jz.nebula.dao;

import com.jz.nebula.entity.Shipper;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShipperRepository extends PagingAndSortingRepository<Shipper, Long> {
    Shipper findByName(String name);
}