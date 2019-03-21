package com.jz.nebula.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.ProductCategory;

public interface ProductCategoryRepository extends PagingAndSortingRepository<ProductCategory, Long>{

}
