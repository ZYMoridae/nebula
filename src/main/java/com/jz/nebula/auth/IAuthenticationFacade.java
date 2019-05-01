package com.jz.nebula.auth;

import com.jz.nebula.entity.User;
import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

    User getUser();

    Long getUserId();
}
