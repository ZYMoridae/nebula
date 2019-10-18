package com.jz.nebula.controller.cms;

import com.jz.nebula.auth.AuthenticationFacade;
import com.jz.nebula.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/cms/home")
public class HomeController extends BaseController {

    private final Logger logger = LogManager.getLogger(HomeController.class);

    @Autowired
    AuthenticationFacade authenticationFacade;

    @GetMapping("")
    public String login(Model model) {
        logger.debug("login:: authentication name [{}]", authenticationFacade.getAuthentication().getName());
        model.addAttribute("isUserLogin", authenticationFacade.isUserLogin());
        model.addAttribute("user", authenticationFacade.getUser());
        return "home";
    }
}
