package com.jz.nebula.dao;

import com.jz.nebula.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    @Query("select c from Product c where lower(c.name) like %?1%")
    Page<Product> findByNameContaining(@Param("keyword") String keyword, Pageable pageable);

    List<Product> findByIdIn(List<Long> ids);

    Page<Product> findAllByOrderByIdAsc(Pageable pageable);
}
