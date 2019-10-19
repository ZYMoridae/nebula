package com.jz.nebula.controller.cms;

import com.jz.nebula.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/cms/user")
public class CmsUserController extends CmsBaseController{

    @Autowired
    UserService userService;

    @GetMapping("/management")
    public String login(Pageable pageable, Model model) {
        model.addAttribute("users", userService.findAll(pageable));
        return "user/management";
    }
}
