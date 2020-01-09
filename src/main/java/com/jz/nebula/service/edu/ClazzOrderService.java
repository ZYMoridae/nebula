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

package com.jz.nebula.service.edu;

import com.jz.nebula.auth.AuthenticationFacade;
import com.jz.nebula.dao.OrderStatusRepository;
import com.jz.nebula.dao.edu.ClazzOrderItemRepository;
import com.jz.nebula.dao.edu.ClazzOrderRepository;
import com.jz.nebula.dao.edu.TeacherAvailableTimeRepository;
import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.User;
import com.jz.nebula.entity.edu.*;
import com.jz.nebula.entity.order.OrderStatus;
import com.jz.nebula.entity.payment.PaymentTokenCategory;
import com.jz.nebula.exception.MultipleActivatedOrderException;
import com.jz.nebula.service.TokenService;
import lombok.Synchronized;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClazzOrderService {
    private final Logger logger = LogManager.getLogger(ClazzOrderService.class);

    @Autowired
    ClazzOrderRepository clazzOrderRepository;

    @Autowired
    ClazzOrderItemRepository clazzOrderItemRepository;

    @Autowired
    TeacherAvailableTimeRepository teacherAvailableTimeRepository;

    @Autowired
    AuthenticationFacade authenticationFacade;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Autowired
    TokenService tokenService;

    public ClazzOrder findById(long id) {
        return clazzOrderRepository.findById(id).get();
    }

    public HashMap getClazzOrderWithPaymentToken(long id) throws Exception {
        HashMap<String, Object> result = new HashMap<>();
        ClazzOrder clazzOrder = findById(id);
        result.put("clazzOrder", clazzOrder);
        result.put("paymentToken", tokenService.getPaymentToken(clazzOrder.getId(), PaymentTokenCategory.CLAZZ));
        return result;
    }

    public ClazzOrder save(ClazzOrder clazzOrder) {
        return clazzOrderRepository.save(clazzOrder);
    }

    public void deleteById(long id) {
        clazzOrderRepository.deleteById(id);
    }

    /**
     * We only return teacher available time to user and create clazz order
     *
     * @param clazzOrder
     *
     * @return
     *
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class})
    public synchronized ClazzOrder createClazzOrder(ClazzOrder clazzOrder) throws Exception {
        ClazzOrder persistedClazzOrder = new ClazzOrder();

        // Validate the clazz order
        if (persistedClazzOrder.getClazzOrderItems().size() == 0) {
            throw new Exception("Invalid Clazz order");
        }

        Set<ClazzOrderItem> clazzOrderItems = clazzOrder.getClazzOrderItems();

        persistedClazzOrder = save(clazzOrder);
        logger.debug("Clazz order created and order id is [{}]", persistedClazzOrder.getId());

        for (ClazzOrderItem clazzOrderItem : clazzOrderItems
        ) {
//            clazzOrderItem.setClazzOrderId(persistedClazzOrder.getId());

            // Make class reservation for user
            TeacherAvailableTime teacherAvailableTime = clazzOrderItem.getTeacherAvailableTime();
            teacherAvailableTime.setReserved(true);
            teacherAvailableTimeRepository.save(teacherAvailableTime);

            ClazzOrderItem persistedClazzOrderItem = clazzOrderItemRepository.save(clazzOrderItem);


            logger.debug("Clazz order item created and item id is [{}]", persistedClazzOrderItem.getId());
        }

        return persistedClazzOrder;
    }

    /**
     * Update clazz order
     *
     * @param clazzOrder
     *
     * @return
     *
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class})
    public synchronized ClazzOrder updateClazzOrder(ClazzOrder clazzOrder) throws Exception {
        ClazzOrder persistedClazzOrder = new ClazzOrder();

        if (clazzOrder.getId() == null) {
            throw new Exception("Invalid Clazz order");
        }

        // Validate the clazz order
        if (persistedClazzOrder.getClazzOrderItems().size() == 0) {
            throw new Exception("Invalid Clazz order");
        }

        Set<ClazzOrderItem> clazzOrderItems = clazzOrder.getClazzOrderItems();

        persistedClazzOrder = findById(clazzOrder.getId());

        List<ClazzOrderItem> itemToBeDeleted = new ArrayList<>();
        List<Long> userListId = clazzOrder.getClazzOrderItems().stream().map(item -> item.getId()).collect(Collectors.toList());
        for (ClazzOrderItem clazzOrderItem : persistedClazzOrder.getClazzOrderItems()
        ) {
            if (!userListId.contains(clazzOrderItem.getId())) {
                itemToBeDeleted.add(clazzOrderItem);
            }
        }
        logger.debug("[{}] items need to be delete", userListId.size());

        for (ClazzOrderItem clazzOrderItem : itemToBeDeleted) {
            // Reset reservation for clazz order item
            TeacherAvailableTime teacherAvailableTime = clazzOrderItem.getTeacherAvailableTime();
            teacherAvailableTime.setReserved(false);
            teacherAvailableTimeRepository.save(teacherAvailableTime);

            clazzOrderItemRepository.delete(clazzOrderItem);
            logger.debug("Item with id [{}] was deleted", clazzOrderItem.getId());
        }

        for (ClazzOrderItem clazzOrderItem : clazzOrderItems
        ) {
//            clazzOrderItem.setClazzOrderId(persistedClazzOrder.getId());
            ClazzOrderItem persistedClazzOrderItem = clazzOrderItemRepository.save(clazzOrderItem);
            logger.debug("Clazz order item updated and item id is [{}]", persistedClazzOrderItem.getId());
        }

        persistedClazzOrder = save(clazzOrder);
        logger.debug("Clazz order updated and order id is [{}]", persistedClazzOrder.getId());

        return persistedClazzOrder;
    }

    /**
     * Delete clazz order
     *
     * @param id
     */
    @Transactional(rollbackFor = {Exception.class})
    public synchronized void deleteClazzOrder(Long id) {
        ClazzOrder clazzOrder = findById(id);

        for (ClazzOrderItem clazzOrderItem : clazzOrder.getClazzOrderItems()
        ) {
            // Reset teach available time
            TeacherAvailableTime teacherAvailableTime = clazzOrderItem.getTeacherAvailableTime();
            teacherAvailableTime.setReserved(false);
            teacherAvailableTimeRepository.save(teacherAvailableTime);

            clazzOrderItemRepository.delete(clazzOrderItem);
            logger.debug("Clazz order item with id [{}] was deleted", clazzOrderItem.getId());
        }

        clazzOrderRepository.delete(clazzOrder);
    }

    public ClazzOrder getCurrentActivatedOrder() {
        ClazzOrder order = null;

        Optional<OrderStatus> orderStatus = orderStatusRepository.findByName("pending");
        if (orderStatus.isPresent()) {
            List<ClazzOrder> orders = clazzOrderRepository.findByUserIdAndStatusId(authenticationFacade.getUserId(), orderStatus.get().getId());
            if (orders != null && orders.size() > 0) {
                order = orders.get(0);
            }
        }

        return order;
    }

    /**
     * This function must be called after cart converted to active clazz order
     *
     * @param clazzOrder
     *
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class})
    public synchronized ClazzOrder createOrder(ClazzOrder clazzOrder) throws Exception {
        ClazzOrder persistedClazzOrder = getCurrentActivatedOrder();
        if (persistedClazzOrder == null) {
            ClazzOrder _clazzOrder = new ClazzOrder();
            _clazzOrder.setStatusId((long) OrderStatus.StatusType.PENDING.value);
            _clazzOrder.setUserId(authenticationFacade.getUserId());
        } else {
            throw new MultipleActivatedOrderException();
        }


        return persistedClazzOrder;
    }


    private boolean isUser(User user) {
        return user.getUserRoles().stream().map(userRole -> userRole.getRole()).map(role -> role.getCode()).collect(Collectors.toList()).contains(Role.USER);
    }


    /**
     * Lock time slot
     *
     * @param teacherAvailableTimeId
     *
     * @throws Exception
     */
    public synchronized void lockTimeSlot(Long teacherAvailableTimeId) throws Exception {
        // Lock teacher available time
        TeacherAvailableTime teacherAvailableTime = teacherAvailableTimeRepository.findById(teacherAvailableTimeId).get();
        if (teacherAvailableTime != null) {
            teacherAvailableTime.setReserved(true);
            teacherAvailableTimeRepository.save(teacherAvailableTime);
        } else {
            throw new Exception("Teacher available time not found");
        }
    }
}
