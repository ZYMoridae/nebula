package com.jz.nebula.service;

import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jz.nebula.auth.AuthenticationFacade;

import java.util.Optional;

@Service
public class AuthService {

    private final Logger logger = LogManager.getLogger(AuthService.class);

    @Autowired
    AuthenticationFacade authenticationFacade;

    @Autowired
    UserRepository userRepository;

    public boolean verifyUser(String credential) {
        String encodedCredential = new BCryptPasswordEncoder().encode(credential);
        return authenticationFacade.getUser().getPassword().equals(encodedCredential);
    }

    /**
     * Authenticate user
     *
     * @param email
     * @param rawPassword
     * @return
     */
    public boolean authenticate(String email, String rawPassword) {
        Optional<User> optionalUser = userRepository.findByEmail(email);


        if(!optionalUser.isPresent()) {
            return false;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword, optionalUser.get().getPassword());
    }
}
