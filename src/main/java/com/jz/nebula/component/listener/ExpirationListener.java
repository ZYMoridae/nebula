/*
 * Copyright (c) 2019. iEuclid Technology
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

package com.jz.nebula.component.listener;

import com.jz.nebula.dao.OrderRepository;
import com.jz.nebula.dao.OrderStatusRepository;
import com.jz.nebula.entity.order.Order;
import com.jz.nebula.entity.order.OrderItem;
import com.jz.nebula.entity.order.OrderStatus;
import com.jz.nebula.entity.product.Product;
import com.jz.nebula.service.ProductService;
import lombok.Synchronized;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class ExpirationListener implements MessageListener {
    private final static Logger logger = LogManager.getLogger(ExpirationListener.class);

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = new String(message.getBody());
        if (key.startsWith("ORD")) {
            Long orderId = Long.valueOf(key.replace("ORD", ""));
            Order order = orderRepository.findById(orderId).get();
            if (order != null && order.getOrderStatus().getName().equals("pending")) {
                logger.debug("onMessage:: starting recover stock for");
                recoverStock(order);
            }
        }

        logger.debug("expired key: {}", key);
    }

    /**
     * FIXME: When recover the stock, we should recover the sku stock not the product stock, this need to be fixed urgently
     *
     * @param order
     */
    @Transactional(rollbackFor = {Exception.class})
    public synchronized void recoverStock(Order order) {
        OrderStatus orderStatus = orderStatusRepository.findByName("token_expired").get();

        // We just set the order status to token expired, but we still keep the record in the database for some purposes
        if (orderStatus != null) {
            logger.debug("recoverStock::find status token_expired");
            order.setOrderStatusId(orderStatus.getId());
            orderRepository.save(order);
        }

        Set<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            int quantity = orderItem.getQuantity();
            Product product = orderItem.getProduct();
            product.setUnitsInStock(product.getUnitsInStock() + quantity);
            productService.save(product);
            logger.debug("recoverStock:: product id [{}], stock quantity [{}] added", product.getId(), quantity);
        }
    }
}
