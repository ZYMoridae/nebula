package com.jz.nebula.dao;

import com.jz.nebula.entity.OrderItem;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, Long> {

}
