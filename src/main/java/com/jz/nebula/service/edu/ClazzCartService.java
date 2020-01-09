/*
 * Copyright (c) 2020. iEuclid Technology
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.jz.nebula.service.edu;

import com.jz.nebula.auth.AuthenticationFacade;
import com.jz.nebula.dao.OrderStatusRepository;
import com.jz.nebula.dao.edu.ClazzCartItemRepository;
import com.jz.nebula.dao.edu.ClazzCartRepository;
import com.jz.nebula.dao.edu.ClazzOrderRepository;
import com.jz.nebula.dao.edu.TeacherAvailableTimeRepository;
import com.jz.nebula.entity.Cart;
import com.jz.nebula.entity.edu.*;
import com.jz.nebula.entity.order.OrderStatus;
import com.jz.nebula.entity.payment.PaymentTokenCategory;
import com.jz.nebula.exception.edu.ClazzTimeUnavailableException;
import com.jz.nebula.exception.edu.DuplicateClazzCartItemException;
import com.jz.nebula.service.TokenService;
import lombok.Synchronized;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * How to create clazz cart and convert to clazz order
 * 1. Send below request to add cart item into the cart
 * JSON:
 * {
 *  "clazzId": 2,
 * 	"teacherAvailableTimeId": 3,
 * 	"price": 45
 * }
 *
 * 2. Send below request to convert clazz cart to clazz order
 * JSON:
 * [
 *     {
 *         "id": 1,
 *         "clazzId": 1,
 *         "teacherAvailableTimeId": 2,
 *         "price": 23
 *     },
 *     {
 *         "id": 4,
 *         "clazzId": 2,
 *         "teacherAvailableTimeId": 3,
 *         "price": 45
 *     }
 * ]
 */
@Service
public class ClazzCartService {
    private final Logger logger = LogManager.getLogger(ClazzCartService.class);

    @Autowired
    ClazzCartRepository clazzCartRepository;

    @Autowired
    ClazzCartItemRepository clazzCartItemRepository;

    @Autowired
    TeacherAvailableTimeRepository teacherAvailableTimeRepository;

    @Autowired
    ClazzOrderRepository clazzOrderRepository;

    @Autowired
    AuthenticationFacade authenticationFacade;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Autowired
    TokenService tokenService;

    public ClazzCart getClazzCartByUserId(long userId) {
        return clazzCartRepository.findByUserId(userId);
    }

    public ClazzCart save(ClazzCart clazzCart) {
        return clazzCartRepository.save(clazzCart);
    }

