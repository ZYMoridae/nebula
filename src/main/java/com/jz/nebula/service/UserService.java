package com.jz.nebula.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jz.nebula.dao.RoleRepository;
import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.dao.UserRolesRepository;
import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.User;
import com.jz.nebula.entity.UserRole;
import com.jz.nebula.entity.product.Product;
import com.jz.nebula.util.PageableHelper;
import com.jz.nebula.util.Security;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;
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
    public HashMap<String, Object> findAll(Pageable pageable) {
        logger.debug("findAll:: pageNumber:[{}], pageSize: [{}]", pageable.getPageNumber(), pageable.getPageSize());
        Page<User> pageUsers = userRepository.findAllByOrderByIdAsc(pageable);
        List<User> users = new ArrayList<>();
        pageUsers.iterator().forEachRemaining(users::add);

        HashMap<String, Object> res = new HashMap<>();
        res.put("users", users);
        res.put("totalPages", pageUsers.getTotalPages());
        res.put("pageNumber", pageable.getPageNumber());
        res.put("pageSize", pageable.getPageSize());
        res.put("path", "/cms/user");

        List<Integer> pageNumberArray = PageableHelper.getPageNumberArray(pageUsers.getTotalPages(), pageable.getPageNumber());
        res.put("pageNumberArray", pageNumberArray);

        HashMap<String, Integer> prevNextInfo = PageableHelper.getPrevAndNextIndex(pageNumberArray, pageable.getPageNumber());

        res.put("prevIndex", prevNextInfo.get("prev"));
        res.put("nextIndex", prevNextInfo.get("next"));


        List<Integer> perPageOptions = new ArrayList<>();
        perPageOptions.add(10);
        perPageOptions.add(30);
        perPageOptions.add(50);

        res.put("perPageOptions", perPageOptions);

        return res;
    }

    /**
     * Find user by id
     *
     * @param id
     * @return
     */
    public User findById(long id) {
        return userRepository.findById(id).get();
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


    public void updateUserRole(User persistedUser, List<Role> updateRoles) {
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
