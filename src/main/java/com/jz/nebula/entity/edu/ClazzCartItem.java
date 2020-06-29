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

package com.jz.nebula.entity.edu;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.jz.nebula.util.View;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "class_cart_item", schema = "public")
@Getter
@Setter
public class ClazzCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "class_cart_id")
    private Long clazzCartId;

    @Column(name = "class_id")
    private Long clazzId;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id", nullable = false, updatable = false, insertable = false)
    private Clazz clazz;

    @Column(name = "teacher_available_time_id")
    private Long teacherAvailableTimeId;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_available_time_id", nullable = false, updatable = false, insertable = false)
    private TeacherAvailableTime teacherAvailableTime;

    private double price;

    @JsonView(View.Admin.class)
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonView(View.Admin.class)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Convert to clazz order item
     *
     * @return
     */
    public ClazzOrderItem toClazzOrderItem() {
        ClazzOrderItem clazzOrderItem = new ClazzOrderItem();
        clazzOrderItem.setClazzId(this.clazzId);
        clazzOrderItem.setPrice(this.price);
        clazzOrderItem.setTeacherAvailableTimeId(this.teacherAvailableTimeId);
        return clazzOrderItem;
    }
}
