package com.jz.nebula.controller;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jz.nebula.entity.CartItem;
import com.jz.nebula.entity.Role;
import com.jz.nebula.service.CartItemService;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    CartItem findById(@PathVariable("id") long id) {
        return cartItemService.findById(id);
    }

    /**
     * This is designed for pagination on shopping cart page
     *
     * @param cartId
     * @param pageable
     * @param uriBuilder
     * @param response
     * @param assembler
     * @return
     */
    @GetMapping("/carts/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    PagedResources<Resource<CartItem>> findByCartId(@PathVariable("id") long cartId, Pageable pageable, final UriComponentsBuilder uriBuilder,
                                                    final HttpServletResponse response, PagedResourcesAssembler<CartItem> assembler) {
        return cartItemService.findByCartId(cartId, pageable, assembler);
    }

    /**
     *
     * @param cartItem
     * @return
     * @throws Exception
     */
    @PostMapping("")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    CartItem create(@RequestBody CartItem cartItem) throws Exception {
        CartItem savedCartItem = cartItemService.save(cartItem);
        if (savedCartItem == null) {
            // TODO: Create exception
            throw new Exception();
        }
        return savedCartItem;
    }

    /**
     *
     * @param id
     * @param cartItem
     * @return
     * @throws Exception
     */
    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    CartItem update(@PathVariable("id") long id, @RequestBody CartItem cartItem) throws Exception {
        cartItem.setId(id);
        return cartItemService.save(cartItem);
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> delete(@PathVariable("id") long id) {
        cartItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/{id}/towishlistitem")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> toWishListItem(@PathVariable("id") long id) throws Exception {
        cartItemService.saveToWishList(id);
        return ResponseEntity.noContent().build();
    }
}
