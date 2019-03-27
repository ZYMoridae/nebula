package com.jz.nebula.service;

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

import com.jz.nebula.auth.IAuthenticationFacade;
//import com.jz.nebula.controller.OrderController;
import com.jz.nebula.dao.CartRepository;
//import com.jz.nebula.dao.OrderRepository;
//import com.jz.nebula.entity.Order;
import com.jz.nebula.entity.Cart;

@Service
@Component("cartService")
public class CartService {
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	@Autowired
	private CartRepository cartRepository;
	
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
}

