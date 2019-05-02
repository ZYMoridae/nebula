package com.jz.nebula.controller;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.jz.nebula.entity.Product;
import com.jz.nebula.entity.ProductComment;
import com.jz.nebula.entity.ProductRating;
import com.jz.nebula.entity.Role;
import com.jz.nebula.service.ProductCommentService;
import com.jz.nebula.service.ProductRatingService;
import com.jz.nebula.service.ProductService;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService prouductService;

    @Autowired
    private ProductRatingService productRatingService;

    @Autowired
    private ProductCommentService productCommentService;

    /**
     *
     * @param keyword
     * @param pageable
     * @param uriBuilder
     * @param response
     * @param assembler
     * @return
     */
    @GetMapping
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    PagedResources<Resource<Product>> all(@RequestParam String keyword, Pageable pageable,
                                          final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
                                          PagedResourcesAssembler<Product> assembler) {
        return prouductService.findAll(keyword, pageable, assembler);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Product findById(@PathVariable("id") long id) {
        return prouductService.findById(id);
    }

    /**
     *
     * @param product
     * @return
     */
    @PostMapping("")
    @RolesAllowed({Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Product create(@RequestBody Product product) {
        return prouductService.save(product);
    }

    /**
     *
     * @param id
     * @param product
     * @return
     */
    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Product update(@PathVariable("id") long id, @RequestBody Product product) {
        product.setId(id);
        return prouductService.save(product);
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> delete(@PathVariable("id") long id) {
        prouductService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //--------- Product Rating ----------

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/ratings")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Object getProductRating(@PathVariable("id") long id) {
        return productRatingService.getRating(id);
    }

    /**
     *
     * @param id
     * @param productRating
     * @return
     */
    @PostMapping("/{id}/ratings")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ProductRating createProductRating(@PathVariable("id") long id,
                                      @RequestBody ProductRating productRating) {
        productRating.setProductId(id);
        return productRatingService.save(productRating);
    }

    /**
     *
     * @param productRatingId
     * @param productRating
     * @return
     */
    @PutMapping("/ratings/{product_rating_id}")
    @RolesAllowed({Role.ROLE_ADMIN})
    public @ResponseBody
    ProductRating updateProductRating(@PathVariable("product_rating_id") long productRatingId,
                                      @RequestBody ProductRating productRating) {
        productRating.setId(productRatingId);
        return productRatingService.save(productRating);
    }

    /**
     *
     * @param productRatingId
     * @return
     */
    @DeleteMapping("/ratings/{product_rating_id}")
    @RolesAllowed({Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> deleteProductRating(
            @PathVariable("product_rating_id") long productRatingId) {
        productRatingService.delete(productRatingId);
        return ResponseEntity.noContent().build();
    }

    //----- Product Comment -----

    /**
     *
     * @param id
     * @param productComment
     * @return
     */
    @PostMapping("/{id}/comments")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ProductComment createProductComment(@PathVariable("id") long id,
                                        @RequestBody ProductComment productComment) {
        productComment.setProductId(id);
        return productCommentService.save(productComment);
    }

    /**
     *
     * @param id
     * @param pageable
     * @param uriBuilder
     * @param response
     * @param assembler
     * @return
     */
    @GetMapping("/{id}/comments")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    PagedResources<Resource<ProductComment>> getProductComment(@PathVariable("id") long id,
                                                               Pageable pageable, final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
                                                               PagedResourcesAssembler<ProductComment> assembler) {
        return productCommentService.findByProductIdAndParentCommentId(id, pageable, assembler);
    }

    /**
     *
     * @param id
     * @param commentId
     * @return
     */
    @DeleteMapping("/{id}/comments/{comment_id}")
    @RolesAllowed({Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> deleteProductComment(@PathVariable("id") long id,
                                           @PathVariable("commentId") long commentId) {
        productCommentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }

    // This is for streaming JSON test

    /**
     *
     * @return
     * @throws IOException
     */
    @PostMapping("/streaming")
    public @ResponseBody
    Object streamingTest() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory
                .createGenerator(stream, JsonEncoding.UTF8);

        try {
            jGenerator.writeStartObject();
            jGenerator.writeStringField("name", "Tom");
            jGenerator.writeNumberField("age", 25);
            jGenerator.writeFieldName("address");
            jGenerator.writeStartArray();
            jGenerator.writeString("Poland");
            jGenerator.writeString("5th avenue");
            jGenerator.writeEndArray();
            jGenerator.writeEndObject();
            jGenerator.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return stream.toByteArray();
    }
}
