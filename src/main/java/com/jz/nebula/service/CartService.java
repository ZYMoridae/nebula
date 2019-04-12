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

//	public PagedResources<Resource<Cart>> findAll(Pageable pageable, PagedResourcesAssembler<Order> assembler) {
//		Page<Order> page = orderRepository.findAll(pageable);
//		PagedResources<Resource<Order>> resources = assembler.toResource(page,
//				linkTo(OrderController.class).slash("/orders").withSelfRel());
//		;
//		return resources;
//	}

//	public Order save(Order order) {
//		Order updatedOrder = orderRepository.save(order);
//		return findById(updatedOrder.getId());
//	}

//	public Order findById(long id) {
//		return orderRepository.findById(id).get();
//	}
//
//	public void delete(long id) {
//		orderRepository.deleteById(id);
//	}

	public Cart getCart(long id) {
		return cartRepository.findByUserId(id);
	}

	public Cart getMyCart() {
		return cartRepository.findByUserId(authenticationFacade.getUser().getId());
	}
	
	private boolean isOneOrderActivated() {
		List<Order> orders = orderRepository.findByUserIdAndOrderStatusId(this.authenticationFacade.getUser().getId(), OrderStatus.StatusType.PENDING.value);
		if(orders.size() != 1) {
			return false;
		}
		return true;
	}
	
	@Transactional(rollbackFor = { Exception.class })
	public Order cartToOrder() throws Exception {
		if(!this.isOneOrderActivated()) {
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
