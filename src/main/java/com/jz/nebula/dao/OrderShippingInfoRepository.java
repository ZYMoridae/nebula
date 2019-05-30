package com.jz.nebula.dao;

import com.jz.nebula.entity.OrderShippingInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface OrderShippingInfoRepository extends PagingAndSortingRepository<OrderShippingInfo, Long> {
    Optional<OrderShippingInfo> findByOrdersId(Long ordersId);
}