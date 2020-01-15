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
import com.jz.nebula.controller.api.edu.ClazzController;
import com.jz.nebula.dao.edu.ClazzCategoryRepository;
import com.jz.nebula.dao.edu.ClazzRepository;
import com.jz.nebula.dao.edu.TeacherAvailableTimeRepository;
import com.jz.nebula.entity.User;
import com.jz.nebula.entity.edu.Clazz;
import com.jz.nebula.entity.edu.ClazzCategory;
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

    @Autowired
    ClazzCategoryRepository clazzCategoryRepository;

    /**
     * Find all classes
     *
     * @param keyword
     * @param pageable
     * @param assembler
     *
     * @return
     */
    public PagedResources<Resource<Clazz>> findAll(long clazzCategoryId, String keyword, Pageable pageable,
                                                   PagedResourcesAssembler<Clazz> assembler) {
        Page<Clazz> page;
        if (Strings.isNullOrEmpty(keyword)) {
            page = clazzRepository.findAllByOrderByIdAsc(pageable);
            logger.debug("findAll::order by id");
        } else {
            page = clazzRepository.findByClazzCategoryIdAndNameContaining(clazzCategoryId, keyword, pageable);
            logger.debug("findAll::find by name containing");
        }

        PagedResources<Resource<Clazz>> resources = assembler.toResource(page,
                linkTo(UserController.class).slash("/classes").withSelfRel());

        return resources;
    }

    /**
     * Find by id
     *
     * @param id
     *
     * @return
     */
    public Clazz findById(long id) {
        return clazzRepository.findById(id).get();
    }

    /**
     * Native save class
     *
     * @param clazz
     *
     * @return
     */
    public Clazz save(Clazz clazz) {
        Clazz persistedClazz = clazzRepository.save(clazz);
        return findById(persistedClazz.getId());
    }

    /**
     * Delete class by id
     *
     * @param id
     */
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
     *
     * @return
     */
    public TeacherAvailableTime saveTeacherAvailableTime(TeacherAvailableTime teacherAvailableTime) {
        return teacherAvailableTimeRepository.save(teacherAvailableTime);
    }

    /**
     * Find teacher available time by id
     *
     * @param id
     *
     * @return
     */
    public TeacherAvailableTime findTeacherAvailableTimeById(long id) {
        return teacherAvailableTimeRepository.findById(id).get();
    }

    public ClazzCategory findClazzCategoryById(long id) {
        return clazzCategoryRepository.findById(id).get();
    }

    public ClazzCategory saveClazzCategory(ClazzCategory clazzCategory) {
        return clazzCategoryRepository.save(clazzCategory);
    }

    public void deleteClazzCategoryById(long id) {
        clazzCategoryRepository.deleteById(id);
    }

    public PagedResources<Resource<ClazzCategory>> findAllClazzCategory(String keyword, Pageable pageable,
                                                                        PagedResourcesAssembler<ClazzCategory> assembler) {

        Page<ClazzCategory> page;
        if (Strings.isNullOrEmpty(keyword)) {
            page = clazzCategoryRepository.findAll(pageable);
        } else {
            page = clazzCategoryRepository.findByNameContaining(keyword, pageable);
        }

        PagedResources<Resource<ClazzCategory>> resources = assembler.toResource(page,
                linkTo(ClazzController.class).slash("/classes/categories").withSelfRel());
        ;
        return resources;
    }
}
