package com.jz.nebula.controller.api.edu;

import com.jz.nebula.dto.JobParam;
import com.jz.nebula.entity.edu.job.JobCategory;
import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.edu.job.Job;
import com.jz.nebula.service.edu.JobCategoryService;
import com.jz.nebula.service.edu.JobService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

@RestController
//@Api(tags = "JobController")
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

    //    @ApiOperation("Get job based on pagination")
    @GetMapping
    public PagedModel<EntityModel<Job>> all(@RequestParam String keyword, Pageable pageable,
                                            PagedResourcesAssembler<Job> assembler) {
        return jobService.findAll(keyword, pageable, assembler);
    }

    //    @ApiOperation("Create job")
    @PostMapping
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_USER, Role.ROLE_TEACHER, Role.ROLE_VENDOR})
    public @ResponseBody
    Job create(@Validated @RequestBody JobParam jobParam) {
        return jobService.create(jobParam);
    }

    //    @ApiOperation("Update job")
    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_USER, Role.ROLE_TEACHER, Role.ROLE_VENDOR})
    public @ResponseBody
    Job create(@PathVariable("id") long id, @Validated @RequestBody JobParam jobParam) {
        return jobService.update(id, jobParam);
    }

    //    @ApiOperation("Delete job")
    @DeleteMapping("/{id}")
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_USER, Role.ROLE_TEACHER, Role.ROLE_VENDOR})
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        jobService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Find job info according to id.
     *
     * @param id
     *
     * @return
     */
//    @ApiOperation("Delete job by id")
    @GetMapping("/{id}")
    public Job findById(@PathVariable("id") long id) {
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
//    @ApiOperation("Get all job categories")
    @GetMapping("/categories")
    public PagedModel<EntityModel<JobCategory>> getCategories(@RequestParam String keyword, Pageable pageable,
                                                              final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
                                                              PagedResourcesAssembler<JobCategory> assembler) {
        return jobCategoryService.findAll(keyword, pageable, assembler);
    }
}
