package com.jz.nebula.component.exception.auth;

import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
