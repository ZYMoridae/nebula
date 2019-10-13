package com.jz.nebula.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jz.nebula.entity.Cart;
import com.jz.nebula.entity.CartItem;
import com.jz.nebula.entity.Role;
import com.jz.nebula.service.CartService;

@RestController
@RequestMapping("/carts")
@Api(value = "carts")
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * @param id
     * @return
     */
    @GetMapping(value = "/user/{id}")
    @RolesAllowed({Role.ROLE_ADMIN})
    @ApiOperation(value = "Get a cart with an ID", response = Cart.class)
    public @ResponseBody
    Cart getCart(@PathVariable("id") long id) {
        return cartService.getCart(id);
    }

    /**
     * @return
     */
    @GetMapping(value = "/my")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    @ApiOperation(value = "Get current user's cart", response = Cart.class)
    public @ResponseBody
    Cart getMyCart() {
        return cartService.getMyCart();
    }

    /**
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/finalise")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    @ApiOperation(value = "Finalise all cart items", response = Object.class)
    public @ResponseBody
    Object finalizeAllCartItems() throws Exception {
        return cartService.cartToOrder();
    }

    /**
     * @param cartItems
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/bulk")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    @ApiOperation(value = "Finalise cart items", response = Object.class)
    public @ResponseBody
    Object finalizeCartItems(@RequestBody List<CartItem> cartItems) throws Exception {
        return cartService.cartToOrder(cartItems);
    }

}
