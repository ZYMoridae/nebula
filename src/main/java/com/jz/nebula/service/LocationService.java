package com.jz.nebula.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.jz.nebula.controller.api.LocationController;
import com.jz.nebula.dao.LocationRepository;
import com.jz.nebula.entity.Location;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    /**
     * Get location by pagination
     *
     * @param pageable
     * @param assembler
     *
     * @return
     */
    public PagedModel<EntityModel<Location>> findAll(Pageable pageable, PagedResourcesAssembler<Location> assembler) {
        Page<Location> page = locationRepository.findAll(pageable);
        PagedModel<EntityModel<Location>> resources = assembler.toModel(page,
                linkTo(LocationController.class).slash("/locations").withSelfRel());
        ;
        return resources;
    }

    /**
     * Save location
     *
     * @param location
     *
     * @return
     */
    public Location save(Location location) {
        Location updatedLocation = locationRepository.save(location);
        return findById(updatedLocation.getId());
    }

    /**
     * Find location by id
     *
     * @param id
     *
     * @return
     */
    public Location findById(long id) {
        return locationRepository.findById(id).get();
    }

    /**
     * Delete location by id
     *
     * @param id
     */
    public void delete(long id) {
        locationRepository.deleteById(id);
    }
}
