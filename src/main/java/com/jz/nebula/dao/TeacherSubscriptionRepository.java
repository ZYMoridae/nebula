package com.jz.nebula.dao;

import com.jz.nebula.entity.teacher.TeacherSubscription;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TeacherSubscriptionRepository extends PagingAndSortingRepository<TeacherSubscription, Long> {
    Optional<TeacherSubscription> findByTeacherIdAndUserId(long teacherId, long userId);

    int deleteByTeacherIdAndUserId(long teacherId, long userId);
}
