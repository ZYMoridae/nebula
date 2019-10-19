package com.jz.nebula.controller.cms;

import com.jz.nebula.entity.User;
import com.jz.nebula.service.AuthService;
import com.jz.nebula.util.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/cms/")
public class CmLoginController {

    @Autowired
    AuthService authService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("isUserLogin", false);
        model.addAttribute("user", new User());
        return "login/login";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "forward:/cms/login";
    }

//    @PostMapping("")
//    public String loginSubmit(@ModelAttribute User user) {
//        if(authService.authenticate(user.getUsername(), user.getPassword())) {
//            return "redirect:/cms/home";
//        }else {
//            return "login/login";
//        }
//    }
}
