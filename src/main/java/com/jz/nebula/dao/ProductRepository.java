package com.jz.nebula.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

}
