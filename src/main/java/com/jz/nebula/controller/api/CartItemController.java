/*
 * Copyright (c) 2019. Nebula Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jz.nebula.controller.api;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

import com.jz.nebula.entity.Cart;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/api/cart-items")
@Api(value = "cartitem")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    @ApiOperation(value = "Get a cart item with an ID", response = CartItem.class)
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
    @ApiOperation(value = "Get a cart items in side cart with an ID", response = PagedResources.class)
    public @ResponseBody
    PagedResources<Resource<CartItem>> findByCartId(@PathVariable("id") long cartId, Pageable pageable, final UriComponentsBuilder uriBuilder,
                                                    final HttpServletResponse response, PagedResourcesAssembler<CartItem> assembler) {
        return cartItemService.findByCartId(cartId, pageable, assembler);
    }

    /**
     * @param cartItem
     * @return
     * @throws Exception
     */
    @PostMapping("")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    @ApiOperation(value = "Create a cart item", response = CartItem.class)
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
     * @param id
     * @param cartItem
     * @return
     * @throws Exception
     */
    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    @ApiOperation(value = "Update a cart item", response = CartItem.class)
    public @ResponseBody
    CartItem update(@PathVariable("id") long id, @RequestBody CartItem cartItem) throws Exception {
        cartItem.setId(id);
        return cartItemService.save(cartItem);
    }

    /**
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    @ApiOperation(value = "Delete a cart item", response = ResponseEntity.class)
    public @ResponseBody
    ResponseEntity<?> delete(@PathVariable("id") long id) {
        cartItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/{id}/towishlistitem")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    @ApiOperation(value = "Convert cart item to wish list item", response = ResponseEntity.class)
    public @ResponseBody
    ResponseEntity<?> toWishListItem(@PathVariable("id") long id) throws Exception {
        cartItemService.saveToWishList(id);
        return ResponseEntity.noContent().build();
    }
}
