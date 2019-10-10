package com.jz.nebula.dao;

import com.jz.nebula.entity.LogisticsProvider;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LogisticsProviderRepository extends PagingAndSortingRepository<LogisticsProvider, Long> {
    LogisticsProvider findByName(String name);
}