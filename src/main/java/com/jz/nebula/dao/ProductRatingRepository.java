package com.jz.nebula.dao;

import com.jz.nebula.entity.product.ProductRating;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRatingRepository extends PagingAndSortingRepository<ProductRating, Long> {
    List<ProductRating> findByProductId(long productId);
}
