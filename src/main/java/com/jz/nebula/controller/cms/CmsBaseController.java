package com.jz.nebula.controller.cms;

import com.jz.nebula.auth.AuthenticationFacade;
import com.jz.nebula.service.cms.SessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletResponse;

public class CmsBaseController {
    protected final Logger logger = LogManager.getLogger(CmsHomeController.class);

    @Autowired
    AuthenticationFacade authenticationFacade;

    @ModelAttribute
    public void setSessionHeader(Model model) {
        logger.debug("login:: authentication name [{}]", authenticationFacade.getAuthentication().getName());
        model.addAttribute("isUserLogin", authenticationFacade.isUserLogin());
        model.addAttribute("currentUser", authenticationFacade.getUser());
    }
}
