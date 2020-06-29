package com.jz.nebula.util.auth;

import com.jz.nebula.entity.User;
import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
    Authentication getAuthentication();

    User getUser();

    Long getUserId();
}
