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

package com.jz.nebula.controller.api.edu;

import com.jz.nebula.dto.edu.ClazzCartItemParam;
import com.jz.nebula.dto.edu.ToCartOrderParam;
import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.edu.ClazzCart;
import com.jz.nebula.entity.edu.ClazzCartItem;
import com.jz.nebula.service.TokenService;
import com.jz.nebula.service.edu.ClazzCartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;

@RestController
@RequestMapping("/api/classes/carts")
public class ClazzCartController {
    private final static Logger logger = LogManager.getLogger(ClazzCartController.class);

    private ClazzCartService clazzCartService;

    @Autowired
    public void setClazzCartService(ClazzCartService clazzCartService) {
        this.clazzCartService = clazzCartService;
    }

    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ClazzCart findById(@PathVariable("id") long id) {
        return clazzCartService.findById(id);
    }

    /**
     * Convert clazz cart to clazz order. We return the payment token as well.
     *
     * @param id
     * @param toCardOrderParam
     *
     * @return
     *
     * @throws Exception
     */
    @PostMapping("/{id}/orders")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    HashMap<String, Object> toCartOrder(@PathVariable("id") long id, @RequestBody ToCartOrderParam toCardOrderParam) throws Exception {
        return clazzCartService.cartToOrder(toCardOrderParam);
    }

    /**
     * Add clazz to cart
     *
     * @param clazzCartItemParam
     *
     * @return
     *
     * @throws Exception
     */
    @PostMapping("/items")
    @RolesAllowed({Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ClazzCart addClazzCartItem(@RequestBody ClazzCartItemParam clazzCartItemParam) throws Exception {
        ClazzCart persistedClazzCart = clazzCartService.addItemToCart(clazzCartItemParam);
        return clazzCartService.findById(persistedClazzCart.getId());
    }

    /**
     * Update clazz cart item
     *
     * @param id
     * @param clazzCartItem
     *
     * @return
     *
     * @throws Exception
     */
    @PutMapping("/items/{id}")
    @RolesAllowed({Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ClazzCartItem updateClazzCartItem(@PathVariable("id") long id, @RequestBody ClazzCartItemParam clazzCartItemParam) throws Exception {
//        clazzCartItem.setId(id);
        return clazzCartService.updateClazzCartItem(id, clazzCartItemParam);
    }

    /**
     * Delete item from clazz cart
     *
     * @param id
     *
     * @return
     *
     * @throws Exception
     */
    @DeleteMapping("/items/{id}")
    @RolesAllowed({Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> deleteClazzCartItem(@PathVariable("id") long id) throws Exception {
        clazzCartService.deleteItemFromCart(id);
        return ResponseEntity.noContent().build();
    }


}
