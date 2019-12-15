package com.jz.nebula.dao;

import com.jz.nebula.entity.order.OrderItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, Long> {
    @Query("select SUM(o.unitPrice * o.quantity) as total from OrderItem o")
    Double getTotalTransaction();
}
