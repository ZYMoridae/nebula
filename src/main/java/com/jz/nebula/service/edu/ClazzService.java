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

import com.google.common.base.Strings;
import com.jz.nebula.controller.api.UserController;
import com.jz.nebula.dao.edu.ClazzRepository;
import com.jz.nebula.dao.edu.TeacherAvailableTimeRepository;
import com.jz.nebula.entity.User;
import com.jz.nebula.entity.edu.Clazz;
import com.jz.nebula.entity.edu.TeacherAvailableTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Service
public class ClazzService {
    private final static Logger logger = LogManager.getLogger(ClazzService.class);

    @Autowired
    ClazzRepository clazzRepository;

    @Autowired
    TeacherAvailableTimeRepository teacherAvailableTimeRepository;

    public PagedResources<Resource<Clazz>> findAll(String keyword, Pageable pageable,
                                                  PagedResourcesAssembler<Clazz> assembler) {
        Page<Clazz> page;
        if (Strings.isNullOrEmpty(keyword)) {
            page = clazzRepository.findAllByOrderByIdAsc(pageable);
            logger.debug("findAll::");
        } else {
            page = clazzRepository.findByNameContaining(keyword, pageable);
        }

        PagedResources<Resource<Clazz>> resources = assembler.toResource(page,
                linkTo(UserController.class).slash("/classes").withSelfRel());

        return resources;
    }

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
     *
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

    /**
     * Update teacher available time
     *
     * @param teacherAvailableTime
     * @return
     */
    public TeacherAvailableTime saveTeacherAvailableTime(TeacherAvailableTime teacherAvailableTime) {
        return teacherAvailableTimeRepository.save(teacherAvailableTime);
    }

    public TeacherAvailableTime findTeacherAvailableTimeById(long id) {
        return teacherAvailableTimeRepository.findById(id).get();
    }
}
