package com.jz.nebula.dao;

import com.jz.nebula.entity.UserPreference;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserPreferenceRepository extends PagingAndSortingRepository<UserPreference, Long> {
    List<UserPreference> findByUserPreferenceTypeId(Long id);
}
