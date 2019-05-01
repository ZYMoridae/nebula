package com.jz.nebula.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jz.nebula.auth.AuthenticationFacade;
import com.jz.nebula.controller.ProductController;
import com.jz.nebula.dao.ProductCommentRepository;
import com.jz.nebula.entity.ProductComment;

@Service
public class ProductCommentService {
    private final Logger logger = LogManager.getLogger(ProductCommentService.class);

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
        if (productComment.getParentCommentId() == null) {
            productComment.setParentCommentId((long) 0);
        }
        ProductComment savedComment = productCommentRepository.save(productComment);
        logger.info("Comment with id:[{}] was saved", savedComment.getId());
        return savedComment;
    }

    public ProductComment findById(long id) {
        return productCommentRepository.findById(id).get();
    }

    public PagedResources<Resource<ProductComment>> findByProductIdAndParentCommentId(long productId, Pageable pageable,
                                                                                      PagedResourcesAssembler<ProductComment> assembler) {
        Page<ProductComment> page = productCommentRepository.findByProductIdAndParentCommentId(productId, 0, pageable);
        PagedResources<Resource<ProductComment>> resources = assembler.toResource(page,
                linkTo(ProductController.class).slash("/" + productId + "/comments").withSelfRel());
        return resources;
    }

    public void delete(long id) {
        productCommentRepository.deleteById(id);
        logger.info("Comment with id:[{}] was deleted", id);
    }
}
