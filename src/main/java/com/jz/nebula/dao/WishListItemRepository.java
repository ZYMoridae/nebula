package com.jz.nebula.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.WishListItem;

public interface WishListItemRepository extends PagingAndSortingRepository<WishListItem, Long> {
	public Optional<WishListItem> findByWishListIdAndProductId(long wishListId, long productId);

	Page<WishListItem> findByWishListId(long wishListId, Pageable pageable);
}
