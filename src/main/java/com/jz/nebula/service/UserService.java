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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
     *
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
     * FIXME: We user role relationship to the separate table
     *
     * @param user
     *
     * @return
     */
    public User createUser(User user) {
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

        return savedUser;
    }

    /**
     * Get tokens
     *
     * @param user
     * @param roles
     *
     * @return
     */
    public Map getTokens(User user, List roles) {
        return jwtTokenProvider.createToken(user.getUsername(), roles);
    }

    /**
     * Get current user through user details
     *
     * @param userDetails
     *
     * @return
     */
    public User getCurrentUser(UserDetails userDetails) {
        Map<Object, Object> model = new HashMap<>();
        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities().stream().map(a -> a.getAuthority())
                .collect(toList()));

        User user = userRepository.findByUsername(userDetails.getUsername()).get();
        List<Role> roles = user.getUserRoles().stream().map(userRole -> userRole.getRole()).collect(Collectors.toList());
        user.setRoles(roles);

        return user;
    }

    /**
     * TODO: Need to be optimized
     *
     * @param pageable
     *
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
     *
     * @return
     */
    public PagedModel<EntityModel<User>> findAll(String keyword, Pageable pageable,
                                                 PagedResourcesAssembler<User> assembler) {
        Page<User> page;
        if (Strings.isNullOrEmpty(keyword)) {
            page = userRepository.findAllByOrderByIdAsc(pageable);
        } else {
            page = userRepository.findByNameContaining(keyword, pageable);
        }

        PagedModel<EntityModel<User>> resources = assembler.toModel(page, linkTo(UserController.class).slash("/users").withSelfRel());

        return resources;
    }

    /**
     * Find all roles
     *
     * @param keyword
     * @param pageable
     * @param assembler
     *
     * @return
     */
    public PagedModel<EntityModel<Role>> findAllRoles(String keyword, Pageable pageable,
                                                      PagedResourcesAssembler<Role> assembler) {
        Page<Role> page;
        if (Strings.isNullOrEmpty(keyword)) {
            page = roleRepository.findAllByOrderByIdAsc(pageable);
        } else {
            page = roleRepository.findByCodeContaining(keyword, pageable);
        }

        PagedModel<EntityModel<Role>> resources = assembler.toModel(page,
                linkTo(UserController.class).slash("/users/roles").withSelfRel());

        return resources;
    }

    /**
     * Find user by id
     *
     * @param id
     *
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
     *
     * @return
     */
//    @Transactional(rollbackFor = {Exception.class})
    public User save(User user) {
        User persistedUser;

        if (Objects.isNull(user.getId())) {
            // FIXME: Need to make create user as transactional (rollback when get exception)
            persistedUser = createUser(user);
            logger.debug("save:: create new user");
        } else {
            List<Role> roles = user.getRoles();
            persistedUser = saveUser(user);
            updateUserRole(findById(user.getId()), roles);
        }

        return persistedUser;
    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized User saveUser(User user) {
        User persistedUser;
        User _persistedUser = this.findById(user.getId());

        _persistedUser.setUsername(user.getUsername());
        _persistedUser.setAddress1(user.getAddress1());
        _persistedUser.setAddress2(user.getAddress2());
        _persistedUser.setEmail(user.getEmail());
        _persistedUser.setTelephone(user.getTelephone());
        _persistedUser.setFirstname(user.getFirstname());
        _persistedUser.setLastname(user.getLastname());
        _persistedUser.setGender(user.getGender());

        persistedUser = userRepository.save(_persistedUser);

        logger.debug("save:: update user with id [{}]", persistedUser.getId());

        return persistedUser;
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
        List<String> persistedRoleCode = persistedUser
                .getUserRoles()
                .stream()
                .map(userRole -> userRole.getRole().getCode())
                .collect(toList());
        List<String> updateRoleCode = updateRoles
                .stream()
                .map(userRole -> userRole.getCode())
                .collect(toList());

        List<String> roleToBeDelete = new ArrayList<>();
        List<String> roleToBeInsert = new ArrayList<>();

        List<String> intersection = persistedRoleCode
                .stream()
                .filter(updateRoleCode::contains)
                .collect(toList());

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
