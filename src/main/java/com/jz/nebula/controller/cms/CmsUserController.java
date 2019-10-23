package com.jz.nebula.controller.cms;

import com.jz.nebula.dao.RoleRepository;
import com.jz.nebula.dao.UserRolesRepository;
import com.jz.nebula.entity.User;
import com.jz.nebula.entity.UserRole;
import com.jz.nebula.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/cms/user")
public class CmsUserController extends CmsBaseController {

    @Autowired
    UserService userService;

    @Autowired
    UserRolesRepository userRolesRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/management")
    public String login(Pageable pageable, Model model) {
        model.addAttribute("data", userService.findAll(pageable));
        return "user/management";
    }

    @GetMapping("/{id}/show")
    public String show(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/show";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "user/new";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, @ModelAttribute User user, @RequestParam("userRoleArray") String userRoleArray) {
        // FIXME: For user role we need to manually updated, need to figure out how to update nested object
        String[] userRole = userRoleArray.split(",");

        user.setId(id);
        logger.debug("edit:: [{}]", user.getId());
        User persistedUser = userService.findById(id);

        /**
         * FIXME: [2019-10-20] If use default save, will get error
         * Error: class of the given object did not match class of persistent copy
         */

        persistedUser.setUsername(user.getUsername());
        persistedUser.setAddress1(user.getAddress1());
        persistedUser.setAddress2(user.getAddress2());
        persistedUser.setEmail(user.getEmail());
        persistedUser.setTelephone(user.getTelephone());
        persistedUser.setFirstname(user.getFirstname());
        persistedUser.setLastname(user.getLastname());
        persistedUser.setGender(user.getGender());

//        user.getUserRoles().stream().forEach(userRole -> {logger.debug("edit:: user role [{}]", userRole.getRole().getId());});

        userService.save(persistedUser);
        return "redirect:/cms/user/" + user.getId() + "/show";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute User user) {

        String encodedCredential = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedCredential);
        User persistedUser = userService.save(user);

        // FIXME: Due to the reason of nested model saving problem, we create user role and save it manually
        UserRole userRole = new UserRole();
        userRole.setUserId(persistedUser.getId());
        userRole.setRoleId(roleRepository.findByCode("USER").get().getId());
        userRolesRepository.save(userRole);
        logger.debug("create:: user role created");

        return "redirect:/cms/user/" + persistedUser.getId() + "/show";
    }
}
