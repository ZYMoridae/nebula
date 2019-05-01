package com.jz.nebula.dao;

import com.jz.nebula.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Page<Product> findByNameContaining(String keyword, Pageable pageable);
}
