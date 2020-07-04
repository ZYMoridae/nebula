package com.jz.nebula.dao.edu;

import com.jz.nebula.entity.edu.job.Job;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobRepository extends PagingAndSortingRepository<Job, Long> {

}
