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

import com.jz.nebula.controller.ProductCategoryController;
import com.jz.nebula.dao.ProductCategoryRepository;
import com.jz.nebula.entity.ProductCategory;

@Service
@Component("productCategoryService")
public class ProductCategoryService {
	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	public PagedResources<Resource<ProductCategory>> findAll(Pageable pageable,
			PagedResourcesAssembler<ProductCategory> assembler) {
		Page<ProductCategory> page = productCategoryRepository.findAll(pageable);
		PagedResources<Resource<ProductCategory>> resources = assembler.toResource(page,
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
