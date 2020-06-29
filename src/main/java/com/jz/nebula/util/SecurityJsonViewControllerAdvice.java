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

package com.jz.nebula.util;

import com.jz.nebula.entity.Role;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Used for custom JSON field based on ROLE (combine with Spring Security)
 */
@RestControllerAdvice
class SecurityJsonViewControllerAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    @Override
    protected void beforeBodyWriteInternal(
            MappingJacksonValue bodyContainer,
            MediaType contentType,
            MethodParameter returnType,
            ServerHttpRequest request,
            ServerHttpResponse response) {
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getAuthorities() != null) {
            Collection<? extends GrantedAuthority> authorities
                    = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            List<Class> jsonViews = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .map(Role.ViewRole::valueOf)
                    .map(View.MAPPING::get)
                    .collect(Collectors.toList());
//            if (jsonViews.size() == 1) {
            Class _view = filterJsonView(jsonViews);
            bodyContainer.setSerializationView(_view);
            return;
//            }
//            throw new IllegalArgumentException("Ambiguous @JsonView declaration for roles "
//                    + authorities.stream()
//                    .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
        }
    }

    /**
     * The below logic will control which field will be returned to client through RESTful API
     * FIXME: Do we need to create separate view for teacher, vendor and user?
     * <p>
     * 1. ROLE_ANONYMOUS: This role will be used for public API view
     * 2. ROLE_USER:      Basic JSON view for all class
     * 3. ROLE_TEACHER:   Only return fields that teacher can be accessed
     * 4. ROLE_VENDOR:    Only return fields that vendor can be accessed
     * 5. ROLE_ADMIN:     The top level and includes all fields in the entity
     *
     * @param jsonViews
     *
     * @return
     */
    private Class filterJsonView(List<Class> jsonViews) {
        boolean isAdmin = false;
//        boolean isVendor = false;
//        boolean isTeacher = false;

        Class adminView = null;
        Class otherView = null;

        for (Class jsonView : jsonViews) {
            if (jsonView.getName().equals(View.Admin.class.getName())) {
                isAdmin = true;
                adminView = jsonView;
                break;
            } else {
//                if(jsonView.getName().equals(View.Vendor.class.getName())) {
                otherView = jsonView;
//                }
            }
        }

        // Return if role is admin
        if (isAdmin) {
            return adminView;
        }

        return otherView;
    }
}

