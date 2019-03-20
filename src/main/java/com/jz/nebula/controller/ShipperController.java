package com.jz.nebula.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.Shipper;
import com.jz.nebula.repository.ShipperRepository;

@RestController
@RequestMapping("/shippers")
public class ShipperController {
    
	@Autowired
    private ShipperRepository shipperRepository;
    
	@GetMapping("")
	@RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
	public @ResponseBody Iterable<Shipper> allShipper() {
		return shipperRepository.findAll();
	}	
	
	@GetMapping("/{id}")
	@RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
	public @ResponseBody Shipper findById(@PathVariable("id") long id) {
		return shipperRepository.findById(id).get();
	}
    
	@PostMapping("")
	@RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
	public @ResponseBody Shipper newShipper(@RequestBody Shipper shipper) {
		return shipperRepository.save(shipper);
	}
    
	@PutMapping("/{id}")
	@RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
	public @ResponseBody Shipper updateShipper(@PathVariable("id") long id, @RequestBody Shipper shipper) {
		shipper.setId(id);
		return shipperRepository.save(shipper);
	}
    
	@DeleteMapping("/{id}")
	@RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
	public @ResponseBody ResponseEntity<?> deleteShipper(@PathVariable("id") long id) {
		shipperRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
