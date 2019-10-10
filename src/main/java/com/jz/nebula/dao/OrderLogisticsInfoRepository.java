package com.jz.nebula.dao;

import com.jz.nebula.entity.OrderLogisticsInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface OrderLogisticsInfoRepository extends PagingAndSortingRepository<OrderLogisticsInfo, Long> {
    Optional<OrderLogisticsInfo> findByOrdersId(Long ordersId);
}