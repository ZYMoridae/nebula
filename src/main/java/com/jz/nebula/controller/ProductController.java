package com.jz.nebula.controller;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jz.nebula.entity.Product;
import com.jz.nebula.entity.ProductComment;
import com.jz.nebula.entity.ProductRating;
import com.jz.nebula.entity.Role;
import com.jz.nebula.service.ProductCommentService;
import com.jz.nebula.service.ProductRatingService;
import com.jz.nebula.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService prouductService;

	@Autowired
	private ProductRatingService productRatingService;

	@Autowired
	private ProductCommentService productCommentService;

	@GetMapping
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody PagedResources<Resource<Product>> all(@RequestParam String keyword, Pageable pageable,
			final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
			PagedResourcesAssembler<Product> assembler) {
		return prouductService.findAll(keyword, pageable, assembler);
	}

	@GetMapping("/{id}")
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody Product findById(@PathVariable("id") long id) {
		return prouductService.findById(id);
	}

	@PostMapping("")
	@RolesAllowed({ Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody Product create(@RequestBody Product product) {
		return prouductService.save(product);
	}

	@PutMapping("/{id}")
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody Product update(@PathVariable("id") long id, @RequestBody Product product) {
		product.setId(id);
		return prouductService.save(product);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") long id) {
		prouductService.delete(id);
		return ResponseEntity.noContent().build();
	}

	// Product Rating
	@GetMapping("/{id}/ratings")
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody Object getProductRating(@PathVariable("id") long id) {
		return productRatingService.getRating(id);
	}

	@PostMapping("/{id}/ratings")
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody ProductRating createProductRating(@PathVariable("id") long id,
			@RequestBody ProductRating productRating) {
		productRating.setProductId(id);
		return productRatingService.save(productRating);
	}

	@PutMapping("/ratings/{product_rating_id}")
	@RolesAllowed({ Role.ROLE_ADMIN })
	public @ResponseBody ProductRating updateProductRating(@PathVariable("product_rating_id") long productRatingId,
			@RequestBody ProductRating productRating) {
		productRating.setId(productRatingId);
		return productRatingService.save(productRating);
	}

	@DeleteMapping("/ratings/{product_rating_id}")
	@RolesAllowed({ Role.ROLE_ADMIN })
	public @ResponseBody ResponseEntity<?> deleteProductRating(
			@PathVariable("product_rating_id") long productRatingId) {
		productRatingService.delete(productRatingId);
		return ResponseEntity.noContent().build();
	}

	// Product Comment
	@PostMapping("/{id}/comments")
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody ProductComment createProductComment(@PathVariable("id") long id,
			@RequestBody ProductComment productComment) {
		productComment.setProductId(id);
		return productCommentService.save(productComment);
	}

	@GetMapping("/{id}/comments")
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody Object getProductComment(@PathVariable("id") long id) {
		return productCommentService.findByProductIdAndParentCommentId(id);
	}

	@DeleteMapping("/{id}/comments/{comment_id}")
	@RolesAllowed({ Role.ROLE_ADMIN })
	public @ResponseBody ResponseEntity<?> deleteProductComment(@PathVariable("id") long id,
			@PathVariable("commentId") long commentId) {
		productCommentService.delete(commentId);
		return ResponseEntity.noContent().build();
	}

}
