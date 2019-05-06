package com.jz.nebula.service;

import com.jz.nebula.controller.ApiController;
import com.jz.nebula.controller.ShipperController;
import com.jz.nebula.dao.ApiCategoryRepository;
import com.jz.nebula.dao.ApiInfoRepository;
import com.jz.nebula.entity.api.ApiCategory;
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

import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Service
public class ApiInfoService {
    private final Logger logger = LogManager.getLogger(ApiInfoService.class);

    @Autowired
    private ApiInfoRepository apiInfoRepository;

    @Autowired
    private ApiCategoryRepository apiCategoryRepository;


    private Optional<ApiCategory> getCategoryByName(String name) {
        return apiCategoryRepository.findByName(name);
    }


    public PagedResources<Resource<ApiInfo>> findAll(String categoryName, Pageable pageable, PagedResourcesAssembler<ApiInfo> assembler) {
        Page<ApiInfo> page;
        Optional<ApiCategory> apiCategory = getCategoryByName(categoryName);
        if(apiCategory.isPresent()) {
            page = apiInfoRepository.findByApiCategoryId(apiCategory.get().getId(), pageable);
        }else {
            page = apiInfoRepository.findAll(pageable);
        }

        PagedResources<Resource<ApiInfo>> resources = assembler.toResource(page,
                linkTo(ApiController.class).slash("/api-infos").withSelfRel());

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
