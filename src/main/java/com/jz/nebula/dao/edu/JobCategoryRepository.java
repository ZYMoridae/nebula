package com.jz.nebula.dao.edu;

import com.jz.nebula.entity.JobCategory;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobCategoryRepository extends PagingAndSortingRepository<JobCategory, Long>, JpaSpecificationExecutor<JobCategory> {

}
