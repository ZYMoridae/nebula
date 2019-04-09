package com.jz.nebula.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.Notification;

public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long> {

}
