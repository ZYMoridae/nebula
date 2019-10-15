package com.jz.nebula.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jz.nebula.dao.RoleRepository;
import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.dao.UserRolesRepository;
import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.User;
import com.jz.nebula.entity.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service("userDetailsService")
public class UserService implements UserDetailsService {
    private final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private TokenService jwtTokenProvider;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Load user from database by username
     *
     * @param username
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).get();
        UserBuilder builder;
        builder = org.springframework.security.core.userdetails.User.withUsername(username);
        builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
        String[] roleArray = user.getUserRoles().stream().map(userRole -> userRole.getRole().getCode()).collect(Collectors.toList()).toArray(new String[0]);
        builder.roles(roleArray);

        return builder.build();
    }

    /**
     * Create user
     *
     * @param user
     * @return
     */
    public Map createUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        ArrayList<Role> rolesList = new ArrayList<>();
        Role normalUser = roleRepository.findByCode(Role.ROLE_USER.replaceAll("ROLE_", "")).get();

        rolesList.add(normalUser);
        user.setRoles(rolesList);
        ObjectMapper oMapper = new ObjectMapper();
        User savedUser = userRepository.save(user);
        logger.debug("createUser::user saved");

        UserRole userRole = new UserRole();
        userRole.setRoleId(normalUser.getId());
        userRole.setUserId(savedUser.getId());
        userRolesRepository.save(userRole);
        logger.debug("createUser::user role saved");

        // Find user by id again to get user roles
        savedUser = userRepository.findById(savedUser.getId()).get();

        Map<String, Object> map = oMapper.convertValue(savedUser, Map.class);
        List<String> roles = new ArrayList<>();
        roles.add(Role.ROLE_USER);
        Map<String, String> tokenMap = jwtTokenProvider.createToken(savedUser.getUsername(), rolesList);
        map.put("token", tokenMap.get("accessToken"));
        map.put("refreshToken", tokenMap.get("refreshToken"));

        return tokenMap;
    }

    /**
     * Get current user through user details
     *
     * @param userDetails
     * @return
     */
    public User getCurrentUser(UserDetails userDetails) {
        Map<Object, Object> model = new HashMap<>();
        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities().stream().map(a -> a.getAuthority())
                .collect(toList()));

        User user = userRepository.findByUsername(userDetails.getUsername()).get();

        return user;
    }
}
