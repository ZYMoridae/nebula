package com.jz.nebula.dao;

import com.jz.nebula.entity.UserShippingPreference;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserShippingPreferenceRepository extends PagingAndSortingRepository<UserShippingPreference, Long> {
    List<UserShippingPreference> findByUserId(Long userId);
}
