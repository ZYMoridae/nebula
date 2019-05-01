package com.jz.nebula.dao;

import com.jz.nebula.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    Optional<Role> findByCode(String code);
}
