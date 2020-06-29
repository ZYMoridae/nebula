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

package com.jz.nebula.service.edu;

import com.jz.nebula.dao.edu.TeacherAvailableTimeRepository;
import com.jz.nebula.entity.edu.TeacherAvailableTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class TeacherAvailableTimeService {

    @Autowired
    TeacherAvailableTimeRepository teacherAvailableTimeRepository;

    public TeacherAvailableTime findById(long id) {
        return teacherAvailableTimeRepository.findById(id).get();
    }

    public TeacherAvailableTime save(TeacherAvailableTime teacherAvailableTime) {
        TeacherAvailableTime persistedTeacherAvailableTime = teacherAvailableTimeRepository.save(teacherAvailableTime);
        return findById(persistedTeacherAvailableTime.getId());
    }

    public void delete(long id) {
        teacherAvailableTimeRepository.deleteById(id);
    }

    /**
     * Check teacher is available during the user selected time
     * 1. Check the time slot is reserved by someone else or not
     * 2. Check the time slot is fall between the available time
     *
     * @param teacherAvailableTime
     * @param userStartTime
     * @param userEndTime
     *
     * @return
     */
    private boolean isTeacherAvailable(TeacherAvailableTime teacherAvailableTime, Date userStartTime, Date userEndTime) {
        boolean isAvailable = false;

        if (teacherAvailableTime.isReserved()) {
            return false;
        }

        Date clazzStartTime = teacherAvailableTime.getStartTime();
        Date clazzEndTime = teacherAvailableTime.getEndTime();

        if (clazzStartTime.getTime() <= userStartTime.getTime() && clazzEndTime.getTime() >= userEndTime.getTime()) {
            isAvailable = true;
        } else {
            isAvailable = false;
        }

        return isAvailable;
    }
}
