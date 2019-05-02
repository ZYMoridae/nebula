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

import com.jz.nebula.controller.ProductController;
import com.jz.nebula.dao.ProductRepository;
import com.jz.nebula.entity.Product;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public PagedResources<Resource<Product>> findAll(String keyword, Pageable pageable,
                                                     PagedResourcesAssembler<Product> assembler) {
        Page<Product> page;
        if (keyword == "") {
            page = productRepository.findAll(pageable);
        } else {
            page = productRepository.findByNameContaining(keyword, pageable);
        }

        PagedResources<Resource<Product>> resources = assembler.toResource(page,
                linkTo(ProductController.class).slash("/products").withSelfRel());
        ;
        return resources;
    }

    public Product save(Product product) {
        Product updatedProduct = productRepository.save(product);

        return findById(updatedProduct.getId());
    }

    public Product findById(long id) {
        return productRepository.findById(id).get();
    }

    public void delete(long id) {
        productRepository.deleteById(id);
    }
}
