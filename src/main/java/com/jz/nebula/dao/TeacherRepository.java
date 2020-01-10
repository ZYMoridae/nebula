package com.jz.nebula.dao;

import com.jz.nebula.entity.User;
import com.jz.nebula.entity.teacher.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeacherRepository extends PagingAndSortingRepository<Teacher, Long> {
    Page<Teacher> findAllByOrderByIdAsc(Pageable pageable);

    @Query("select c from Teacher c where lower(c.username) like %?1%")
    Page<Teacher> findByNameContaining(@Param("keyword") String keyword, Pageable pageable);
}
