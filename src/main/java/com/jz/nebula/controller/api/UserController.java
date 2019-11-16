package com.jz.nebula.controller.api;

import com.jz.nebula.auth.IAuthenticationFacade;
import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.User;
import com.jz.nebula.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private UserService userService;

    /**
     * @param userDetails
     * @return
     */
    @GetMapping("/me")
    public ResponseEntity<?> currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getCurrentUser(userDetails);
        return ok(user);
    }

    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_ADMIN})
    public User getUser(@PathVariable("id") long id) {
        return userService.findById(id);
    }

    /**
     * This is public route
     *
     * @param user
     * @return
     */
    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        // TODO: Move logic into user service
        Map userResultMap = userService.createUser(user);
        return ok(userResultMap);
    }

    /**
     * @param id
     * @param user
     * @return
     * @throws Exception
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) throws Exception {
        if (id != authenticationFacade.getUser().getId()) {
            throw new Exception();
        }
        return ok(userRepository.save(user));
    }

    /**
     * Get all users information by pagination
     *
     * @param keyword
     * @param pageable
     * @param uriBuilder
     * @param response
     * @param assembler
     * @return
     */
    @GetMapping
    @RolesAllowed({Role.ROLE_ADMIN})
    public @ResponseBody
    PagedResources<Resource<User>> all(@RequestParam String keyword, Pageable pageable,
                                       final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
                                       PagedResourcesAssembler<User> assembler) {
        return userService.findAll(keyword, pageable, assembler);
    }

    /**
     * Get all roles
     *
     * @param keyword
     * @param pageable
     * @param uriBuilder
     * @param response
     * @param assembler
     * @return
     */
    @GetMapping("/roles")
    @RolesAllowed({Role.ROLE_ADMIN})
    public @ResponseBody
    PagedResources<Resource<Role>> allRoles(@RequestParam String keyword, Pageable pageable,
                                            final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
                                            PagedResourcesAssembler<Role> assembler) {
        return userService.findAllRoles(keyword, pageable, assembler);
    }


}
