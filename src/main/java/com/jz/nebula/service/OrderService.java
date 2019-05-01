package com.jz.nebula.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jz.nebula.auth.IAuthenticationFacade;
//import com.jz.nebula.amqp.MessageProducer;
import com.jz.nebula.controller.OrderController;
import com.jz.nebula.dao.OrderRepository;
import com.jz.nebula.dao.OrderStatusRepository;
import com.jz.nebula.entity.Order;
import com.jz.nebula.entity.OrderStatus;
import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.User;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

//	@Autowired
//	private MessageProducer messageProducer;

    public PagedResources<Resource<Order>> findAll(Pageable pageable, PagedResourcesAssembler<Order> assembler) {
        Page<Order> page = orderRepository.findAll(pageable);
        PagedResources<Resource<Order>> resources = assembler.toResource(page,
                linkTo(OrderController.class).slash("/orders").withSelfRel());
        ;
        return resources;
    }

    /**
     * Find all user orders with pagination
     *
     * @param id
     * @param pageable
     * @param assembler
     * @return
     */
    public PagedResources<Resource<Order>> findByUserId(long id, Pageable pageable,
                                                        PagedResourcesAssembler<Order> assembler) {
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
        return order.getId() == null;
    }

    /**
     * Check current user ROLE
     *
     * @param user
     * @return
     */
    private boolean isUser(User user) {
        return user.getRoles().contains(Role.ROLE_USER);
    }

    /**
     * If current user is not ADMIN or VENDOR just replace the user id
     *
     * @param order
     * @return
     */
    private Order preProcessing(Order order) {
        Order safetyOrder = order;
        User user = this.authenticationFacade.getUser();
        if (isUser(user) && safetyOrder.getUserId() != null) {
            logger.info("User with id:[{}] put userId: [{}] in the request", user.getId(), safetyOrder.getUserId());
            safetyOrder.setUserId(user.getId());
        }
        return safetyOrder;
    }

    /**
     * When order created push notification to queue and process it
     *
     * @param order
     * @return
     */
    public Order save(Order order) {
        order = preProcessing(order);
        boolean isNew = this.isNewOrder(order);

        if (isNew) {
            order.setOrderStatusId((long) OrderStatus.StatusType.PENDING.value);
        }
        Order updatedOrder = orderRepository.save(order);
        return findById(updatedOrder.getId());
    }

    /**
     * Find order by id
     *
     * @param id
     * @return
     */
    public Order findById(long id) {
        return orderRepository.findById(id).get();
    }

    /**
     * Delete order by id
     *
     * @param id
     */
    public void delete(long id) {
        orderRepository.deleteById(id);
    }

    public Order getCurrentActivatedOrder() {
        Order order = null;

        Optional<OrderStatus> orderStatus = orderStatusRepository.findByName("pending");
        if (orderStatus.isPresent()) {
            List<Order> orders = orderRepository.findByUserIdAndOrderStatusId(authenticationFacade.getUserId(), orderStatus.get().getId());
            if (orders.size() == 1) {
                order = orders.get(0);
            }
        }

        return order;
    }
}
