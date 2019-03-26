package com.jz.nebula.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.OrderItem;

public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, Long> {

}
