package com.jz.nebula.dao.edu;

import com.jz.nebula.entity.edu.job.JobCategory;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface JobCategoryRepository extends PagingAndSortingRepository<JobCategory, Long>, JpaSpecificationExecutor<JobCategory> {
    List<JobCategory> findByIdIn(List<Long> ids);
}
