package com.jz.nebula.controller;

import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.UserPreference;
import com.jz.nebula.service.UserPreferenceService;
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

public class UserPreferenceController {
    @Autowired
    private UserPreferenceService userPreferenceService;

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
    PagedResources<Resource<UserPreference>> all(Pageable pageable, final UriComponentsBuilder uriBuilder,
                                                 final HttpServletResponse response, PagedResourcesAssembler<UserPreference> assembler) {
        return userPreferenceService.findAll(pageable, assembler);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    UserPreference findById(@PathVariable("id") long id) {
        return userPreferenceService.findById(id);
    }

    /**
     *
     * @param userPreference
     * @return
     */
    @PostMapping("")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    UserPreference create(@RequestBody UserPreference userPreference) {
        return userPreferenceService.save(userPreference);
    }

    /**
     *
     * @param id
     * @param userPreference
     * @return
     */
    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    UserPreference update(@PathVariable("id") long id, @RequestBody UserPreference userPreference) {
        userPreference.setId(id);
        return userPreferenceService.save(userPreference);
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
        userPreferenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
