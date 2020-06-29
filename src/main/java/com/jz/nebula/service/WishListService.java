package com.jz.nebula.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jz.nebula.util.auth.AuthenticationFacade;
import com.jz.nebula.dao.WishListRepository;
import com.jz.nebula.entity.WishList;

@Service
@Transactional
public class WishListService {
    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private WishListRepository wishListRepository;

    /**
     * Get wish list
     *
     * @param id
     *
     * @return
     */
    public WishList getWishList(long id) {
        return wishListRepository.findByUserId(id);
    }

    /**
     * Get current user wish list
     *
     * @return
     */
    public WishList getMyWishList() {
        return wishListRepository.findByUserId(authenticationFacade.getUser().getId());
    }
}
