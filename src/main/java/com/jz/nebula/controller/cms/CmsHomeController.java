package com.jz.nebula.controller.cms;

import com.jz.nebula.util.auth.AuthenticationFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/cms/home")
public class CmsHomeController extends CmsBaseController {

    @Autowired
    AuthenticationFacadeImpl authenticationFacadeImpl;

    @GetMapping("")
    public String login(Model model) {
        logger.debug("model attributes:: [{}]", model.containsAttribute("isUserLogin"));
        return "home";
    }
}
