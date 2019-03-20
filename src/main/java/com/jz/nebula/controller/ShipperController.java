package com.jz.nebula.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jz.nebula.entity.Shipper;
import com.jz.nebula.repository.ShipperRepository;

@RestController
@RequestMapping("/shippers")
public class ShipperController {
    
		@Autowired
    private ShipperRepository shipperRepository;
    
    @GetMapping("/{id}")
    @RolesAllowed({"ROLE_USER"})
    public @ResponseBody Shipper findById(@PathVariable("id") long id) {
        return shipperRepository.findById(id).get();
    }
    
    @PostMapping("")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public @ResponseBody Shipper newShipper(@RequestBody Shipper shipper) {
    	return shipperRepository.save(shipper);
    }
    
    
}
