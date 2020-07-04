package com.jz.nebula.service.edu;

import com.jz.nebula.controller.api.edu.JobController;
import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.dao.edu.JobCategoryRepository;
import com.jz.nebula.dao.edu.JobRepository;
import com.jz.nebula.dto.JobParam;
import com.jz.nebula.entity.User;
import com.jz.nebula.entity.edu.job.Job;
import com.jz.nebula.entity.edu.job.JobCategory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class JobService {

    private JobRepository jobRepository;

    private UserRepository userRepository;

    private JobCategoryRepository jobCategoryRepository;

    @Autowired
    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setJobCategoryRepository(JobCategoryRepository jobCategoryRepository) {
        this.jobCategoryRepository = jobCategoryRepository;
    }

    /**
     * Fetch jobs on pagination
     *
     * @param keyword
     * @param pageable
     * @param assembler
     *
     * @return
     */
    public PagedModel<EntityModel<Job>> findAll(String keyword, Pageable pageable, PagedResourcesAssembler<Job> assembler) {
        Page<Job> page = jobRepository.findAll(pageable);

        PagedModel<EntityModel<Job>> resources = assembler.toModel(page, linkTo(JobController.class).slash("/jobs").withSelfRel());

        return resources;
    }

    /**
     * Delete job by id
     *
     * @param id
     */
    public void delete(long id) {
        jobRepository.deleteById(id);
    }

    /**
     * Create job
     *
     * @param jobParam
     *
     * @return
     */
    public Job create(JobParam jobParam) {
        Job job = new Job();
        job.setDescription(jobParam.getDescription());
        job.setTitle(jobParam.getTitle());
        User user = userRepository.findById(jobParam.getUserId()).get();

        job.setUser(user);

        List<JobCategory> jobCategoryList = jobCategoryRepository.findByIdIn(jobParam.getJobCategoryIds());
        job.setJobCategorySet(new HashSet<>(jobCategoryList));

        // Remove first <p></p> tag from description
        if (job.getDescription().startsWith("<p>")) {
            job.setDescription(job.getDescription().replace("<p>", ""));
        }

        if (job.getDescription().endsWith("</p>")) {
            int lastIndex = job.getDescription().lastIndexOf("</p>");
            job.setDescription(job.getDescription().substring(0, lastIndex));
        }

        Job persistedJob = jobRepository.save(job);
        return findById(persistedJob.getId());
    }

    /**
     * Update job
     *
     * @param id
     * @param jobParam
     *
     * @return
     */
    public Job update(long id, JobParam jobParam) {
        Job job = new Job();
        job.setId(id);
        BeanUtils.copyProperties(jobParam, job);

        // Set user
        User user = userRepository.findById(jobParam.getUserId()).get();
        job.setUser(user);

        // Set job catgory
        List<JobCategory> jobCategoryList = jobCategoryRepository.findByIdIn(jobParam.getJobCategoryIds());
        job.setJobCategorySet(new HashSet<>(jobCategoryList));

        Job persistedJob = jobRepository.save(job);
        return findById(persistedJob.getId());
    }

    /**
     * Find job by id
     *
     * @param id
     *
     * @return
     */
    public Job findById(long id) {
        return jobRepository.findById(id).get();
    }
}
