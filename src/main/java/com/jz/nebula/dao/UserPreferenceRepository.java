package com.jz.nebula.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.UserPreference;

public interface UserPreferenceRepository extends PagingAndSortingRepository<UserPreference, Long> {
	List<UserPreference> findByUserPreferenceTypeId(Long id);
}
