package com.jz.nebula.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.jz.nebula.controller.api.UserController;
import com.jz.nebula.dao.RoleRepository;
import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.dao.UserRolesRepository;
import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.User;
import com.jz.nebula.entity.UserRole;
import com.jz.nebula.util.pagination.CmsPagination;
import com.jz.nebula.util.pagination.CmsPaginationHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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
        builder.password(user.getPassword());
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

    /**
     * TODO: Need to be optimized
     *
     * @param pageable
     * @return
     */
    public CmsPagination findAll(Pageable pageable) {
        CmsPaginationHelper<User> userCmsPaginationHelper = new CmsPaginationHelper<>();
        return userCmsPaginationHelper.getCmsPagination(pageable, userRepository.findAllByOrderByIdAsc(pageable), "/cms/user");
    }

    /**
     * Get all by pagination
     *
     * @param keyword
     * @param pageable
     * @param assembler
     * @return
     */
    public PagedResources<Resource<User>> findAll(String keyword, Pageable pageable,
                                                  PagedResourcesAssembler<User> assembler) {
        Page<User> page;
        if (Strings.isNullOrEmpty(keyword)) {
            page = userRepository.findAllByOrderByIdAsc(pageable);
        } else {
            page = userRepository.findByNameContaining(keyword, pageable);
        }

        PagedResources<Resource<User>> resources = assembler.toResource(page,
                linkTo(UserController.class).slash("/users").withSelfRel());

        return resources;
    }

    /**
     * Find user by id
     *
     * @param id
     * @return
     */
    public User findById(long id) {
        User user = null;
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            user.setRoles(user.getUserRoles().stream().map(userRole -> userRole.getRole()).collect(Collectors.toList()));
        }

        return user;
    }

    /**
     * Save user
     *
     * @param user
     * @return
     */
    public User save(User user) {
        logger.debug("save:: id [{}]", user.getId());
        userRepository.save(user);
        return findById(user.getId());
    }

    /**
     * FIXME: Can be improved
     * Update user role
     *
     * @param persistedUser
     * @param updateRoles
     */
    @Transactional(rollbackFor = {Exception.class})
    public synchronized void updateUserRole(User persistedUser, List<Role> updateRoles) {
        List<String> persistedRoleCode = persistedUser.getUserRoles().stream().map(userRole -> userRole.getRole().getCode()).collect(toList());
        List<String> updateRoleCode = updateRoles.stream().map(userRole -> userRole.getCode()).collect(toList());

        List<String> roleToBeDelete = new ArrayList<>();
        List<String> roleToBeInsert = new ArrayList<>();

        List<String> intersection = persistedRoleCode.stream().filter(updateRoleCode::contains).collect(toList());
        persistedRoleCode.stream().forEach(userRoleCode -> {
            if (!intersection.contains(userRoleCode)) {
                roleToBeDelete.add(userRoleCode);
            }
        });

        updateRoleCode.stream().forEach(userRoleCode -> {
            if (!intersection.contains(userRoleCode)) {
                roleToBeInsert.add(userRoleCode);
            }
        });


        persistedUser.getUserRoles().stream().forEach(userRole -> {
            if (roleToBeDelete.contains(userRole.getRole().getCode())) {
                userRolesRepository.delete(userRole);
            }
        });

        updateRoles.stream().forEach(role -> {
            if (roleToBeInsert.contains(role.getCode())) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(role.getId());
                userRole.setUserId(persistedUser.getId());
                userRolesRepository.save(userRole);
            }
        });
    }
}
