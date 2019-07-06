package com.jz.nebula.controller;

import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.teacher.Teacher;
import com.jz.nebula.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Teacher findById(@PathVariable("id") long id) {
        return teacherService.findById(id);
    }


}
