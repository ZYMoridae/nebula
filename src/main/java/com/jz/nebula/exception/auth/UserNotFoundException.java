package com.jz.nebula.exception.auth;

import org.springframework.security.core.AuthenticationException;

import javax.validation.constraints.NotNull;

public class UserNotFoundException extends AuthenticationException {
    public UserNotFoundException(@NotNull String msg) {
        super(msg);
    }
}
