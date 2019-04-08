package com.jz.nebula.auth;

import org.springframework.security.core.Authentication;

import com.jz.nebula.entity.User;

public interface IAuthenticationFacade {
	Authentication getAuthentication();

	User getUser();
}
