/*
 * Copyright (c) 2019. Nebula Technology
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.jz.nebula.controller.api;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.jz.nebula.entity.product.Product;
import com.jz.nebula.entity.product.ProductComment;
import com.jz.nebula.entity.product.ProductRating;
import com.jz.nebula.entity.Role;
import com.jz.nebula.component.exception.InvalidEntityException;
import com.jz.nebula.service.ProductCommentService;
import com.jz.nebula.service.ProductRatingService;
import com.jz.nebula.service.ProductService;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRatingService productRatingService;

    @Autowired
    private ProductCommentService productCommentService;

    /**
     * Get all products in pagination. This API endpoint should be exposed to public.
     * TODO: Is it secure enough?
     *
     * @param keyword
     * @param pageable
     * @param uriBuilder
     * @param response
     * @param assembler
     *
     * @return
     */
    @GetMapping
    public @ResponseBody
    PagedModel<EntityModel<Product>> all(@RequestParam String keyword, Pageable pageable,
                                         final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
                                         PagedResourcesAssembler<Product> assembler) {
        return productService.findAll(keyword, pageable, assembler);
    }

    /**
     * TODO: Need to be optimized. Currently, this page mainly for payment page (PaymentOrder component).
     *
     * @param params
     *
     * @return
     */
    @PostMapping("/ids")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    List<Product> findByIds(@RequestBody HashMap params) {
        System.out.println(params.get("ids"));
        List _ids = (ArrayList) params.get("ids");

        _ids = (ArrayList) _ids.stream().map(item -> Long.valueOf(String.valueOf(item))).collect(Collectors.toList());

        return productService.findByIds(_ids);
    }

    /**
     * Get product info according to id. This endpoint should be exposed to public.
     * TODO: Is it secure enough?
     *
     * @param id
     *
     * @return
     */
    @GetMapping("/{id}")
    public @ResponseBody
    Product findById(@PathVariable("id") long id) {
        return productService.findById(id);
    }

    /**
     * Two steps service call. The first save call is for transactional database action. The second call is to retrieve
     * data
     *
     * @param product
     *
     * @return
     */
    @PostMapping("")
    @RolesAllowed({Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Product create(@RequestBody Product product) throws InvalidEntityException {
        Product savedProduct = productService.save(product);
        if (savedProduct == null) {
            throw new InvalidEntityException("Invalid product");
        }
        return productService.findById(savedProduct.getId());
    }

    /**
     * @param id
     * @param product
     *
     * @return
     */
    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Product update(@PathVariable("id") long id, @RequestBody Product product) {
        product.setId(id);
        Product updatedProduct = productService.save(product);
        return productService.findById(updatedProduct.getId());
    }

    /**
     * @param id
     *
     * @return
     */
    @DeleteMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> delete(@PathVariable("id") long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ------ Product Rating ------

    /**
     * @param id
     *
     * @return
     */
    @GetMapping("/{id}/ratings")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Object getProductRating(@PathVariable("id") long id) {
        return productRatingService.getRating(id);
    }

    /**
     * @param id
     * @param productRating
     *
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
     * @param productRatingId
     * @param productRating
     *
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
     * @param productRatingId
     *
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

    // ------ Product Comment ------

    /**
     * @param id
     * @param productComment
     *
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
     * @param id
     * @param pageable
     * @param uriBuilder
     * @param response
     * @param assembler
     *
     * @return
     */
    @GetMapping("/{id}/comments")
    public @ResponseBody
    PagedModel<EntityModel<ProductComment>> getProductComment(@PathVariable("id") long id,
                                                              Pageable pageable, final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
                                                              PagedResourcesAssembler<ProductComment> assembler) {
        return productCommentService.findByProductIdAndParentCommentId(id, pageable, assembler);
    }

    /**
     * @param id
     * @param commentId
     *
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
     * @return
     *
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
