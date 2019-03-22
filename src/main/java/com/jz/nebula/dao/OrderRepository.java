package com.jz.nebula.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.Order;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long>{

}
