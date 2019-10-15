package com.jz.nebula.controller;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jz.nebula.entity.Location;
import com.jz.nebula.entity.Role;
import com.jz.nebula.service.LocationService;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    /**
     * @param pageable
     * @param uriBuilder
     * @param response
     * @param assembler
     * @return
     */
    @GetMapping
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    PagedResources<Resource<Location>> all(Pageable pageable, final UriComponentsBuilder uriBuilder,
                                           final HttpServletResponse response, PagedResourcesAssembler<Location> assembler) {
        return locationService.findAll(pageable, assembler);
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Location findById(@PathVariable("id") long id) {
        return locationService.findById(id);
    }

    /**
     * @param location
     * @return
     */
    @PostMapping("")
    @RolesAllowed({Role.ROLE_ADMIN})
    public @ResponseBody
    Location create(@RequestBody Location location) {
        return locationService.save(location);
    }

    /**
     * @param id
     * @param location
     * @return
     */
    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_ADMIN})
    public @ResponseBody
    Location update(@PathVariable("id") long id, @RequestBody Location location) {
        location.setId(id);
        return locationService.save(location);
    }

    /**
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @RolesAllowed({Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> delete(@PathVariable("id") long id) {
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
