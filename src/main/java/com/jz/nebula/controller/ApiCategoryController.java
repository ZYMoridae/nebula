//package com.jz.nebula.controller;
//
//import com.jz.nebula.entity.Role;
//import com.jz.nebula.entity.api.ApiCategory;
//import com.jz.nebula.service.ApiCategoryService;
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
//@RequestMapping("/api-categories")
//public class ApiCategoryController {
//    @Autowired
//    private ApiCategoryService apiCategoryService;
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
//    PagedResources<Resource<ApiCategory>> all(Pageable pageable, final UriComponentsBuilder uriBuilder,
//                                              final HttpServletResponse response, PagedResourcesAssembler<ApiCategory> assembler) {
//        return apiCategoryService.findAll(pageable, assembler);
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
//    ApiCategory findById(@PathVariable("id") long id) {
//        return apiCategoryService.findById(id);
//    }
//
//    /**
//     *
//     * @param apiCategory
//     * @return
//     */
//    @PostMapping("")
//    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
//    public @ResponseBody
//    ApiCategory create(@RequestBody ApiCategory apiCategory) {
//        return apiCategoryService.save(apiCategory);
//    }
//
//    /**
//     *
//     * @param id
//     * @param apiCategory
//     * @return
//     */
//    @PutMapping("/{id}")
//    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
//    public @ResponseBody
//    ApiCategory update(@PathVariable("id") long id, @RequestBody ApiCategory apiCategory) {
//        apiCategory.setId(id);
//        return apiCategoryService.save(apiCategory);
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
//        apiCategoryService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//}
