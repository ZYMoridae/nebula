package com.jz.nebula.controller;

import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.Shipper;
import com.jz.nebula.service.ShipperService;
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
@RequestMapping("/shippers")
public class ShipperController {
    @Autowired
    private ShipperService shipperService;

    @GetMapping
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    PagedResources<Resource<Shipper>> all(Pageable pageable, final UriComponentsBuilder uriBuilder,
                                          final HttpServletResponse response, PagedResourcesAssembler<Shipper> assembler) {
        return shipperService.findAll(pageable, assembler);
    }

    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Shipper findById(@PathVariable("id") long id) {
        return shipperService.findById(id);
    }

    @PostMapping("")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Shipper create(@RequestBody Shipper shipper) {
        return shipperService.save(shipper);
    }

    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Shipper update(@PathVariable("id") long id, @RequestBody Shipper shipper) {
        shipper.setId(id);
        return shipperService.save(shipper);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> delete(@PathVariable("id") long id) {
        shipperService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
