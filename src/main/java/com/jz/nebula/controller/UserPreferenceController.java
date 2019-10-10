package com.jz.nebula.controller;

import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.UserLogisticsPreference;
import com.jz.nebula.service.UserLogisticsPreferenceService;
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

@RestController
@RequestMapping("/preferences")
public class UserPreferenceController {
    @Autowired
    private UserLogisticsPreferenceService userLogisticsPreferenceService;

    /**
     *
     * @param pageable
     * @param uriBuilder
     * @param response
     * @param assembler
     * @return
     */
    @GetMapping("/logistics")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    PagedResources<Resource<UserLogisticsPreference>> all(Pageable pageable, final UriComponentsBuilder uriBuilder,
                                                          final HttpServletResponse response, PagedResourcesAssembler<UserLogisticsPreference> assembler) {
        return userLogisticsPreferenceService.findAll(pageable, assembler);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/logistics/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    UserLogisticsPreference findById(@PathVariable("id") long id) {
        return userLogisticsPreferenceService.findById(id);
    }

    /**
     *
     * @param userLogisticsPreference
     * @return
     */
    @PostMapping("/logistics")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    UserLogisticsPreference create(@RequestBody UserLogisticsPreference userLogisticsPreference) {
        return userLogisticsPreferenceService.save(userLogisticsPreference);
    }

    /**
     *
     * @param id
     * @param userLogisticsPreference
     * @return
     */
    @PutMapping("/logistics/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    UserLogisticsPreference update(@PathVariable("id") long id, @RequestBody UserLogisticsPreference userLogisticsPreference) {
        userLogisticsPreference.setId(id);
        return userLogisticsPreferenceService.save(userLogisticsPreference);
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/logistics/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> delete(@PathVariable("id") long id) {
        userLogisticsPreferenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
