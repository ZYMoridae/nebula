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
import com.jz.nebula.dao.edu.ClazzCartItemRepository;
import com.jz.nebula.dao.edu.ClazzCartRepository;
import com.jz.nebula.dao.edu.ClazzOrderRepository;
import com.jz.nebula.dao.edu.TeacherAvailableTimeRepository;
import com.jz.nebula.entity.Cart;
import com.jz.nebula.entity.edu.*;
import lombok.Synchronized;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

    public ClazzCart getClazzCart() {
        return clazzCartRepository.findByUserId(authenticationFacade.getUserId());
    }

    public ClazzCart save(ClazzCart clazzCart) {
        return clazzCartRepository.save(clazzCart);
    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized ClazzCart addItemToCart(ClazzCartItem clazzCartItem) throws Exception {
        ClazzCart clazzCart = getClazzCart();

        // If clazz cart not created then created
        if (clazzCart == null) {
            ClazzCart _clazzCart = new ClazzCart();
            _clazzCart.setUserId(authenticationFacade.getUserId());
            clazzCart = save(_clazzCart);
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

        order = clazzOrderRepository.save(order);
        return order;
    }

    @Transactional(rollbackFor = {Exception.class})
    public ClazzOrder cartToOrder(List<ClazzCartItem> clazzCartItemList) throws Exception {
        ClazzOrder order;
        ClazzCart cart = getMyCart();
        Set<ClazzCartItem> persistedCartItems = cart.getClazzCartItems();

        // Cart item validation
        List<Long> cartItemIds = persistedCartItems.stream().map(item -> item.getId()).collect(Collectors.toList());
        if (!this.isCartItemsValid(clazzCartItemList, cartItemIds))
            return null;

        Map<Long, ClazzCartItem> mappedPersistedCartItems = new ConcurrentHashMap<>();
        persistedCartItems.forEach(item -> mappedPersistedCartItems.put(item.getId(), item));

        // Cart items will be updated
        List<ClazzCartItem> newCartItems = clazzCartItemList.stream()
                .filter(item -> mappedPersistedCartItems.containsKey(item.getId())).collect(Collectors.toList());
//        newCartItems.forEach(item -> mappedPersistedCartItems.get(item.getId()).setQuantity(item.getQuantity()));
        logger.info("cartToOrder::cart items have been saved");

        // Create order
        Set<ClazzOrderItem> orderItems = newCartItems.stream().map(item -> item.toClazzOrderItem()).collect(Collectors.toSet());
        order = this.createOrder(orderItems);
        logger.info("cartToOrder::order has been created");

        // If order created successfully, then remove the cart item from cart
        deleteAllClazzCartItems(newCartItems);

        return order;
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

        cart.getClazzCartItems().stream().forEach(clazzCartItem -> {
            if (clazzCartItem.getId() == cartItemId) {
                clazzCartItemRepository.delete(clazzCartItem);
            }
        });
    }
}
