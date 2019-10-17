package com.jz.nebula.controller.cms;

import com.jz.nebula.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/cms/home")
public class HomeController extends BaseController{

    @GetMapping("")
    public String login(Model model) {
        return "home";
    }
}
