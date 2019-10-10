package com.jz.nebula.dao;

import com.jz.nebula.entity.User;
import com.jz.nebula.entity.teacher.Teacher;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TeacherRepository extends PagingAndSortingRepository<Teacher, Long> {
}
