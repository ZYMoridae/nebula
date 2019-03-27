package com.jz.nebula.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.Cart;

public interface CartRepository extends PagingAndSortingRepository<Cart, Long> {
	Cart findByUserId(long id);
}
