package com.jz.nebula.interceptor;

import com.jz.nebula.service.cms.SessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CmsRouteInterceptor extends HandlerInterceptorAdapter {
    private final Logger logger = LogManager.getLogger(RouteInterceptor.class);

    @Autowired
    SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        logger.info("preHandle:: URI is [{}]", url);

        String sessionId = request.getHeader("X-SESSION");

        if(sessionId == null) {
            response.sendRedirect("/login");
            return false;
        }

        sessionService.extendSessionLifetime(sessionId);
        logger.debug("preHandle:: extend session life time");

        return super.preHandle(request, response, handler);
    }
}
