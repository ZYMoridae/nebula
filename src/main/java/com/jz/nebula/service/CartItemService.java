package com.jz.nebula.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.Optional;

import com.jz.nebula.util.auth.AuthenticationFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jz.nebula.controller.api.CartItemController;
import com.jz.nebula.dao.CartItemRepository;
import com.jz.nebula.dao.CartRepository;
//import com.jz.nebula.dao.ProductRepository;
import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.entity.Cart;
import com.jz.nebula.entity.CartItem;
//import com.jz.nebula.entity.product.Product;
import com.jz.nebula.entity.User;
import com.jz.nebula.entity.WishListItem;
//import com.jz.nebula.component.exception.ProductStockException;
import com.jz.nebula.util.validator.CartItemValidator;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
public class CartItemService {
    private final Logger logger = LogManager.getLogger(CartItemService.class);

    private AuthenticationFacade authenticationFacade;

    private CartItemRepository cartItemRepository;

    private UserRepository userRepository;

    private CartRepository cartRepository;

    private WishListItemService wishListItemService;

    private CartItemValidator cartItemValidator;

    @Autowired
    public CartItemService(AuthenticationFacade authenticationFacade,
                           CartItemRepository cartItemRepository,
                           UserRepository userRepository,
                           CartRepository cartRepository,
                           WishListItemService wishListItemService,
                           CartItemValidator cartItemValidator) {
        this.authenticationFacade = authenticationFacade;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.wishListItemService = wishListItemService;
        this.cartItemValidator = cartItemValidator;
    }

    /**
     * Get current user's cart
     *
     * @return
     */
    private Cart getCart() {
        Optional<User> userOptional = userRepository.findByUsername(authenticationFacade.getAuthentication().getName());
        Cart cart = null;
        if (userOptional.isPresent()) {
            cart = cartRepository.findByUserId(userOptional.get().getId());
        }

        if (cart == null) {
            cart = new Cart(authenticationFacade.getUser());
            cart = cartRepository.save(cart);
        }
        return cart;
    }

    /**
     * Get item in shopping cart
     *
     * @param cartItem
     *
     * @return
     */
    private CartItem getItemInCart(CartItem cartItem) {
        Optional<CartItem> optional = cartItemRepository.findByCartIdAndProductId(cartItem.getCartId(),
                cartItem.getProduct().getId());
        if (optional.isPresent()) {
            logger.info("getItemInCart::cart item with id:[{}] was found", optional.get().getId());
        } else {
            logger.info("getItemInCart::cart item was not been found");
        }
        return optional.isPresent() ? optional.get() : null;
    }

    /**
     * Find cart items by cart id
     *
     * @param cartId
     * @param pageable
     * @param assembler
     *
     * @return
     */
    public PagedModel<EntityModel<CartItem>> findByCartId(long cartId, Pageable pageable,
                                                          PagedResourcesAssembler<CartItem> assembler) {
        Page<CartItem> page = cartItemRepository.findByCartId(cartId, pageable);
        PagedModel<EntityModel<CartItem>> resources = assembler.toModel(page,
                linkTo(CartItemController.class).slash("/cart-items").withSelfRel());
        ;
        return resources;
    }

    /**
     * Save cartItem into database
     *
     * @param cartItem
     *
     * @return
     *
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class})
    public synchronized CartItem save(CartItem cartItem) throws Exception {
        boolean isValid = cartItemValidator.validate(cartItem);
        CartItem updatedCartItem;
        if (!isValid) {
            return null;
        }
        Cart cart = getCart();

        cartItem.setCartId(cart.getId());

        CartItem existingCartItem = getItemInCart(cartItem);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItem.getQuantity());
            updatedCartItem = cartItemRepository.save(existingCartItem);
        } else {
            updatedCartItem = cartItemRepository.save(cartItem);
        }

        logger.info("saveCartItem::product with id:[{}] was added to cart", cartItem.getProduct().getId());

        return findById(updatedCartItem.getId());
    }

    /**
     * Get cart item by id
     *
     * @param id
     *
     * @return
     */
    public CartItem findById(long id) {
        return cartItemRepository.findById(id).get();
    }

    /**
     * Delete cart item by id
     *
     * @param id
     */
    public void delete(long id) {
        cartItemRepository.deleteById(id);
    }

    /**
     * Delete cart items
     *
     * @param cartItems
     */
    public void deleteAll(Iterable<CartItem> cartItems) {
        cartItemRepository.deleteAll(cartItems);
    }

    /**
     * Convert cart item to wish list item and save to wishlist
     *
     * @param cartItemId
     *
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class})
    public synchronized void saveToWishList(long cartItemId) throws Exception {
        CartItem cartItem = findById(cartItemId);
        WishListItem wishListItem = cartItem.toWishListItem();
        wishListItemService.save(wishListItem);
        logger.info("saveToWishList::wish list item with id:[{}] was saved", wishListItem.getId());

        delete(cartItem.getId());
        logger.info("saveToWishList::cart item with id:[{}] was deleted", cartItemId);
    }
}
