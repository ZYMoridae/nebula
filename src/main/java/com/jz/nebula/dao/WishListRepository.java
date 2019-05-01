package com.jz.nebula.dao;

import com.jz.nebula.entity.WishList;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WishListRepository extends PagingAndSortingRepository<WishList, Long> {
    WishList findByUserId(long id);
}
