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

package com.jz.nebula;


import com.jz.nebula.entity.Role;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used for JSON view
 *
 */
public class View {
    public static final Map<Role.ViewRole, Class> MAPPING = new HashMap<>();

    static {
        MAPPING.put(Role.ViewRole.ROLE_ADMIN, Admin.class);
        MAPPING.put(Role.ViewRole.ROLE_USER, User.class);
        MAPPING.put(Role.ViewRole.ROLE_TEACHER, Teacher.class);
        MAPPING.put(Role.ViewRole.ROLE_VENDOR, Vendor.class);
        MAPPING.put(Role.ViewRole.ROLE_ANONYMOUS, User.class);
    }

    public static class User {

    }

    public static class Admin extends User {

    }

    public static class Vendor extends User {

    }

    public static class Teacher extends User {

    }
}
