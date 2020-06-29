package com.jz.nebula.service.edu;

import com.jz.nebula.controller.api.edu.JobController;
import com.jz.nebula.dao.edu.JobRepository;
import com.jz.nebula.entity.edu.Clazz;
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
public class JobService {

    private JobRepository jobRepository;

    @Autowired
    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public PagedModel<EntityModel<Job>> findAll(String keyword, Pageable pageable, PagedResourcesAssembler<Job> assembler) {
        Page<Job> page = jobRepository.findAll(pageable);

        PagedModel<EntityModel<Job>> resources = assembler.toModel(page, linkTo(JobController.class).slash("/jobs").withSelfRel());

        return resources;
    }

    /**
     * Save job
     *
     * @param job
     *
     * @return
     */
    public Job save(Job job) {
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
