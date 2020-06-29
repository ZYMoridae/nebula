package com.jz.nebula.controller.api;

import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.api.SwaggerConfig;
import com.jz.nebula.service.SwaggerConfigService;
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


@RestController
@RequestMapping("/api/swg-configs")
public class SwaggerConfigController {
    @Autowired
    private SwaggerConfigService swaggerConfigService;

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
    PagedModel<EntityModel<SwaggerConfig>> all(Pageable pageable, final UriComponentsBuilder uriBuilder,
                                               final HttpServletResponse response, PagedResourcesAssembler<SwaggerConfig> assembler) {
        return swaggerConfigService.findAll(pageable, assembler);
    }

    /**
     * @param id
     *
     * @return
     */
    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    SwaggerConfig findById(@PathVariable("id") long id) {
        return swaggerConfigService.findById(id);
    }

    /**
     * @param swaggerConfig
     *
     * @return
     */
    @PostMapping("")
    @RolesAllowed({Role.ROLE_ADMIN})
    public @ResponseBody
    SwaggerConfig create(@RequestBody SwaggerConfig swaggerConfig) {
        return swaggerConfigService.save(swaggerConfig);
    }

    /**
     * @param id
     * @param swaggerConfig
     *
     * @return
     */
    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_ADMIN})
    public @ResponseBody
    SwaggerConfig update(@PathVariable("id") long id, @RequestBody SwaggerConfig swaggerConfig) {
        swaggerConfig.setId(id);
        return swaggerConfigService.save(swaggerConfig);
    }

    /**
     * @param id
     *
     * @return
     */
    @DeleteMapping("/{id}")
    @RolesAllowed({Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> delete(@PathVariable("id") long id) {
        swaggerConfigService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
