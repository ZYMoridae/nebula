package com.jz.nebula.service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jz.nebula.auth.IAuthenticationFacade;
import com.jz.nebula.dao.CartItemRepository;
import com.jz.nebula.dao.CartRepository;
import com.jz.nebula.dao.ProductRepository;
import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.entity.Cart;
import com.jz.nebula.entity.CartItem;
import com.jz.nebula.entity.Product;
import com.jz.nebula.entity.User;
import com.jz.nebula.exception.ProductStockException;
import com.jz.nebula.validator.CartItemValidator;

@Service
@Component("cartItemService")
public class CartItemService {
	private final Logger logger = Logger.getLogger(CartItemService.class);
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
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
	
	private CartItem getItemInCart(CartItem cartItem) {
		Optional<CartItem> optional = cartItemRepository.findByCartIdAndProductId(cartItem.getCartId(), cartItem.getProductId());
		return optional.get();
	}
	
	private synchronized void updateStock(CartItem cartItem) throws ProductStockException {
		Optional<Product> optional = productRepository.findById(cartItem.getProductId()); 
		if(!optional.isEmpty()) {
			Product product = optional.get();
			AtomicInteger currentStock = new AtomicInteger(product.getUnitsInStock());
			currentStock.addAndGet(cartItem.getQuantity() * -1);
			if(currentStock.get() < 0) {
				throw new ProductStockException();
			}
			
			product.setUnitsInStock(currentStock.get());
			productRepository.save(product);
		}
	}
	
	public synchronized CartItem save(CartItem cartItem){
		boolean isValid = cartItemValidator.validate(cartItem);
		CartItem updatedOrder = null;
		if(!isValid) {
			return null;
		}
		Cart cart = getCart();
		if (cart == null) {
			cart = new Cart(authenticationFacade.getUser().getId());
			cart = cartRepository.save(cart);
		}
		
		cartItem.setCartId(cart.getId());
		
		CartItem existingCartItem = getItemInCart(cartItem);
		if(existingCartItem != null) {
			existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItem.getQuantity());
			updatedOrder = cartItemRepository.save(existingCartItem);
		}else {
			updatedOrder = cartItemRepository.save(cartItem);
		}
		
		try {
			this.updateStock(cartItem);
		} catch (ProductStockException e) {
			logger.error("product stock update error...");
			e.printStackTrace();
			return null;
		}
		
		return findById(updatedOrder.getId());
	}

	public CartItem findById(long id) {
		return cartItemRepository.findById(id).get();
	}
	
	public void delete(long id) {
		cartItemRepository.deleteById(id);
	}
}