package com.jz.nebula.dao;

import com.jz.nebula.entity.teacher.Teacher;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TeacherRepository extends PagingAndSortingRepository<Teacher, Long> {
}
