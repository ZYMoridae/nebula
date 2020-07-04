package com.jz.nebula.service.edu;

import com.jz.nebula.controller.api.edu.JobController;
import com.jz.nebula.dao.edu.JobCategoryRepository;
import com.jz.nebula.dto.JobCategoryParam;
import com.jz.nebula.entity.edu.job.JobCategory;
import org.springframework.beans.BeanUtils;
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
     * Create job category
     *
     * @param jobCategoryParam
     *
     * @return
     */
    public JobCategory create(JobCategoryParam jobCategoryParam) {
        JobCategory jobCategory = new JobCategory();
        jobCategory.setName(jobCategoryParam.getName());
        jobCategory.setCode(jobCategoryParam.getCode());
        JobCategory persistedJobCategory = jobCategoryRepository.save(jobCategory);
        return findById(persistedJobCategory.getId());
    }

    /**
     * Delete by id
     *
     * @param id
     */
    public void delete(long id) {
        jobCategoryRepository.deleteById(id);
    }

    /**
     * Update job category
     *
     * @param id
     * @param jobCategoryParam
     */
    public JobCategory update(long id, JobCategoryParam jobCategoryParam) {
        JobCategory jobCategory = new JobCategory();
        jobCategory.setId(id);
        BeanUtils.copyProperties(jobCategoryParam, jobCategory);
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
