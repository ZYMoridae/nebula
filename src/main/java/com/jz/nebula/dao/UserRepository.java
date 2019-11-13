package com.jz.nebula.dao;

import com.jz.nebula.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Query("select c from User c where lower(c.username) like %?1%")
    Page<User> findByNameContaining(@Param("keyword") String keyword, Pageable pageable);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Page<User> findAllByOrderByIdAsc(Pageable pageable);
}
