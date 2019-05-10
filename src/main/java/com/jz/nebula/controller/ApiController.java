//package com.jz.nebula.controller;
//
//import com.jz.nebula.entity.Role;
//import com.jz.nebula.entity.api.ApiInfo;
//import com.jz.nebula.service.ApiInfoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PagedResourcesAssembler;
//import org.springframework.hateoas.PagedResources;
//import org.springframework.hateoas.Resource;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import javax.annotation.security.RolesAllowed;
//import javax.servlet.http.HttpServletResponse;
//
//@RestController
//@RequestMapping("/api-infos")
//public class ApiController {
//    @Autowired
//    private ApiInfoService apiInfoService;
//
//    /**
//     *
//     * @param pageable
//     * @param uriBuilder
//     * @param response
//     * @param assembler
//     * @return
//     */
//    @GetMapping
//    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
//    public @ResponseBody
//    PagedResources<Resource<ApiInfo>> all(@RequestParam String categoryName, Pageable pageable, final UriComponentsBuilder uriBuilder,
//                                          final HttpServletResponse response, PagedResourcesAssembler<ApiInfo> assembler) {
//        return apiInfoService.findAll(categoryName, pageable, assembler);
//    }
//
//    /**
//     *
//     * @param id
//     * @return
//     */
//    @GetMapping("/{id}")
//    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
//    public @ResponseBody
//    ApiInfo findById(@PathVariable("id") long id) {
//        return apiInfoService.findById(id);
//    }
//
//    /**
//     *
//     * @param apiInfo
//     * @return
//     */
//    @PostMapping("")
//    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
//    public @ResponseBody
//    ApiInfo create(@RequestBody ApiInfo apiInfo) {
//        return apiInfoService.save(apiInfo);
//    }
//
//    /**
//     *
//     * @param id
//     * @param apiInfo
//     * @return
//     */
//    @PutMapping("/{id}")
//    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
//    public @ResponseBody
//    ApiInfo update(@PathVariable("id") long id, @RequestBody ApiInfo apiInfo) {
//        apiInfo.setId(id);
//        return apiInfoService.save(apiInfo);
//    }
//
//    /**
//     *
//     * @param id
//     * @return
//     */
//    @DeleteMapping("/{id}")
//    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
//    public @ResponseBody
//    ResponseEntity<?> delete(@PathVariable("id") long id) {
//        apiInfoService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//}
