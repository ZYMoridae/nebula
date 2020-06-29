package com.jz.nebula.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.jz.nebula.controller.api.SwaggerConfigController;
import com.jz.nebula.dao.SwaggerConfigRepository;
import com.jz.nebula.entity.api.SwaggerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

@Service
public class SwaggerConfigService {

    @Autowired
    private SwaggerConfigRepository swaggerConfigRepository;

    public PagedModel<EntityModel<SwaggerConfig>> findAll(Pageable pageable, PagedResourcesAssembler<SwaggerConfig> assembler) {
        Page<SwaggerConfig> page = swaggerConfigRepository.findAll(pageable);
        PagedModel<EntityModel<SwaggerConfig>> resources = assembler.toModel(page,
                linkTo(SwaggerConfigController.class).slash("/swg-configs").withSelfRel());
        ;
        return resources;
    }

    public SwaggerConfig save(SwaggerConfig swaggerConfig) {
        return swaggerConfigRepository.save(swaggerConfig);
    }

    public SwaggerConfig findById(long id) {
        return swaggerConfigRepository.findById(id).get();
    }

    public void delete(long id) {
        swaggerConfigRepository.deleteById(id);
    }

}
