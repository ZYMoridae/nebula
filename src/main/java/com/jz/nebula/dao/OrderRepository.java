package com.jz.nebula.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.Order;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
	Optional<Order> findByUserId(long userId);

	Optional<Order> findByUserIdAndOrderStatusId(long userId, long orderStatusId);
}
