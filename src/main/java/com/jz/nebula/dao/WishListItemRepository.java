package com.jz.nebula.dao;

import com.jz.nebula.entity.WishListItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface WishListItemRepository extends PagingAndSortingRepository<WishListItem, Long> {
    Optional<WishListItem> findByWishListIdAndProductId(long wishListId, long productId);

    Page<WishListItem> findByWishListId(long wishListId, Pageable pageable);
}
