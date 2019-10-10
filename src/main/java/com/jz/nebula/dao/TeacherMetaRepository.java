package com.jz.nebula.dao;

import com.jz.nebula.entity.teacher.TeacherMeta;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TeacherMetaRepository extends PagingAndSortingRepository<TeacherMeta, Long> {
    Optional<TeacherMeta> findByUserId(Long userId);
}
