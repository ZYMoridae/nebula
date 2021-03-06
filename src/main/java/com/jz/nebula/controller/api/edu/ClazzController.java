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

package com.jz.nebula.controller.api.edu;

import com.jz.nebula.dto.edu.ClazzParam;
import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.edu.Clazz;
import com.jz.nebula.entity.edu.ClazzCategory;
import com.jz.nebula.entity.edu.TeacherAvailableTime;
import com.jz.nebula.service.edu.ClazzService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/api/classes")
public class ClazzController {
    private final static Logger logger = LogManager.getLogger(ClazzController.class);

    private ClazzService clazzService;

    @Autowired
    public void setClazzService(ClazzService clazzService) {
        this.clazzService = clazzService;
    }

    /**
     * Find all classes
     *
     * @param keyword
     * @param pageable
     * @param uriBuilder
     * @param response
     * @param assembler
     *
     * @return
     */
    @GetMapping
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_USER, Role.ROLE_TEACHER, Role.ROLE_VENDOR})
    public @ResponseBody
    PagedModel<EntityModel<Clazz>> all(@RequestParam long clazzCategoryId, @RequestParam String keyword, Pageable pageable,
                                       final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
                                       PagedResourcesAssembler<Clazz> assembler) {
        return clazzService.findAll(clazzCategoryId, keyword, pageable, assembler);
    }

    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_TEACHER, Role.ROLE_ADMIN})
    public @ResponseBody
    Clazz findById(@PathVariable("id") long id) {
        return clazzService.findById(id);
    }

    @PostMapping("")
    @RolesAllowed({Role.ROLE_TEACHER, Role.ROLE_ADMIN})
    public @ResponseBody
    Clazz create(@Validated @RequestBody ClazzParam clazzParam) {
        return clazzService.create(clazzParam);
    }

    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_TEACHER, Role.ROLE_ADMIN})
    public @ResponseBody
    Clazz update(@PathVariable("id") long id, @RequestBody ClazzParam clazzParam) {
        return clazzService.update(id, clazzParam);
    }

    /**
     * Delete class by id
     *
     * @param id
     *
     * @return
     */
    @DeleteMapping("/{id}")
    @RolesAllowed({Role.ROLE_TEACHER, Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> delete(@PathVariable("id") long id) {
        clazzService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Create teacher availabilities for particular class
     *
     * @param id
     * @param teacherAvailableTime
     *
     * @return
     */
    @PostMapping("/{id}/teachers/availabilities")
    @RolesAllowed({Role.ROLE_TEACHER, Role.ROLE_ADMIN})
    public @ResponseBody
    TeacherAvailableTime createTeacherAvailableTime(@PathVariable("id") long id, @RequestBody TeacherAvailableTime teacherAvailableTime) {
        return clazzService.createTeacherAvailableTime(teacherAvailableTime);
    }

    /**
     * Delete teacher availabilities for particular class
     *
     * @param id
     * @param availId
     *
     * @return
     */
    @DeleteMapping("/{id}/teachers/availabilities/{availId}")
    @RolesAllowed({Role.ROLE_TEACHER, Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> deleteTeacherAvailableTime(@PathVariable("id") long id, @PathVariable("availId") long availId) {
        clazzService.deleteTeacherAvailableTimeById(availId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Update teacher available time by id
     *
     * @param availId
     * @param teacherAvailableTime
     *
     * @return
     */
    @PutMapping("/{id}/teachers/availabilities/{availId}")
    @RolesAllowed({Role.ROLE_TEACHER, Role.ROLE_ADMIN})
    public @ResponseBody
    TeacherAvailableTime updateTeacherAvailableTime(@PathVariable("availId") long availId, @RequestBody TeacherAvailableTime teacherAvailableTime) {
        teacherAvailableTime.setId(availId);
        return clazzService.saveTeacherAvailableTime(teacherAvailableTime);
    }

    /**
     * Find teacher available time by id
     *
     * @param availId
     *
     * @return
     */
    @GetMapping("/{id}/teachers/availabilities/{availId}")
    @RolesAllowed({Role.ROLE_TEACHER, Role.ROLE_ADMIN})
    public @ResponseBody
    TeacherAvailableTime getTeacherAvailableTime(@PathVariable("availId") long availId) {
        return clazzService.findTeacherAvailableTimeById(availId);
    }

    /**
     * Get class category by id
     *
     * @param id
     *
     * @return
     */
    @GetMapping("/categories/{id}")
    @RolesAllowed({Role.ROLE_TEACHER, Role.ROLE_ADMIN, Role.ROLE_USER, Role.ROLE_ADMIN})
    public @ResponseBody
    ClazzCategory getClazzCategory(@PathVariable("id") long id) {
        return clazzService.findClazzCategoryById(id);
    }

    /**
     * Create new class category
     *
     * @param clazzCategory
     *
     * @return
     */
    @PostMapping("/categories")
    @RolesAllowed({Role.ROLE_ADMIN})
    public @ResponseBody
    ClazzCategory createClazzCategory(@RequestBody ClazzCategory clazzCategory) {
        return clazzService.saveClazzCategory(clazzCategory);
    }

    /**
     * Update class category by id
     *
     * @param id
     * @param clazzCategory
     *
     * @return
     */
    @PutMapping("/categories/{id}")
    @RolesAllowed({Role.ROLE_ADMIN})
    public @ResponseBody
    ClazzCategory createClazzCategory(@PathVariable("id") long id, @RequestBody ClazzCategory clazzCategory) {
        clazzCategory.setId(id);
        return clazzService.saveClazzCategory(clazzCategory);
    }

    /**
     * Delete category by id
     *
     * @param id
     *
     * @return
     */
    @DeleteMapping("/categories/{id}")
    @RolesAllowed({Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> deleteClazzCategory(@PathVariable("id") long id) {
        clazzService.deleteClazzCategoryById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Find all class categories
     *
     * @param keyword
     * @param pageable
     * @param uriBuilder
     * @param response
     * @param assembler
     *
     * @return
     */
    @GetMapping("/categories")
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_USER, Role.ROLE_TEACHER, Role.ROLE_VENDOR})
    public @ResponseBody
    PagedModel<EntityModel<ClazzCategory>> getAllClazzCategory(@RequestParam String keyword, Pageable pageable,
                                                               final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
                                                               PagedResourcesAssembler<ClazzCategory> assembler) {
        return clazzService.findAllClazzCategory(keyword, pageable, assembler);
    }

}
