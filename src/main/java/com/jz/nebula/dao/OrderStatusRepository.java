package com.jz.nebula.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.OrderStatus;

public interface OrderStatusRepository extends PagingAndSortingRepository<OrderStatus, Long>{

}
