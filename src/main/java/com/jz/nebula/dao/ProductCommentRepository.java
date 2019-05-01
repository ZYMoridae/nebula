package com.jz.nebula.dao;

import com.jz.nebula.entity.ProductComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductCommentRepository extends PagingAndSortingRepository<ProductComment, Long> {
    Page<ProductComment> findByProductIdAndParentCommentId(long id, long parentCommentId, Pageable pageable);
}
