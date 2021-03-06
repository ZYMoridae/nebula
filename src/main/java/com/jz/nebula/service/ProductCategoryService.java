package com.jz.nebula.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.jz.nebula.controller.api.ProductCategoryController;
import com.jz.nebula.dao.ProductCategoryRepository;
import com.jz.nebula.entity.product.ProductCategory;

@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public PagedModel<EntityModel<ProductCategory>> findAll(String keyword, Pageable pageable,
                                                            PagedResourcesAssembler<ProductCategory> assembler) {

        Page<ProductCategory> page;
        if (Strings.isNullOrEmpty(keyword)) {
            page = productCategoryRepository.findAll(pageable);
        } else {
            page = productCategoryRepository.findByNameContaining(keyword, pageable);
        }

        PagedModel<EntityModel<ProductCategory>> resources = assembler.toModel(page,
                linkTo(ProductCategoryController.class).slash("/product-categories").withSelfRel());
        ;
        return resources;
    }

    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    public ProductCategory findById(long id) {
        return productCategoryRepository.findById(id).get();
    }

    public void delete(long id) {
        productCategoryRepository.deleteById(id);
    }
}
