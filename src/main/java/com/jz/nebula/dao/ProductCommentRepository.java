package com.jz.nebula.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jz.nebula.entity.ProductComment;

public interface ProductCommentRepository extends PagingAndSortingRepository<ProductComment, Long> {
	Optional<ProductComment> findByProductIdAndParentCommentId(long id, long parentCommentId);
}
