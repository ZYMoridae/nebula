/*
 * Copyright (c) 2019. Nebula Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jz.nebula.service.analytics;

import com.jz.nebula.dao.OrderItemRepository;
import com.jz.nebula.dao.OrderRepository;
import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.entity.analytics.DashboardSummary;
import com.jz.nebula.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    public DashboardSummary getDashboardSummary() {
        DashboardSummary dashboardSummary = new DashboardSummary();
        dashboardSummary.setActiveUsers(userRepository.count());
        dashboardSummary.setTotalOrders(orderRepository.count());
        dashboardSummary.setTransaction(orderItemRepository.getTotalTransaction());
        return dashboardSummary;
    }

}
