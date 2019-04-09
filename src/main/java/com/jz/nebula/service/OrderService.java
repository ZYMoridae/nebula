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

import com.jz.nebula.amqp.MessageProducer;
import com.jz.nebula.controller.OrderController;
import com.jz.nebula.dao.OrderRepository;
import com.jz.nebula.entity.Order;

@Service
@Component("orderService")
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private MessageProducer messageProducer;

	public PagedResources<Resource<Order>> findAll(Pageable pageable, PagedResourcesAssembler<Order> assembler) {
		Page<Order> page = orderRepository.findAll(pageable);
		PagedResources<Resource<Order>> resources = assembler.toResource(page,
				linkTo(OrderController.class).slash("/orders").withSelfRel());
		;
		return resources;
	}
	
	/**
	 * Check the order is new order
	 * 
	 * @param order
	 * @return
	 */
	private boolean isNewOrder(Order order) {
		return order.getId() != null;
	}
	
	/**
	 * When order created push notification to quene and process it
	 * 
	 * @param order
	 * @return
	 */
	public Order save(Order order) {
		boolean isNew = this.isNewOrder(order);
		Order updatedOrder = orderRepository.save(order);
		if (isNew) {
			messageProducer.sendMessage("order." + updatedOrder.getId());
		}
		return findById(updatedOrder.getId());
	}

	public Order findById(long id) {
		return orderRepository.findById(id).get();
	}

	public void delete(long id) {
		orderRepository.deleteById(id);
	}
}
