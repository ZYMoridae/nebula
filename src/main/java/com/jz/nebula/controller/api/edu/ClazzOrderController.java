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
import com.jz.nebula.entity.edu.ClazzOrder;
import com.jz.nebula.entity.payment.CreditCard;
import com.jz.nebula.service.edu.ClazzOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;

@RestController
@RequestMapping("/api/classes/orders")
public class ClazzOrderController {
    private final static Logger logger = LogManager.getLogger(ClazzOrderController.class);

    @Autowired
    ClazzOrderService clazzOrderService;

    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ClazzOrder findById(@PathVariable("id") long id) {
        return clazzOrderService.findById(id);
    }


}
