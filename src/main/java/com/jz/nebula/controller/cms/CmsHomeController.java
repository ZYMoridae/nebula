package com.jz.nebula.controller.cms;

import com.jz.nebula.auth.AuthenticationFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/cms/home")
public class CmsHomeController extends CmsBaseController {

    @Autowired
    AuthenticationFacade authenticationFacade;

    @GetMapping("")
    public String login(Model model) {
        logger.debug("model attributes:: [{}]", model.containsAttribute("isUserLogin"));
        return "home";
    }
}
