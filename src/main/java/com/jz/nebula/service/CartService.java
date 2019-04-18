package com.jz.nebula.service;

import java.util.List;
import java.util.stream.Collectors;

//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PagedResourcesAssembler;
//import org.springframework.hateoas.PagedResources;
//import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jz.nebula.auth.IAuthenticationFacade;
//import com.jz.nebula.controller.OrderController;
import com.jz.nebula.dao.CartRepository;
import com.jz.nebula.dao.OrderRepository;
//import com.jz.nebula.dao.OrderRepository;
//import com.jz.nebula.entity.Order;
import com.jz.nebula.entity.Cart;
import com.jz.nebula.entity.Order;
import com.jz.nebula.entity.OrderStatus;

@Service
@Component("cartService")
@Transactional
public class CartService {
	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private OrderRepository orderRepository;

	/**
	 * Get cart by userId
	 * 
	 * @param userId
	 * @return
	 */
	public Cart getCart(long userId) {
		return cartRepository.findByUserId(userId);
	}
	
	/**
	 * Get current user cart
	 * 
	 * @return
	 */
	public Cart getMyCart() {
		return cartRepository.findByUserId(authenticationFacade.getUserId());
	}
	
	/**
	 * Check order status
	 * 
	 * @return
	 */
	private boolean isOneOrderActivated() {
		List<Order> orders = orderRepository.findByUserIdAndOrderStatusId(this.authenticationFacade.getUser().getId(),
				OrderStatus.StatusType.PENDING.value);
		return orders.size() == 1;
	}
	
	/**
	 * Convert cart to order
	 * 
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = { Exception.class })
	public Order cartToOrder() throws Exception {
		if (!this.isOneOrderActivated()) {
			throw new Exception();
		}

		Cart cart = getMyCart();
		Order order = new Order();
		order.setOrderItems(cart.getCartItems().stream().map(item -> item.toOrderItem()).collect(Collectors.toSet()));
		order.setUserId(this.authenticationFacade.getUser().getId());
		// TODO: Read shipper from user preference
		order.setShipperId((long) 1);

		order = orderRepository.save(order);
		return order;
	}
}
