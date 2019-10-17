package com.jz.nebula.controller.api;

import com.jz.nebula.entity.product.ProductCategory;
import com.jz.nebula.entity.Role;
import com.jz.nebula.service.ProductCategoryService;
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
@RequestMapping("/api/product-categories")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

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
    PagedResources<Resource<ProductCategory>> all(Pageable pageable,
                                                  final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
                                                  PagedResourcesAssembler<ProductCategory> assembler) {
        return productCategoryService.findAll(pageable, assembler);
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ProductCategory findById(@PathVariable("id") long id) {
        return productCategoryService.findById(id);
    }

    /**
     * @param productCategory
     * @return
     */
    @PostMapping("")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ProductCategory create(@RequestBody ProductCategory productCategory) {
        return productCategoryService.save(productCategory);
    }

    /**
     * @param id
     * @param productCategory
     * @return
     */
    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ProductCategory update(@PathVariable("id") long id,
                           @RequestBody ProductCategory productCategory) {
        productCategory.setId(id);
        return productCategoryService.save(productCategory);
    }

    /**
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> delete(@PathVariable("id") long id) {
        productCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
