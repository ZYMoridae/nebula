package com.jz.nebula.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jz.nebula.auth.IAuthenticationFacade;
import com.jz.nebula.controller.api.WishListItemController;
import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.dao.WishListItemRepository;
import com.jz.nebula.dao.WishListRepository;
import com.jz.nebula.entity.CartItem;
import com.jz.nebula.entity.User;
import com.jz.nebula.entity.WishList;
import com.jz.nebula.entity.WishListItem;
import com.jz.nebula.validator.WishListItemValidator;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
public class WishListItemService {
    private final Logger logger = LogManager.getLogger(WishListItemService.class);

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private WishListItemRepository wishListItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WishListItemValidator wishListItemValidator;

    @Autowired
    private CartItemService cartItemService;

    /**
     * Get authenticated user
     *
     * @return
     */
    private User getAuthenticatedUser() {
        return authenticationFacade.getUser();
    }

    /**
     * Get wish list
     *
     * @return
     */
    private WishList getWishList() {
        User user = getAuthenticatedUser();
        WishList wishList = null;
        if (user != null) {
            wishList = wishListRepository.findByUserId(user.getId());
        }
        if (wishList == null) {
            wishList = new WishList(user.getId());
            wishList = wishListRepository.save(wishList);
        }
        return wishList;
    }

    /**
     * Get item in wish list
     *
     * @param wishListItem
     * @return
     */
    private WishListItem getItemInWishList(WishListItem wishListItem) {
        Optional<WishListItem> optional = wishListItemRepository
                .findByWishListIdAndProductId(wishListItem.getWishListId(), wishListItem.getProductId());
        return optional.isPresent() ? optional.get() : null;
    }

    /**
     * Find wish list items by wish list id
     *
     * @param wishListId Wish list Id
     * @param pageable   Pageable object
     * @param assembler  PagedResourcesAssembler object
     * @return PagedResources object
     */
    public PagedResources<Resource<WishListItem>> findByWishListId(long wishListId, Pageable pageable,
                                                                   PagedResourcesAssembler<WishListItem> assembler) {
        Page<WishListItem> page = wishListItemRepository.findByWishListId(wishListId, pageable);
        PagedResources<Resource<WishListItem>> resources = assembler.toResource(page,
                linkTo(WishListItemController.class).slash("/wishlist-items").withSelfRel());
        ;
        return resources;
    }

    /**
     * Save wish list item
     *
     * @param wishListItem WishListItem object
     * @return WishListItem object
     * @throws Exception Exception class
     */
    @Transactional(rollbackFor = {Exception.class})
    public synchronized WishListItem save(WishListItem wishListItem) throws Exception {
        boolean isValid = wishListItemValidator.validate(wishListItem);
        WishListItem updatedWishListItem;
        if (!isValid) {
            return null;
        }
        WishList wishList = getWishList();

        wishListItem.setWishListId(wishList.getId());

        WishListItem existingWishListItem = getItemInWishList(wishListItem);
        if (existingWishListItem != null) {
            existingWishListItem.setQuantity(existingWishListItem.getQuantity() + wishListItem.getQuantity());
            updatedWishListItem = wishListItemRepository.save(existingWishListItem);
        } else {
            updatedWishListItem = wishListItemRepository.save(wishListItem);
        }

        logger.info("Product with id:[{}] has been added", wishListItem.getProductId());

        return findById(updatedWishListItem.getId());
    }

    /**
     * Find wish list item by id
     *
     * @param id Wish list item id
     * @return WishListItem object
     */
    public WishListItem findById(long id) {
        return wishListItemRepository.findById(id).get();
    }

    /**
     * Delete wish list item by id
     *
     * @param id Wish list item id
     */
    public void delete(long id) {
        wishListItemRepository.deleteById(id);
    }

    /**
     * Convert wish list item to cart item
     *
     * @param wishListItem WishListItem object
     * @return CartItem object
     * @throws Exception Exception class
     */
    @Transactional(rollbackFor = {Exception.class})
    public CartItem toCartItem(WishListItem wishListItem) throws Exception {
        CartItem cartItem = wishListItem.toCartItem();
        cartItemService.save(cartItem);
        delete(wishListItem.getId());

        return cartItemService.findById(cartItem.getId());
    }
}
