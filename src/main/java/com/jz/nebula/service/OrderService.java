package com.jz.nebula.service;

import com.jz.nebula.auth.IAuthenticationFacade;
import com.jz.nebula.controller.api.OrderController;
import com.jz.nebula.dao.OrderLogisticsInfoRepository;
import com.jz.nebula.dao.OrderRepository;
import com.jz.nebula.dao.OrderStatusRepository;
import com.jz.nebula.dao.sku.SkuRepository;
import com.jz.nebula.entity.*;
import com.jz.nebula.entity.order.Order;
import com.jz.nebula.entity.order.OrderItem;
import com.jz.nebula.entity.order.OrderLogisticsInfo;
import com.jz.nebula.entity.order.OrderStatus;
import com.jz.nebula.entity.product.Product;
import com.jz.nebula.entity.sku.Sku;
import com.jz.nebula.exception.MultipleActivatedOrderException;
import com.jz.nebula.exception.SkuOutOfStockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

//import com.jz.nebula.amqp.MessageProducer;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLogisticsInfoRepository orderLogisticsInfoRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private TokenService tokenService;

    /**
     * Get order by pagination
     *
     * @param pageable
     * @param assembler
     *
     * @return
     */
    public PagedResources<Resource<Order>> findAll(Pageable pageable, PagedResourcesAssembler<Order> assembler) {
        Page<Order> page;
        User user = authenticationFacade.getUser();
        if (user.isAdmin()) {
            page = orderRepository.findByUserId(user.getId(), pageable);
        } else {
            page = orderRepository.findAll(pageable);
        }

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
     *
     * @return
     */
    public PagedResources<Resource<Order>> findByUserId(long id, Pageable pageable,
                                                        PagedResourcesAssembler<Order> assembler) {
        Page<Order> page = orderRepository.findByUserId(id, pageable);
        PagedResources<Resource<Order>> resources = assembler.toResource(page,
                linkTo(OrderController.class).slash("/orders").withSelfRel());
        ;
        return resources;
    }

    /**
     * Check the order is new order
     *
     * @param order
     *
     * @return
     */
    private boolean isNewOrder(Order order) {
        return order.getId() == null;
    }

    /**
     * Check current user ROLE
     *
     * @param user
     *
     * @return
     */
    private boolean isUser(User user) {
        return user.getUserRoles().stream().map(userRole -> userRole.getRole()).map(role -> role.getCode()).collect(Collectors.toList()).contains(Role.USER);
    }

    /**
     * If current user is not ADMIN or VENDOR just replace the user id
     *
     * @param order
     *
     * @return
     */
    private synchronized Order preProcessing(Order order) throws SkuOutOfStockException, MultipleActivatedOrderException {
        Order safetyOrder = order;
        User user = this.authenticationFacade.getUser();
        if (isUser(user) && safetyOrder.getUserId() != null) {
            logger.info("preProcessingOrder::user with id:[{}] put userId: [{}] in the request", user.getId(), safetyOrder.getUserId());
            safetyOrder.setUserId(user.getId());
        }

        if (this.getCurrentActivatedOrder() != null) {
            throw new MultipleActivatedOrderException();
        }

        Order persistedOrder = null;

        if (order.getId() != null) {
            persistedOrder = this.findById(order.getId());
        }

        if (persistedOrder == null) {
            List<String> skuCodes = order.getOrderItems().stream().map(item -> item.getSkuCode()).collect(Collectors.toList());

            List<Long> orderProductId = order.getOrderItems().stream().map(item -> item.getProductId()).collect(Collectors.toList());

            List<Sku> skus = skuRepository.findBySkuCodeIn(skuCodes);

            List<Product> products = productService.findByIds(orderProductId);
            for (OrderItem orderItem : order.getOrderItems()) {
                // Check order item id
                ArrayList<Sku> persistedSku = (ArrayList<Sku>) skus.stream().filter(item -> item.getSkuCode() == orderItem.getSkuCode()).collect(Collectors.toList());
                if (persistedSku.size() > 0 && persistedSku.get(0).getStock() < orderItem.getQuantity()) {
                    logger.debug("preProcessing::sku code:[{}] out of stock", persistedSku.get(0).getSkuCode());
                    throw new SkuOutOfStockException();
                }

                // Check unit price
                ArrayList<Product> persistedProduct = (ArrayList<Product>) products.stream().filter(item -> item.getId() == orderItem.getProductId()).collect(Collectors.toList());
                if (persistedProduct.size() > 0) {
                    orderItem.setUnitPrice(persistedProduct.get(0).getPrice());
                }
            }
        }

        return safetyOrder;
    }

    /**
     * When order created push notification to queue and process it
     *
     * @param order
     *
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public Order createOrder(Order order) throws SkuOutOfStockException, MultipleActivatedOrderException {
        order = preProcessing(order);
        boolean isNew = this.isNewOrder(order);

        if (isNew) {
            order.setOrderStatusId((long) OrderStatus.StatusType.PENDING.value);
        }

        User currentUser = authenticationFacade.getUser();

//        if (!currentUser.isAdmin()) {
        order.setUserId(currentUser.getId());
//        }

        Order updatedOrder = orderRepository.save(order);

        this.deleteCartItems(order.getOrderItems());

        return updatedOrder;
    }

    /**
     * Native save function, should not put any business logic inside it
     *
     * @param order
     *
     * @return
     */
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void deleteCartItems(Set<OrderItem> finalisedOrderItems) {
        Cart cart = this.cartService.getMyCart();

        Set<CartItem> cartItems = cart.getCartItems();

        List<Long> finalisedOrderItemsId = finalisedOrderItems.stream().map(orderItem -> orderItem.getProductId()).collect(Collectors.toList());

        for (CartItem cartItem : cartItems) {
            if (finalisedOrderItemsId.contains(cartItem.getProductId())) {
                cartItemService.delete(cartItem.getId());
            }
        }
    }


    /**
     * Find order by id
     *
     * @param id
     *
     * @return
     */
    public Order findById(long id) {
        Order order = orderRepository.findById(id).get();

        if (order != null && order.getOrderStatus().getName().equals("pending")) {
            String paymentToken = tokenService.getPaymentToken(order);
            logger.debug("findById:: set payment token [{}]", paymentToken);
            order.setPaymentToken(tokenService.getPaymentToken(order));
        }

        return order;
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
            if (orders != null && orders.size() > 0) {
                order = orders.get(0);
            }
        }

        return order;
    }

    /**
     * @param orderLogisticsInfo
     *
     * @return
     */
    public OrderLogisticsInfo saveLogisticsInfo(OrderLogisticsInfo orderLogisticsInfo) {
        return orderLogisticsInfoRepository.save(orderLogisticsInfo);
    }

    public OrderLogisticsInfo findLogisticsInfoByOrderId(Long orderId) {
        Optional<OrderLogisticsInfo> orderLogisticsInfo = orderLogisticsInfoRepository.findByOrdersId(orderId);
        return orderLogisticsInfo.isPresent() ? orderLogisticsInfo.get() : null;
    }

    /**
     * @return
     */
    public boolean refund() {
        return false;
    }

    /***************** Admin Functionality **********************/

    public void deleteByIds(List<Long> ids) {
        orderRepository.deleteByIdIn(ids);
    }
}