    /**
     * Check the availability before add to class cart
     *
     * @param clazzCartItem
     *
     * @return
     */
    private boolean isClazzTimeAvailable(ClazzCartItem clazzCartItem) {
        boolean isAvailable = false;

        TeacherAvailableTime teacherAvailableTime = teacherAvailableTimeRepository.findById(clazzCartItem.getTeacherAvailableTimeId()).get();
        if (teacherAvailableTime != null && !teacherAvailableTime.isReserved()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized ClazzCart addItemToCart(ClazzCartItem clazzCartItem) throws Exception {
        // If clazz time unavailable then throw exception
        if (!isClazzTimeAvailable(clazzCartItem)) {
            throw new ClazzTimeUnavailableException();
        }

        ClazzCart clazzCart = getClazzCartByUserId(authenticationFacade.getUserId());

        // If clazz cart not created then created
        if (clazzCart == null) {
            ClazzCart _clazzCart = new ClazzCart();
            _clazzCart.setUserId(authenticationFacade.getUserId());
            clazzCart = save(_clazzCart);
        } else {
            // Check the class item was added twice
            Set<ClazzCartItem> clazzCartItems = clazzCart.getClazzCartItems();
            for (ClazzCartItem item : clazzCartItems) {
                if (item.getClazzId() == clazzCartItem.getClazzId() && item.getTeacherAvailableTimeId() == clazzCartItem.getTeacherAvailableTimeId()) {
                    throw new DuplicateClazzCartItemException();
                }
            }
        }

        clazzCartItem.setClazzCartId(clazzCart.getId());
        clazzCartItemRepository.save(clazzCartItem);

        return clazzCart;
    }

    public ClazzCart getMyCart() {
        return clazzCartRepository.findByUserId(authenticationFacade.getUserId());
    }

    private boolean isCartItemsValid(List<ClazzCartItem> cartItemList, List<Long> cartItemIds) {
        List<Long> _cartItemIds = cartItemList.stream().map(item -> item.getId()).collect(Collectors.toList());
        _cartItemIds.removeAll(cartItemIds);

        return _cartItemIds.size() == 0;
    }

    private ClazzOrder createOrder(Set<ClazzOrderItem> orderItems) {
        ClazzOrder order = new ClazzOrder();
        order.setClazzOrderItems(orderItems);
        order.setUserId(this.authenticationFacade.getUser().getId());
        Optional<OrderStatus> orderStatus = orderStatusRepository.findByName("pending");
        order.setStatusId(orderStatus.get().getId());

        order = clazzOrderRepository.save(order);
        return order;
    }

    @Transactional(rollbackFor = {Exception.class})
    public HashMap<String, Object> cartToOrder(List<ClazzCartItem> clazzCartItemList) throws Exception {
        HashMap<String, Object> result = new HashMap<>();

        ClazzOrder order;
        ClazzCart cart = getMyCart();
        Set<ClazzCartItem> persistedCartItems = cart.getClazzCartItems();

        // Cart item validation
        List<Long> cartItemIds = persistedCartItems.stream().map(item -> item.getId()).collect(Collectors.toList());
        if (!this.isCartItemsValid(clazzCartItemList, cartItemIds))
            return null;

        Map<Long, ClazzCartItem> mappedCartItems = new ConcurrentHashMap<>();
        clazzCartItemList.forEach(item -> mappedCartItems.put(item.getId(), item));

        // Cart items will be updated
        List<ClazzCartItem> newCartItems = persistedCartItems.stream()
                .filter(item -> mappedCartItems.containsKey(item.getId())).collect(Collectors.toList());
//        newCartItems.forEach(item -> mappedPersistedCartItems.get(item.getId()).setQuantity(item.getQuantity()));
        logger.info("cartToOrder::cart items have been saved");

        // Create order
        Set<ClazzOrderItem> orderItems = newCartItems.stream().map(item -> item.toClazzOrderItem()).collect(Collectors.toSet());
        order = this.createOrder(orderItems);
        logger.info("cartToOrder::order has been created");

        // If order created successfully, then remove the cart item from cart

        List<Long> idToBeDeleted = newCartItems.stream().map(item -> item.getId()).collect(Collectors.toList());

        cart.setClazzCartItems(cart.getClazzCartItems().stream().filter(item -> !idToBeDeleted.contains(item.getId())).collect(Collectors.toSet()));

        deleteAllClazzCartItems(newCartItems);

        result.put("clazzOrder", order);
        result.put("paymentToken", tokenService.getPaymentToken(order.getId(), PaymentTokenCategory.CLAZZ));

        return result;
    }

    public void deleteAllClazzCartItems(Iterable<ClazzCartItem> cartItems) {
        clazzCartItemRepository.deleteAll(cartItems);
    }

    public ClazzCart findById(Long id) {
        return clazzCartRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        clazzCartRepository.deleteById(id);
    }

    /**
     * Delete item from cart
     *
     * @param cartItemId
     */
    public void deleteItemFromCart(Long cartItemId) {
        ClazzCart cart = getMyCart();

        Set<ClazzCartItem> clazzCartItems = cart.getClazzCartItems();
        int clazzCartItemSize = clazzCartItems.size();

        for (ClazzCartItem clazzCartItem : clazzCartItems) {
            if (clazzCartItem.getId() == cartItemId) {
                clazzCartItemRepository.delete(clazzCartItem);
                logger.debug("Class cart item with id [{}] was deleted", cartItemId);
                clazzCartItemSize--;
            }
        }

        // FIXME: If cart is empty then delete cart, should we still keep the cart record inside the table
        if(clazzCartItemSize == 0) {
            clazzCartRepository.deleteById(cart.getId());
        }
    }

    /**
     * Update class cart item
     *
     * @param clazzCartItem
     * @return
     */
    public ClazzCartItem updateClazzCartItem(ClazzCartItem clazzCartItem) {
        return clazzCartItemRepository.save(clazzCartItem);
    }
}
