package com.jz.nebula.dao;

import com.jz.nebula.entity.product.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ProductCategoryRepository extends PagingAndSortingRepository<ProductCategory, Long> {
    @Query("select c from ProductCategory c where lower(c.name) like %?1%")
    Page<ProductCategory> findByNameContaining(@Param("keyword") String keyword, Pageable pageable);
}
