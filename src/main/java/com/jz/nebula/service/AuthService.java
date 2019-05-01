package com.jz.nebula.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jz.nebula.auth.AuthenticationFacade;

@Service
public class AuthService {

    @Autowired
    AuthenticationFacade authenticationFacade;

    public boolean verifyUser(String credential) {
        String encodedCredential = new BCryptPasswordEncoder().encode(credential);
        return authenticationFacade.getUser().getPassword().equals(encodedCredential);
    }
}
