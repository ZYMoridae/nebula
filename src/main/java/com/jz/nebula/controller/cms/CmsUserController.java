package com.jz.nebula.controller.cms;

import com.jz.nebula.entity.User;
import com.jz.nebula.entity.UserRole;
import com.jz.nebula.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/cms/user")
public class CmsUserController extends CmsBaseController {

    @Autowired
    UserService userService;

    @GetMapping("/management")
    public String login(Pageable pageable, Model model) {
        model.addAttribute("data", userService.findAll(pageable));
        return "user/management";
    }

    @GetMapping("/{id}/show")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user/show";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "user/new";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, @ModelAttribute User user) {
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

        userService.save(persistedUser);
        return "redirect:/cms/user/" + user.getId() + "/show";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute User user) {
        User persistedUser = userService.save(user);
        return "redirect:/cms/user/" + persistedUser.getId() + "/show";
    }
}
