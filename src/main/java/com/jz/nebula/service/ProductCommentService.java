package com.jz.nebula.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.jz.nebula.util.auth.AuthenticationFacadeImpl;
import com.jz.nebula.controller.api.ProductController;
import com.jz.nebula.dao.ProductCommentRepository;
import com.jz.nebula.entity.product.ProductComment;

@Service
public class ProductCommentService {
    private final Logger logger = LogManager.getLogger(ProductCommentService.class);

    @Autowired
    private ProductCommentRepository productCommentRepository;

    @Autowired
    private AuthenticationFacadeImpl authenticationFacadeImpl;

//	public PagedResources<Resource<ProductComment>> findAll(Pageable pageable,
//			PagedResourcesAssembler<ProductComment> assembler) {
//		Page<ProductComment> page = productCommentRepository.findAll(pageable);
//		PagedResources<Resource<ProductComment>> resources = assembler.toResource(page,
//				linkTo(ProductCommentController.class).slash("/product-comments").withSelfRel());
//		;
//		return resources;
//	}

    public ProductComment save(ProductComment productComment) {
        productComment.setUsertId(authenticationFacadeImpl.getUser().getId());
        if (productComment.getParentCommentId() == null) {
            productComment.setParentCommentId((long) 0);
        }
        ProductComment savedComment = productCommentRepository.save(productComment);
        logger.info("saveComment::Comment with id:[{}] was saved", savedComment.getId());
        return savedComment;
    }

    public ProductComment findById(long id) {
        return productCommentRepository.findById(id).get();
    }

    public PagedModel<EntityModel<ProductComment>> findByProductIdAndParentCommentId(long productId, Pageable pageable,
                                                                                     PagedResourcesAssembler<ProductComment> assembler) {
        Page<ProductComment> page = productCommentRepository.findByProductIdAndParentCommentId(productId, 0, pageable);
        PagedModel<EntityModel<ProductComment>> resources = assembler.toModel(page,
                linkTo(ProductController.class).slash("/" + productId + "/comments").withSelfRel());
        return resources;
    }

    public void delete(long id) {
        productCommentRepository.deleteById(id);
        logger.info("deleteComment::Comment with id:[{}] was deleted", id);
    }
}
