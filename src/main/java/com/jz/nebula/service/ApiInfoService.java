package com.jz.nebula.service;

import com.jz.nebula.controller.ShipperController;
import com.jz.nebula.dao.ApiInfoRepository;
import com.jz.nebula.entity.api.ApiInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Service
public class ApiInfoService {
    private final Logger logger = LogManager.getLogger(ApiInfoService.class);

    @Autowired
    private ApiInfoRepository apiInfoRepository;

    public PagedResources<Resource<ApiInfo>> findAll(Pageable pageable, PagedResourcesAssembler<ApiInfo> assembler) {
        Page<ApiInfo> page = apiInfoRepository.findAll(pageable);
        PagedResources<Resource<ApiInfo>> resources = assembler.toResource(page,
                linkTo(ShipperController.class).slash("/api-infos").withSelfRel());

        return resources;
    }

    public ApiInfo findById(long id) {
        return apiInfoRepository.findById(id).get();
    }

    public ApiInfo save(ApiInfo apiInfo) {
        ApiInfo updatedEntity = apiInfoRepository.save(apiInfo);
        return findById(updatedEntity.getId());
    }

    public void delete(long id) {
        apiInfoRepository.deleteById(id);
    }


}
