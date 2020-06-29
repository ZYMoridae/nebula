package com.jz.nebula.component.interceptor;

import com.jz.nebula.util.auth.AuthenticationFacadeImpl;
import com.jz.nebula.entity.UserLog;
import com.jz.nebula.service.analytics.UserLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RouteInterceptor extends HandlerInterceptorAdapter {
    private final Logger logger = LogManager.getLogger(RouteInterceptor.class);

    @Autowired
    UserLogService userLogService;

    @Autowired
    AuthenticationFacadeImpl authenticationFacadeImpl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = this.getClientIp(request);
        logger.info("Request from IP:[{}]", clientIp);

        Long userId = authenticationFacadeImpl.getUserId();

        userId = userId != null ? userId : -1;

        // Save user log
        UserLog userLog = new UserLog();
        userLog.setAction(request.getRequestURI());
        userLog.setIpAddress(clientIp);
        userLog.setUserId(userId);
        userLogService.save(userLog);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * Get client Ip address
     *
     * @param request
     *
     * @return
     */
    private String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }
}
