package com.jz.nebula.dao.sku;

import com.jz.nebula.entity.sku.SkuAttributeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface SkuAttributeCategoryRepository extends PagingAndSortingRepository<SkuAttributeCategory, Long> {
    @Query("select c from SkuAttributeCategory c where lower(c.name) like %?1%")
    Page<SkuAttributeCategory> findByNameContaining(@Param("keyword") String keyword, Pageable pageable);
}
