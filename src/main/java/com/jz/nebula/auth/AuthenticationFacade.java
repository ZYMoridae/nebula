package com.jz.nebula.auth;

import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
        Optional<User> userOptional = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return userOptional.isPresent() ? userOptional.get() : null;
    }

    @Override
    public Long getUserId() {
        User user = getUser();
        return user == null ? null : user.getId();
    }
}
