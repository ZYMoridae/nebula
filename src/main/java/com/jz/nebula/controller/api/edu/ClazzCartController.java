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

import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.edu.ClazzCart;
import com.jz.nebula.entity.edu.ClazzCartItem;
import com.jz.nebula.entity.edu.ClazzOrder;
import com.jz.nebula.service.edu.ClazzCartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/classes/carts")
public class ClazzCartController {
    private final static Logger logger = LogManager.getLogger(ClazzCartController.class);

    @Autowired
    private ClazzCartService clazzCartService;

    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ClazzCart findById(@PathVariable("id") long id) {
        return clazzCartService.findById(id);
    }

    @PostMapping("/{id}/orders")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ClazzOrder toCartOrder(@PathVariable("id") long id, @RequestBody List<ClazzCartItem> cartItems) throws Exception {
        return clazzCartService.cartToOrder(cartItems);
    }

    /**
     * Add cla
     *
     * @param clazzCartItem
     *
     * @return
     *
     * @throws Exception
     */
    @PostMapping("/items")
    @RolesAllowed({Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ClazzCart addClazzCartItem(@RequestBody ClazzCartItem clazzCartItem) throws Exception {
        ClazzCart persistedClazzCart = clazzCartService.addItemToCart(clazzCartItem);
        return clazzCartService.findById(persistedClazzCart.getId());
    }

    @PutMapping("/items/{id}")
    @RolesAllowed({Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ClazzCartItem updateClazzCartItem(@PathVariable("id") long id, @RequestBody ClazzCartItem clazzCartItem) throws Exception {
        clazzCartItem.setId(id);
        return clazzCartService.updateClazzCartItem(clazzCartItem);
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
