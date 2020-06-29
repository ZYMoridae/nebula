package com.jz.nebula.controller.cms;

import com.jz.nebula.util.auth.AuthenticationFacadeImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public class CmsBaseController {
    protected final Logger logger = LogManager.getLogger(CmsHomeController.class);

    @Autowired
    AuthenticationFacadeImpl authenticationFacadeImpl;

    @ModelAttribute
    public void setSessionHeader(Model model) {
        logger.debug("login:: authentication name [{}]", authenticationFacadeImpl.getAuthentication().getName());
        model.addAttribute("isUserLogin", authenticationFacadeImpl.isUserLogin());
        model.addAttribute("currentUser", authenticationFacadeImpl.getUser());
    }
}
