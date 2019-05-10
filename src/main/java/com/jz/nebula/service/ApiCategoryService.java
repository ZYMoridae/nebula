//package com.jz.nebula.service;
//
//import com.jz.nebula.controller.ShipperController;
//import com.jz.nebula.dao.ApiCategoryRepository;
//import com.jz.nebula.entity.api.ApiCategory;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PagedResourcesAssembler;
//import org.springframework.hateoas.PagedResources;
//import org.springframework.hateoas.Resource;
//import org.springframework.stereotype.Service;
//
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
//
//@Service
//public class ApiCategoryService {
//    private final Logger logger = LogManager.getLogger(ApiInfoService.class);
//
//    @Autowired
//    private ApiCategoryRepository apiCategoryRepository;
//
//    public PagedResources<Resource<ApiCategory>> findAll(Pageable pageable, PagedResourcesAssembler<ApiCategory> assembler) {
//        Page<ApiCategory> page = apiCategoryRepository.findAll(pageable);
//        PagedResources<Resource<ApiCategory>> resources = assembler.toResource(page,
//                linkTo(ShipperController.class).slash("/api-categories").withSelfRel());
//
//        logger.info("API Categories page:[{}] returned", pageable.getPageNumber());
//
//        return resources;
//    }
//
//
//    public ApiCategory findById(long id) {
//        return apiCategoryRepository.findById(id).get();
//    }
//
//    public ApiCategory save(ApiCategory apiCategory) {
//        return apiCategoryRepository.save(apiCategory);
//    }
//
//    public void delete(long id) {
//        apiCategoryRepository.deleteById(id);
//    }
//}
