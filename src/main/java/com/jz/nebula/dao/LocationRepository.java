package com.jz.nebula.dao;

import com.jz.nebula.entity.Location;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LocationRepository extends PagingAndSortingRepository<Location, Long> {

}
