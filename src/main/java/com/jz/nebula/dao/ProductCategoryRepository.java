package com.jz.nebula.dao;

import com.jz.nebula.entity.product.ProductCategory;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductCategoryRepository extends PagingAndSortingRepository<ProductCategory, Long> {

}
