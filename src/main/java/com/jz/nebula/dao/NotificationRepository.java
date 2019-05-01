package com.jz.nebula.dao;

import com.jz.nebula.entity.Notification;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long> {

}
