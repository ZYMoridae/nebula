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

package com.jz.nebula.entity.edu;

import com.fasterxml.jackson.annotation.JsonView;
import com.jz.nebula.util.View;
import com.jz.nebula.entity.User;
import com.jz.nebula.entity.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "class_order", schema = "public")
@Getter
@Setter
public class ClazzOrder {
    public static final String PREFIX = "CLZ";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "total_price")
    private double totalPrice;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "class_order_id", nullable = false)
    private Set<ClazzOrderItem> clazzOrderItems;

    @Column(name = "status_id")
    private Long statusId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false, updatable = false, insertable = false)
    private OrderStatus orderStatus;

    @JsonView(View.Admin.class)
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonView(View.Admin.class)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Get total amount of order
     *
     * @return
     */
    public Optional<Double> getTotalAmount(boolean isInCent) {
        double totalAmount = 0;
        for (ClazzOrderItem clazzOrderItem : this.clazzOrderItems) {
            totalAmount += isInCent ? clazzOrderItem.getPrice() * 100 : clazzOrderItem.getPrice();
        }

        return Optional.of(totalAmount);
    }
}
