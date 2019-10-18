package com.jz.nebula.service;

import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.entity.User;
import com.jz.nebula.service.cms.SessionService;
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

    @Autowired
    SessionService sessionService;

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

    /**
     * Authenticate user and create user session. At current stage, this function will only be used by web authentication
     *
     * @param username
     * @param rawPassword
     * @return
     */
    public boolean authenticate(String username, String rawPassword) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (!optionalUser.isPresent()) return false;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean isPasswordMatched = encoder.matches(rawPassword, optionalUser.get().getPassword());

        // Is password matched and create user session
        if (isPasswordMatched) sessionService.createUserSession(username);
        logger.debug("authenticate:: session created");

        // TODO: Not sure the authentication object will be accessible when user doing the next session
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, rawPassword, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.debug("authenticate:: Spring authentication set");

        return isPasswordMatched;
    }
}
