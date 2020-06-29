package com.jz.nebula.controller.api;

import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.WishList;
import com.jz.nebula.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/wishlists")
public class WishListController {
    @Autowired
    private WishListService wishListService;

    /**
     * @param id
     *
     * @return
     */
    @GetMapping(value = "/user/{id}")
    @RolesAllowed({Role.ROLE_ADMIN})
    public @ResponseBody
    WishList getWishList(@PathVariable("id") long id) {
        return wishListService.getWishList(id);
    }

    /**
     * @return
     */
    @GetMapping(value = "/my")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    WishList getMyWishList() {
        return wishListService.getMyWishList();
    }

//	@PostMapping(value = "/finalise")
//	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
//	public @ResponseBody Object finalizeCart() throws Exception {
//		return wishListService.cartToOrder();
//	}
}
