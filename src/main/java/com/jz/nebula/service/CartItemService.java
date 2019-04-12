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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jz.nebula.auth.IAuthenticationFacade;
import com.jz.nebula.controller.CartItemController;
import com.jz.nebula.dao.CartItemRepository;
import com.jz.nebula.dao.CartRepository;
//import com.jz.nebula.dao.ProductRepository;
import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.entity.Cart;
import com.jz.nebula.entity.CartItem;
//import com.jz.nebula.entity.Product;
import com.jz.nebula.entity.User;
//import com.jz.nebula.exception.ProductStockException;
import com.jz.nebula.validator.CartItemValidator;

import org.springframework.transaction.annotation.Propagation;

@Service
@Component("cartItemService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
public class CartItemService {
	private final Logger logger = LogManager.getLogger(CartItemService.class);

	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

//	@Autowired
//	private ProductRepository productRepository;

	@Autowired
	private CartItemValidator cartItemValidator;

	private Cart getCart() {
		User user = userRepository.findByUsername(authenticationFacade.getAuthentication().getName()).get();
		Cart cart = null;
		if (user != null) {
			cart = cartRepository.findByUserId(user.getId());
		}
		return cart;
	}

	/**
	 * Get item in shopping cart
	 * 
	 * @param cartItem
	 * @return
	 */
	private CartItem getItemInCart(CartItem cartItem) {
		Optional<CartItem> optional = cartItemRepository.findByCartIdAndProductId(cartItem.getCartId(),
				cartItem.getProductId());
		return optional.isPresent() ? optional.get() : null;
	}
	
	/**
	 * Find cart items by cart id
	 * 
	 * @param cartId
	 * @param pageable
	 * @param assembler
	 * @return
	 */
	public PagedResources<Resource<CartItem>> findByCartId(long cartId, Pageable pageable, PagedResourcesAssembler<CartItem> assembler) {
		Page<CartItem> page = cartItemRepository.findByCartId(cartId, pageable);
		PagedResources<Resource<CartItem>> resources = assembler.toResource(page,
				linkTo(CartItemController.class).slash("/cart-items").withSelfRel());
		;
		return resources;
	}
	
//	private synchronized void updateStock(CartItem cartItem) throws ProductStockException {
//		Optional<Product> optional = productRepository.findById(cartItem.getProductId()); 
//		if(optional.isPresent()) {
//			Product product = optional.get();
//			AtomicInteger currentStock = new AtomicInteger(product.getUnitsInStock());
//			currentStock.addAndGet(cartItem.getQuantity() * -1);
//			if(currentStock.get() < 0) {
//				throw new ProductStockException();
//			}
//			
//			product.setUnitsInStock(currentStock.get());
//			productRepository.save(product);
//		}
//	}

	/**
	 * Save cartItem into database
	 * 
	 * @param cartItem
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = { Exception.class })
	public synchronized CartItem save(CartItem cartItem) throws Exception {
		boolean isValid = cartItemValidator.validate(cartItem);
		CartItem updatedCartItem = null;
		if (!isValid) {
			return null;
		}
		Cart cart = getCart();
		if (cart == null) {
			cart = new Cart(authenticationFacade.getUser().getId());
			cart = cartRepository.save(cart);
		}

		cartItem.setCartId(cart.getId());

		CartItem existingCartItem = getItemInCart(cartItem);
		if (existingCartItem != null) {
			existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItem.getQuantity());
			updatedCartItem = cartItemRepository.save(existingCartItem);
		} else {
			updatedCartItem = cartItemRepository.save(cartItem);
		}

//		try {
//			this.updateStock(cartItem);
//		} catch (ProductStockException e) {
//			logger.error("product stock update error...");
//			e.printStackTrace();
//			return null;
//		}
		logger.info("Product with id:[{}] has been added", cartItem.getProductId());

		return findById(updatedCartItem.getId());
	}

	public CartItem findById(long id) {
		return cartItemRepository.findById(id).get();
	}

	public void delete(long id) {
		cartItemRepository.deleteById(id);
	}
}