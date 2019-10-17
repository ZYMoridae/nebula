package com.jz.nebula.service;

import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jz.nebula.auth.AuthenticationFacade;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AuthService {

    private final Logger logger = LogManager.getLogger(AuthService.class);

    @Autowired
    AuthenticationFacade authenticationFacade;

    @Autowired
    UserRepository userRepository;

    /**
     * Verify the user
     *
     * @param credential
     * @return
     */
    public boolean verifyUser(String credential) {
        String encodedCredential = new BCryptPasswordEncoder().encode(credential);
        return authenticationFacade.getUser().getPassword().equals(encodedCredential);
    }
}
