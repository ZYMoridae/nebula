package com.jz.nebula.component.exception.auth;

import org.springframework.security.core.AuthenticationException;

public class BadBasicAuthInfo extends AuthenticationException {
    public BadBasicAuthInfo(String msg) {
        super(msg);
    }
}
