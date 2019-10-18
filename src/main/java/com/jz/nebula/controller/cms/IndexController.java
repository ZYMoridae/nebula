package com.jz.nebula.controller.cms;

import com.jz.nebula.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class IndexController {

    @GetMapping("")
    public String login(Model model) {
        return "forward:/cms/login";
    }
}
