package com.jz.nebula.controller;

import com.jz.nebula.entity.CartItem;
import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.WishListItem;
import com.jz.nebula.service.WishListItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/wishlist-items")
public class WishListItemController {
    @Autowired
    private WishListItemService wishListItemService;

    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    WishListItem findById(@PathVariable("id") long id) {
        return wishListItemService.findById(id);
    }

    /**
     * This is designed for pagination on shopping cart page
     *
     * @param wishListId
     * @param pageable
     * @param uriBuilder
     * @param response
     * @param assembler
     * @return
     */
    @GetMapping("/wishlists/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    PagedResources<Resource<WishListItem>> findByWishListId(@PathVariable("id") long wishListId,
                                                            Pageable pageable, final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
                                                            PagedResourcesAssembler<WishListItem> assembler) {
        return wishListItemService.findByWishListId(wishListId, pageable, assembler);
    }

    @PostMapping("")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    WishListItem create(@RequestBody WishListItem wishListItem) throws Exception {
        WishListItem savedWishListItem = wishListItemService.save(wishListItem);
        if (savedWishListItem == null) {
            // TODO: Create exception
            throw new Exception();
        }
        return savedWishListItem;
    }

    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    WishListItem update(@PathVariable("id") long id, @RequestBody WishListItem wishListItem)
            throws Exception {
        wishListItem.setId(id);
        return wishListItemService.save(wishListItem);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> delete(@PathVariable("id") long id) {
        wishListItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/tocartitem")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    CartItem toCartItem(@PathVariable("id") long id, @RequestBody WishListItem wishListItem)
            throws Exception {
        wishListItem.setId(id);
        return wishListItemService.toCartItem(wishListItem);
    }
}
