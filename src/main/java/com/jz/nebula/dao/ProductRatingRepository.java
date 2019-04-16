package com.jz.nebula.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.ProductRating;

public interface ProductRatingRepository extends PagingAndSortingRepository<ProductRating, Long>{
	List<ProductRating> findByProductId(long productId);
}
