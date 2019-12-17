package com.jz.nebula.auth;

import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    private final Logger logger = LogManager.getLogger(AuthenticationFacade.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public User getUser() {
        User user = null;
        try {
            Optional<User> userOptional = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            user = userOptional.isPresent() ? userOptional.get() : null;
        } catch (NullPointerException e) {
            logger.debug("getUser:: can not find user");
        }

        return user;
    }

    @Override
    public Long getUserId() {
        User user = getUser();
        return user == null ? null : user.getId();
    }

    /**
     * Check user login status
     *
     * @return
     */
    public boolean isUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }
}
