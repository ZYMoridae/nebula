package com.jz.nebula.controller;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.data.domain.Page;
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

import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.Shipper;
//import com.jz.nebula.hateoas.event.PaginatedResultsRetrievedEvent;
import com.jz.nebula.service.ShipperService;

//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/shippers")
public class ShipperController {
//  @Autowired
//  private ApplicationEventPublisher eventPublisher;
  
	@Autowired
	private ShipperService shipperService;
	
	@GetMapping
	@RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
	public @ResponseBody PagedResources<Resource<Shipper>> all(Pageable pageable, final UriComponentsBuilder uriBuilder,
      final HttpServletResponse response, PagedResourcesAssembler<Shipper> assembler) {	
//		eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<Shipper>(Shipper.class, uriBuilder, response,
//        pageable.getPageNumber(), page.getTotalPages(), pageable.getPageSize()));
		
		return shipperService.findAll(pageable, assembler);
	}	
	
	@GetMapping("/{id}")
	@RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
	public @ResponseBody Shipper findById(@PathVariable("id") long id) {
		return shipperService.findById(id);
	}
    
	@PostMapping("")
	@RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
	public @ResponseBody Shipper create(@RequestBody Shipper shipper) {
		return shipperService.save(shipper);
	}
    
	@PutMapping("/{id}")
	@RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
	public @ResponseBody Shipper update(@PathVariable("id") long id, @RequestBody Shipper shipper) {
		shipper.setId(id);
		return shipperService.save(shipper);
	}
    
	@DeleteMapping("/{id}")
	@RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") long id) {
		shipperService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
