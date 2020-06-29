package com.jz.nebula.service.edu;

import com.jz.nebula.controller.api.edu.JobController;
import com.jz.nebula.dao.edu.JobCategoryRepository;
import com.jz.nebula.entity.JobCategory;
import com.jz.nebula.entity.job.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class JobCategoryService {

    private JobCategoryRepository jobCategoryRepository;

    @Autowired
    public void setJobCategoryRepository(JobCategoryRepository jobCategoryRepository) {
        this.jobCategoryRepository = jobCategoryRepository;
    }

    /**
     * Find categories
     *
     * @param keyword
     * @param pageable
     * @param assembler
     *
     * @return
     */
    public PagedModel<EntityModel<JobCategory>> findAll(String keyword, Pageable pageable, PagedResourcesAssembler<JobCategory> assembler) {
        Page<JobCategory> page = jobCategoryRepository.findAll(pageable);

        PagedModel<EntityModel<JobCategory>> resources = assembler.toModel(page, linkTo(JobController.class).slash("/jobs/categories").withSelfRel());

        return resources;
    }

    /**
     * Save job category
     *
     * @param jobCategory
     *
     * @return
     */
    public JobCategory save(JobCategory jobCategory) {
        JobCategory persistedJobCategory = jobCategoryRepository.save(jobCategory);
        return findById(persistedJobCategory.getId());
    }

    /**
     * Find job category
     *
     * @param id
     *
     * @return
     */
    public JobCategory findById(long id) {
        return jobCategoryRepository.findById(id).get();
    }
}
