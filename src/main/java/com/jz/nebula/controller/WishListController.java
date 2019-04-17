package com.jz.nebula.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.WishList;
import com.jz.nebula.service.WishListService;

@RestController
@RequestMapping("/wishlists")
public class WishListController {
	@Autowired
	private WishListService wishListService;

	@GetMapping(value = "/user/{id}")
	@RolesAllowed({ Role.ROLE_ADMIN })
	public @ResponseBody WishList getWishList(@PathVariable("id") long id) {
		return wishListService.getWishList(id);
	}

	@GetMapping(value = "/my")
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody WishList getMyWishList() {
		return wishListService.getMyWishList();
	}

//	@PostMapping(value = "/finalise")
//	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
//	public @ResponseBody Object finalizeCart() throws Exception {
//		return wishListService.cartToOrder();
//	}
}
