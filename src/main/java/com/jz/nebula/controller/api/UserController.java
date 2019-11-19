package com.jz.nebula.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    public ResponseEntity<?> createUser(@RequestBody User user, @RequestParam Boolean isIncludeToken) {
        // TODO: Move logic into user service
        User persistedUser = userService.createUser(user);

        ObjectMapper oMapper = new ObjectMapper();
        Map userResultMap = oMapper.convertValue(persistedUser, Map.class);

        if (isIncludeToken == true) {
            Map<String, String> tokenMap = userService.getTokens(persistedUser, user.getRoles());
            userResultMap.put("token", tokenMap.get("accessToken"));
            userResultMap.put("refreshToken", tokenMap.get("refreshToken"));
        }

        return ok(userResultMap);
    }

    /**
     * @param id
     * @param user
     * @return
     * @throws Exception
     */
    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") long id, @RequestBody User user) throws Exception {
        if (id != authenticationFacade.getUser().getId()) {
            throw new Exception();
        }
        user.setId(id);
        return userService.save(user);
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
