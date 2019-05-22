package com.jz.nebula.controller;

import com.jz.nebula.entity.HomeBanner;
import com.jz.nebula.entity.Role;
import com.jz.nebula.service.HomeBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/home-banners")
public class HomeBannerController {
    @Autowired
    private HomeBannerService homeBannerService;

    /**
     *
     * @param pageable
     * @param uriBuilder
     * @param response
     * @param assembler
     * @return
     */
    @GetMapping
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    PagedResources<Resource<HomeBanner>> all(Pageable pageable, final UriComponentsBuilder uriBuilder,
                                             final HttpServletResponse response, PagedResourcesAssembler<HomeBanner> assembler) {
        return homeBannerService.findAll(pageable, assembler);
    }

    /**
     * [PUBLIC] This provide home banners for front end to use
     *
     * @return
     */
    @GetMapping("/active")
    public @ResponseBody
    List<HomeBanner> allActive() {
        return homeBannerService.findAllActive();
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    HomeBanner findById(@PathVariable("id") long id) {
        return homeBannerService.findById(id);
    }

    /**
     *
     * @param homeBanner
     * @return
     */
    @PostMapping("")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    HomeBanner create(@RequestBody HomeBanner homeBanner) {
        return homeBannerService.save(homeBanner);
    }

    /**
     *
     * @param id
     * @param homeBanner
     * @return
     */
    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    HomeBanner update(@PathVariable("id") long id, @RequestBody HomeBanner homeBanner) {
        homeBanner.setId(id);
        return homeBannerService.save(homeBanner);
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> delete(@PathVariable("id") long id) {
        homeBannerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
