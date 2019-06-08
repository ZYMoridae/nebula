package com.jz.nebula.controller;

import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.UserShippingPreference;
import com.jz.nebula.service.UserShippingPreferenceService;
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
    private UserShippingPreferenceService userShippingPreferenceService;

    /**
     *
     * @param pageable
     * @param uriBuilder
     * @param response
     * @param assembler
     * @return
     */
    @GetMapping("/shippings")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    PagedResources<Resource<UserShippingPreference>> all(Pageable pageable, final UriComponentsBuilder uriBuilder,
                                                         final HttpServletResponse response, PagedResourcesAssembler<UserShippingPreference> assembler) {
        return userShippingPreferenceService.findAll(pageable, assembler);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/shippings/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    UserShippingPreference findById(@PathVariable("id") long id) {
        return userShippingPreferenceService.findById(id);
    }

    /**
     *
     * @param userShippingPreference
     * @return
     */
    @PostMapping("/shippings")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    UserShippingPreference create(@RequestBody UserShippingPreference userShippingPreference) {
        return userShippingPreferenceService.save(userShippingPreference);
    }

    /**
     *
     * @param id
     * @param userShippingPreference
     * @return
     */
    @PutMapping("/shippings/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    UserShippingPreference update(@PathVariable("id") long id, @RequestBody UserShippingPreference userShippingPreference) {
        userShippingPreference.setId(id);
        return userShippingPreferenceService.save(userShippingPreference);
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/shippings/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> delete(@PathVariable("id") long id) {
        userShippingPreferenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
