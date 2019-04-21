package com.jz.nebula.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.OrderStatus;

public interface OrderStatusRepository extends PagingAndSortingRepository<OrderStatus, Long> {
	Optional<OrderStatus> findByName(String name);
}
