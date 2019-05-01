package com.jz.nebula.dao;

import com.jz.nebula.entity.OrderStatus;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface OrderStatusRepository extends PagingAndSortingRepository<OrderStatus, Long> {
    Optional<OrderStatus> findByName(String name);
}
