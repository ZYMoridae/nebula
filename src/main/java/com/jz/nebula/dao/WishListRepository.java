package com.jz.nebula.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.WishList;

public interface WishListRepository extends PagingAndSortingRepository<WishList, Long> {
	WishList findByUserId(long id);
}
