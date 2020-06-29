package com.jz.nebula.controller.api.edu;

import com.jz.nebula.entity.JobCategory;
import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.edu.Clazz;
import com.jz.nebula.entity.job.Job;
import com.jz.nebula.service.edu.JobCategoryService;
import com.jz.nebula.service.edu.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private JobService jobService;

    private JobCategoryService jobCategoryService;

    @Autowired
    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }

    @Autowired
    public void setJobCategoryService(JobCategoryService jobCategoryService) {
        this.jobCategoryService = jobCategoryService;
    }

    @GetMapping
    public @ResponseBody
    PagedModel<EntityModel<Job>> all(@RequestParam String keyword, Pageable pageable,
                                     final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
                                     PagedResourcesAssembler<Job> assembler) {
        return jobService.findAll(keyword, pageable, assembler);
    }


    @PostMapping
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_USER, Role.ROLE_TEACHER, Role.ROLE_VENDOR})
    public @ResponseBody
    Job create(@RequestBody Job job) {
        return jobService.save(job);
    }


    /**
     * Find job info according to id.
     *
     * @param id
     *
     * @return
     */
    @GetMapping("/{id}")
    public @ResponseBody
    Job findById(@PathVariable("id") long id) {
        return jobService.findById(id);
    }

    /**
     * Get all categories
     *
     * @param keyword
     * @param pageable
     * @param uriBuilder
     * @param response
     * @param assembler
     *
     * @return
     */
    @GetMapping("/categories")
    public @ResponseBody
    PagedModel<EntityModel<JobCategory>> getCategories(@RequestParam String keyword, Pageable pageable,
                                                       final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
                                                       PagedResourcesAssembler<JobCategory> assembler) {
        return jobCategoryService.findAll(keyword, pageable, assembler);
    }
}
