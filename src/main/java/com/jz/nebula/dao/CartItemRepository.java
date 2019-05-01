package com.jz.nebula.dao;

import com.jz.nebula.entity.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CartItemRepository extends PagingAndSortingRepository<CartItem, Long> {
    public Optional<CartItem> findByCartIdAndProductId(long cartId, long productId);

    Page<CartItem> findByCartId(long cartId, Pageable pageable);
}
