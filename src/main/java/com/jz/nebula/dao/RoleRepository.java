package com.jz.nebula.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
	Optional<Role> findByCode(String code);
}
