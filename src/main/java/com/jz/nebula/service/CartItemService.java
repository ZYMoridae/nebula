package com.jz.nebula.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jz.nebula.auth.IAuthenticationFacade;
import com.jz.nebula.dao.CartItemRepository;
import com.jz.nebula.dao.CartRepository;
import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.entity.Cart;
import com.jz.nebula.entity.CartItem;
import com.jz.nebula.entity.User;

@Service
@Component("cartItemService")
public class CartItemService {
	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	private Cart getCart() {
		User user = userRepository.findByUsername(authenticationFacade.getAuthentication().getName()).get();
		Cart cart = null;
		if (user != null) {
			cart = cartRepository.findByUserId(user.getId());
		}
		return cart;
	}

	public CartItem save(CartItem cartItem){
		Cart cart = getCart();
		if (cart == null) {
			cart = new Cart(authenticationFacade.getUser().getId());
			cart = cartRepository.save(cart);
		}
		cartItem.setCartId(cart.getId());
		CartItem updatedOrder = cartItemRepository.save(cartItem);
		return findById(updatedOrder.getId());
	}

	public CartItem findById(long id) {
		return cartItemRepository.findById(id).get();
	}
	
	public void delete(long id) {
		cartItemRepository.deleteById(id);
	}
}