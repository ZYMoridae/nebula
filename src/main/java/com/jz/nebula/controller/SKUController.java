package com.jz.nebula.controller;

import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.sku.Sku;
import com.jz.nebula.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/skus")
public class SKUController {

    @Autowired
    private SkuService skuService;

    @PostMapping("")
    @RolesAllowed({Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Sku create(@RequestBody Sku sku) {
        return skuService.create(sku);
    }

    @GetMapping("/{id}")
    public @ResponseBody
    Sku findById(@PathVariable("id") long id) {
        return skuService.find(id);
    }

    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Sku update(@PathVariable("id") long id, @RequestBody Sku sku) {
        sku.setId(id);
        return skuService.update(sku);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> delete(@PathVariable("id") long id) {
        skuService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
