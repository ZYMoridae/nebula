package com.jz.nebula.service;

import com.jz.nebula.dao.TeacherRepository;
import com.jz.nebula.entity.teacher.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    @Autowired
    private TeacherRepository teacherRepository;

    public Teacher findById(long id) {
        return teacherRepository.findById(id).get();
    }
}
