package com.jz.nebula.controller.cms;

import com.jz.nebula.entity.User;
import com.jz.nebula.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/cms/login")
public class LoginController {

    @Autowired
    AuthService authService;

    @GetMapping("")
    public String login(Model model) {
        model.addAttribute("isUserLogin", false);
        model.addAttribute("user", new User());
        return "login/login";
    }

    @PostMapping("")
    public String loginSubmit(@ModelAttribute User user) {
        if(authService.authenticate(user.getEmail(), user.getPassword())) {
            return "redirect:/cms/home";
        }else {
            return "login/login";
        }
    }
}
