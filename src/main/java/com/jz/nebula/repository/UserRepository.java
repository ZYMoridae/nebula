package com.jz.nebula.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jz.nebula.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
}
