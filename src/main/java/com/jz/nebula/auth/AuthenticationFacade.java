package com.jz.nebula.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.entity.User;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
	}
}
