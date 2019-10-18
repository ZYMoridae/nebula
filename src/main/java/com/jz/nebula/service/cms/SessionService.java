package com.jz.nebula.service.cms;

import com.jz.nebula.auth.AuthenticationFacade;
import com.jz.nebula.util.Security;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SessionService {
    private final Logger logger = LogManager.getLogger(SessionService.class);

    @Autowired
    private RedisTemplate<String, String> template;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    private int expiredMinutes = 30;

    public String createUserSession(String username) {
        String sessionId = Security.generateHash(username);

        template.opsForHash().put(sessionId, "username", username);
        template.expire(sessionId, this.expiredMinutes, TimeUnit.MINUTES);
        logger.debug("createUserSession:: [{}] session was created", sessionId);
        return sessionId;
    }

    /**
     * Extends session life time
     *
     * @param sessionId
     */
    public void extendSessionLifetime(String sessionId) {
        template.expire(sessionId, this.expiredMinutes, TimeUnit.MINUTES);
    }

    /**
     * Check session in Redis whether it is still alive
     *
     * @param sessionId
     * @return
     */
    public boolean isSessionAlive(String sessionId) {
        return template.opsForHash().hasKey(sessionId, "username");
    }

    /**
     * Delete hash key from Redis
     *
     * @param sessionId
     */
    public void deleteSession(String sessionId) {
        template.opsForHash().delete(sessionId, "username");
        logger.debug("deleteSession:: delete session [{}]", sessionId);
    }

    /**
     * TODO: Check authentication facade works after user login
     *
     * @return
     */
    public String getCurrentSessionId() {
        String sessionId = Security.generateHash(authenticationFacade.getAuthentication().getName());
        logger.debug("getCurrentSessionId:: current user session id [{}]", sessionId);

        if (!isSessionAlive(sessionId)) {
            return null;
        }
        return sessionId;
    }
}
