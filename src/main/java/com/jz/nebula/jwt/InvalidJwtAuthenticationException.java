package com.jz.nebula.jwt;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8439349034884952090L;

		public InvalidJwtAuthenticationException(String e) {
        super(e);
    }
}
