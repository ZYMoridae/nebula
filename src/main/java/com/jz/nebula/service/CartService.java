package com.jz.nebula.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.jz.nebula.util.auth.AuthenticationFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jz.nebula.dao.CartRepository;
import com.jz.nebula.dao.OrderRepository;
import com.jz.nebula.entity.Cart;
import com.jz.nebula.entity.CartItem;
import com.jz.nebula.entity.order.Order;
import com.jz.nebula.entity.order.OrderItem;
import com.jz.nebula.entity.order.OrderStatus;

@Service
@Transactional
public class CartService {
    private final Logger logger = LogManager.getLogger(CartService.class);

    private AuthenticationFacade authenticationFacade;

    private CartRepository cartRepository;

    private OrderRepository orderRepository;

    private CartItemService cartItemService;

    @Autowired
    public CartService(AuthenticationFacade authenticationFacade,
                       CartRepository cartRepository,
                       OrderRepository orderRepository,
                       CartItemService cartItemService) {
        this.authenticationFacade = authenticationFacade;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.cartItemService = cartItemService;
    }

    /**
     * Get cart by userId
     *
     * @param userId
     *
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
        return orders != null && orders.size() == 1 ? true : false;
    }

    /**
     * Create order
     *
     * @param orderItems
     *
     * @return
     */
    private Order createOrder(Set<OrderItem> orderItems) {
        Order order = new Order();
        order.setOrderItems(orderItems);
        order.setUser(this.authenticationFacade.getUser());
        // TODO: Read shipper from user preference
//        order.setLogisticsProvider();

        order = orderRepository.save(order);
        return order;
    }

    /**
     * Check the cart item IDs pass through from front end are in the cart.
     *
     * @param cartItemList
     * @param cartItemIds
     *
     * @return
     */
    private boolean isCartItemsValid(List<CartItem> cartItemList, List<Long> cartItemIds) {
        List<Long> _cartItemIds = cartItemList.stream().map(item -> item.getId()).collect(Collectors.toList());
        _cartItemIds.removeAll(cartItemIds);

        return _cartItemIds.size() == 0;
    }

    /**
     * Create order from cart. All cart items will be put into order and logistics
     * address will be updated through Order UPDATE API. (All these cart items will
     * be deleted from cart.) The payment API will be called in the last step.
     *
     * @return
     *
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class})
    public Order cartToOrder() throws Exception {
        if (!this.isOneOrderActivated()) {
            throw new Exception();
        }

        Cart cart = getMyCart();
        Set<OrderItem> orderItems = cart.getCartItems().stream().map(item -> item.toOrderItem())
                .collect(Collectors.toSet());
        Order order = this.createOrder(orderItems);

        // If order created successfully, then remove the cart item from cart
        cartItemService.deleteAll(cart.getCartItems());

        return order;
    }

    /**
     * Create order from cart item list, this is the first step in order process.
     * After first step, logistics address will be updated in the second step through
     * UPDATE API endpoint. (All these cart items will be deleted from cart.) The
     * payment API will be called in the last step.
     *
     * @param cartItemList
     *
     * @return
     *
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class})
    public Order cartToOrder(List<CartItem> cartItemList) throws Exception {
        Order order;
        Cart cart = getMyCart();
        Set<CartItem> persistedCartItems = cart.getCartItems();

        // Cart item validation
        List<Long> cartItemIds = persistedCartItems.stream().map(item -> item.getId()).collect(Collectors.toList());
        if (!this.isCartItemsValid(cartItemList, cartItemIds))
            return null;

        Map<Long, CartItem> mappedPersistedCartItems = new ConcurrentHashMap<>();
        persistedCartItems.forEach(item -> mappedPersistedCartItems.put(item.getId(), item));

        // Cart items will be updated
        List<CartItem> newCartItems = cartItemList.stream()
                .filter(item -> mappedPersistedCartItems.containsKey(item.getId())).collect(Collectors.toList());
        newCartItems.forEach(item -> mappedPersistedCartItems.get(item.getId()).setQuantity(item.getQuantity()));
        logger.info("cartToOrder::cart items have been saved");

        // Create order
        Set<OrderItem> orderItems = newCartItems.stream().map(item -> item.toOrderItem()).collect(Collectors.toSet());
        order = this.createOrder(orderItems);
        logger.info("cartToOrder::order has been created");

        // If order created successfully, then remove the cart item from cart
        cartItemService.deleteAll(newCartItems);

        return order;
    }

}
