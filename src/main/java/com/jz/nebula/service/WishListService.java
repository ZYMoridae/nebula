package com.jz.nebula.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jz.nebula.auth.IAuthenticationFacade;
import com.jz.nebula.dao.WishListRepository;
import com.jz.nebula.entity.WishList;

@Service
@Component("wishListService")
@Transactional
public class WishListService {
	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@Autowired
	private WishListRepository wishListRepository;

	public WishList getWishList(long id) {
		return wishListRepository.findByUserId(id);
	}

	public WishList getMyWishList() {
		return wishListRepository.findByUserId(authenticationFacade.getUser().getId());
	}
}
