package com.jz.nebula.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.CartItem;

public interface CartItemRepository extends PagingAndSortingRepository<CartItem, Long> {
	public Optional<CartItem> findByCartIdAndProductId(long cartId, long productId);
}
