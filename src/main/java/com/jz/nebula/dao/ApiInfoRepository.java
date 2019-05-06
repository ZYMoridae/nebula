package com.jz.nebula.dao;

import com.jz.nebula.entity.api.ApiInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ApiInfoRepository extends PagingAndSortingRepository<ApiInfo, Long> {
    Page<ApiInfo> findByApiCategoryId(long apiCategoryId, Pageable pageable);
}
