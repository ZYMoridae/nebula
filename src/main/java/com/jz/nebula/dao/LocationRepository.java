package com.jz.nebula.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.Location;

public interface LocationRepository extends PagingAndSortingRepository<Location, Long> {

}
