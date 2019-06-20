package com.jz.nebula.controller;

import com.jz.nebula.auth.IAuthenticationFacade;
import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.entity.User;
import com.jz.nebula.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/users")
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

}
