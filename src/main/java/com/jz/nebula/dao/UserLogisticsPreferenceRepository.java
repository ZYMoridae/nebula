package com.jz.nebula.dao;

import com.jz.nebula.entity.UserLogisticsPreference;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserLogisticsPreferenceRepository extends PagingAndSortingRepository<UserLogisticsPreference, Long> {
    List<UserLogisticsPreference> findByUserId(Long userId);
}
