package com.jz.nebula.controller.cms;

import com.jz.nebula.service.cms.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletResponse;

public class BaseController {

    @Autowired
    SessionService sessionService;

    @ModelAttribute
    public void setSessionHeader(HttpServletResponse response) {
        response.setHeader("X-SESSION", sessionService.getCurrentSessionId());
    }
}
