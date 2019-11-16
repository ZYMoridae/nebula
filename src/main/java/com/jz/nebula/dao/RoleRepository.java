package com.jz.nebula.dao;

import com.jz.nebula.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    Optional<Role> findByCode(String code);

    @Query("select c from Role c where lower(c.code) like %?1%")
    Page<Role> findByCodeContaining(@Param("keyword") String keyword, Pageable pageable);

    Page<Role> findAllByOrderByIdAsc(Pageable pageable);
}
