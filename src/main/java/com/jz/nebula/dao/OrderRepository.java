package com.jz.nebula.dao;

import com.jz.nebula.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    Page<Order> findByUserId(long userId, Pageable pageable);

    List<Order> findByUserIdAndOrderStatusId(long userId, long orderStatusId);

    void deleteByIdIn(List<Long> ids);
}
