package com.jz.nebula.exception.auth;

import org.springframework.security.core.AuthenticationException;

import javax.validation.constraints.NotNull;

public class BadBasicAuthInfo extends AuthenticationException {
    public BadBasicAuthInfo(@NotNull String msg) {
        super(msg);
    }
}
