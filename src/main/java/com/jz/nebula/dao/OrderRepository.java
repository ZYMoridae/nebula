package com.jz.nebula.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.Order;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
	Page<Order> findByUserId(long userId, Pageable pageable);

	List<Order> findByUserIdAndOrderStatusId(long userId, long orderStatusId);
}
