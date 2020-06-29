/*
 * Copyright (c) 2019. Nebula Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jz.nebula.controller.api;

import com.jz.nebula.entity.HomeBanner;
import com.jz.nebula.entity.Role;
import com.jz.nebula.service.HomeBannerService;
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
import java.util.List;

@RestController
@RequestMapping("/api/home-banners")
public class HomeBannerController {
    private HomeBannerService homeBannerService;

    @Autowired
    public void setHomeBannerService(HomeBannerService homeBannerService) {
        this.homeBannerService = homeBannerService;
    }

    /**
     * @param pageable
     * @param uriBuilder
     * @param response
     * @param assembler
     *
     * @return
     */
    @GetMapping
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    PagedModel<EntityModel<HomeBanner>> all(Pageable pageable, final UriComponentsBuilder uriBuilder,
                                            final HttpServletResponse response, PagedResourcesAssembler<HomeBanner> assembler) {
        return homeBannerService.findAll(pageable, assembler);
    }

    /**
     * [PUBLIC] This provide home banners for front end to use
     *
     * @return
     */
    @GetMapping("/active")
    public @ResponseBody
    List<HomeBanner> allActive() {
        return homeBannerService.findAllActive();
    }

    /**
     * @param id
     *
     * @return
     */
    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    HomeBanner findById(@PathVariable("id") long id) {
        return homeBannerService.findById(id);
    }

    /**
     * @param homeBanner
     *
     * @return
     */
    @PostMapping("")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    HomeBanner create(@RequestBody HomeBanner homeBanner) {
        return homeBannerService.save(homeBanner);
    }

    /**
     * @param id
     * @param homeBanner
     *
     * @return
     */
    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    HomeBanner update(@PathVariable("id") long id, @RequestBody HomeBanner homeBanner) {
        homeBanner.setId(id);
        return homeBannerService.save(homeBanner);
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
        homeBannerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
