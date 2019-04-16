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

import com.jz.nebula.auth.AuthenticationFacade;
import com.jz.nebula.controller.ProductCategoryController;
import com.jz.nebula.dao.ProductCategoryRepository;
import com.jz.nebula.dao.ProductCommentRepository;
import com.jz.nebula.entity.ProductCategory;
import com.jz.nebula.entity.ProductComment;

@Service
@Component("productCommentService")
public class ProductCommentService {
	@Autowired
	private ProductCommentRepository productCommentRepository;
	
	@Autowired
	private AuthenticationFacade authenticationFacade;
	
//	public PagedResources<Resource<ProductComment>> findAll(Pageable pageable,
//			PagedResourcesAssembler<ProductComment> assembler) {
//		Page<ProductComment> page = productCommentRepository.findAll(pageable);
//		PagedResources<Resource<ProductComment>> resources = assembler.toResource(page,
//				linkTo(ProductCommentController.class).slash("/product-comments").withSelfRel());
//		;
//		return resources;
//	}

	public ProductComment save(ProductComment productComment) {
		productComment.setUsertId(authenticationFacade.getUser().getId());
		if(productComment.getParentCommentId() == null) {
			productComment.setParentCommentId((long) 0);
		}
		return productCommentRepository.save(productComment);
	}

	public ProductComment findById(long id) {
		return productCommentRepository.findById(id).get();
	}
	
	public ProductComment findByProductIdAndParentCommentId(long productId) {
		return productCommentRepository.findByProductIdAndParentCommentId(productId, 0).get();
	}
	
	public void delete(long id) {
		productCommentRepository.deleteById(id);
	}
}
