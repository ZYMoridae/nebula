package com.jz.nebula.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jz.nebula.controller.LocationController;
import com.jz.nebula.dao.LocationRepository;
import com.jz.nebula.entity.Location;

@Service
@Component("locationService")
public class LocationService {
	@Autowired
	private LocationRepository locationRepository;
	
	public PagedResources<Resource<Location>> findAll(Pageable pageable, PagedResourcesAssembler<Location> assembler) {
		Page<Location> page = locationRepository.findAll(pageable);
		PagedResources<Resource<Location>> resources = assembler.toResource(page,
				linkTo(LocationController.class).slash("/locations").withSelfRel());
		;
		return resources;
	}

	public Location save(Location location) {
		Location updatedLocation = locationRepository.save(location);
		return findById(updatedLocation.getId());
	}

	public Location findById(long id) {
		return locationRepository.findById(id).get();
	}

	public void delete(long id) {
		locationRepository.deleteById(id);
	}
}
