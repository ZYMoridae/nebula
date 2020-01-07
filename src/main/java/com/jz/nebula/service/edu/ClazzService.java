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

import com.jz.nebula.dao.edu.ClazzRepository;
import com.jz.nebula.dao.edu.TeacherAvailableTimeRepository;
import com.jz.nebula.entity.edu.Clazz;
import com.jz.nebula.entity.edu.TeacherAvailableTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClazzService {

    @Autowired
    ClazzRepository clazzRepository;

    @Autowired
    TeacherAvailableTimeRepository teacherAvailableTimeRepository;

    public Clazz findById(long id) {
        return clazzRepository.findById(id).get();
    }

    public Clazz save(Clazz clazz) {
        Clazz persistedClazz = clazzRepository.save(clazz);
        return findById(persistedClazz.getId());
    }

    public void delete(long id) {
        clazzRepository.deleteById(id);
    }

    /**
     * Create teacher available time
     *
     * @param teacherAvailableTime
     * @return
     */
    public TeacherAvailableTime createTeacherAvailableTime(TeacherAvailableTime teacherAvailableTime) {
        return teacherAvailableTimeRepository.save(teacherAvailableTime);
    }

    /**
     * Delete teacher available time by id
     *
     * @param id
     */
    public void deleteTeacherAvailableTimeById(Long id) {
        teacherAvailableTimeRepository.deleteById(id);
    }
}
