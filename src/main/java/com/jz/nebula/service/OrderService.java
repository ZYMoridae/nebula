package com.jz.nebula.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jz.nebula.controller.OrderController;
import com.jz.nebula.dao.OrderRepository;
import com.jz.nebula.entity.Order;

@Service
@Component("orderService")
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;

	public PagedResources<Resource<Order>> findAll(Pageable pageable, PagedResourcesAssembler<Order> assembler) {
		Page<Order> page = orderRepository.findAll(pageable);
		PagedResources<Resource<Order>> resources = assembler.toResource(page,
				linkTo(OrderController.class).slash("/orders").withSelfRel());
		;
		return resources;
	}

	public Order save(Order order) {
		Order updatedOrder = orderRepository.save(order);
		return findById(updatedOrder.getId());
	}

	public Order findById(long id) {
		return orderRepository.findById(id).get();
	}

	public void delete(long id) {
		orderRepository.deleteById(id);
	}
}
