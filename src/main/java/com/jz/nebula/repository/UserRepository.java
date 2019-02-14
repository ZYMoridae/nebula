package com.jz.nebula.repository;

import org.springframework.data.repository.CrudRepository;

import com.jz.nebula.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{
	User findByUsername(String username);
}
