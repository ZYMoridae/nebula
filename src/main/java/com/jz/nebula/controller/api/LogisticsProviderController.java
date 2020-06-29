package com.jz.nebula.controller.api;

import com.jz.nebula.entity.LogisticsProvider;
import com.jz.nebula.entity.Role;
import com.jz.nebula.service.LogisticsProviderService;
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
@RequestMapping("/api/logistics-provider")
public class LogisticsProviderController {
    private LogisticsProviderService logisticsProviderService;

    @Autowired
    public void setLogisticsProviderService(LogisticsProviderService logisticsProviderService) {
        this.logisticsProviderService = logisticsProviderService;
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
    PagedModel<EntityModel<LogisticsProvider>> all(Pageable pageable, final UriComponentsBuilder uriBuilder,
                                                   final HttpServletResponse response, PagedResourcesAssembler<LogisticsProvider> assembler) {
        return logisticsProviderService.findAll(pageable, assembler);
    }

    /**
     * @param id
     *
     * @return
     */
    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    LogisticsProvider findById(@PathVariable("id") long id) {
        return logisticsProviderService.findById(id);
    }

    /**
     * @param logisticsProvider
     *
     * @return
     */
    @PostMapping("")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    LogisticsProvider create(@RequestBody LogisticsProvider logisticsProvider) {
        return logisticsProviderService.save(logisticsProvider);
    }

    /**
     * @param id
     * @param logisticsProvider
     *
     * @return
     */
    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    LogisticsProvider update(@PathVariable("id") long id, @RequestBody LogisticsProvider logisticsProvider) {
        logisticsProvider.setId(id);
        return logisticsProviderService.save(logisticsProvider);
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
        logisticsProviderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
