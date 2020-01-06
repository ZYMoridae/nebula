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

import com.jz.nebula.dao.edu.ClazzOrderItemRepository;
import com.jz.nebula.dao.edu.ClazzOrderRepository;
import com.jz.nebula.dao.edu.TeacherAvailableTimeRepository;
import com.jz.nebula.entity.edu.ClazzOrder;
import com.jz.nebula.entity.edu.ClazzOrderItem;
import com.jz.nebula.entity.edu.TeacherAvailableTime;
import lombok.Synchronized;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

    public ClazzOrder findById(long id) {
        return clazzOrderRepository.findById(id).get();
    }

    public ClazzOrder save(ClazzOrder clazzOrder) {
        return clazzOrderRepository.save(clazzOrder);
    }

    public void deleteById(long id) {
        clazzOrderRepository.deleteById(id);
    }

    /**
     * Create clazz order
     *
     * @param clazzOrder
     * @return
     * @throws Exception
     */
    @Synchronized
    @Transactional(rollbackFor = {Exception.class})
    public ClazzOrder createClazzOrder(ClazzOrder clazzOrder) throws Exception {
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
            clazzOrderItem.setClazzOrderId(persistedClazzOrder.getId());

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
     * @return
     * @throws Exception
     */
    @Synchronized
    @Transactional(rollbackFor = {Exception.class})
    public ClazzOrder updateClazzOrder(ClazzOrder clazzOrder) throws Exception {
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
            clazzOrderItem.setClazzOrderId(persistedClazzOrder.getId());
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
    @Synchronized
    @Transactional(rollbackFor = {Exception.class})
    public void deleteClazzOrder(Long id) {
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
}
