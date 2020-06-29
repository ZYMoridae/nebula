package com.jz.nebula.controller.api;

import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.sku.Sku;
import com.jz.nebula.entity.sku.SkuAttributeCategory;
import com.jz.nebula.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/skus")
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
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Sku findById(@PathVariable("id") long id) {
        return skuService.find(id);
    }

    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Sku update(@PathVariable("id") long id, @RequestBody Sku sku) {
        sku.setId(id);
        return skuService.update(sku);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> delete(@PathVariable("id") long id) {
        skuService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Sku Attribute Categories


    @GetMapping("/attributes/categories")
    @RolesAllowed({Role.ROLE_VENDOR, Role.ROLE_ADMIN, Role.ROLE_USER})
    public @ResponseBody
    PagedModel<EntityModel<SkuAttributeCategory>> all(@RequestParam String keyword, Pageable pageable,
                                                      final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
                                                      PagedResourcesAssembler<SkuAttributeCategory> assembler) {
        return skuService.findAllSkuAttributeCategory(keyword, pageable, assembler);
    }
}
