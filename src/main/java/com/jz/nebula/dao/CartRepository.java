package com.jz.nebula.dao;

import com.jz.nebula.entity.Cart;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CartRepository extends PagingAndSortingRepository<Cart, Long> {
    Cart findByUserId(long id);
}
