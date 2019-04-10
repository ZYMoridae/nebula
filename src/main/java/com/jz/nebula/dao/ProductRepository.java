package com.jz.nebula.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
	Page<Product> findByNameContaining(String keyword, Pageable pageable);
}
