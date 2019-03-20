package com.jz.nebula;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.jz.nebula.entity.User;
import com.jz.nebula.repository.UserRepository;

@Component
public class CustomAuthenticationProvider
  implements AuthenticationProvider {
	
    @Autowired
    private UserRepository userRepository;
	
    @Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
  
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        System.out.println(name);
        
        User user = userRepository.findByUsername(name).get();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        
        if (encoder.matches(password, user.getPassword())) {
  
            // use the credentials
            // and authenticate against the third-party system
            return new UsernamePasswordAuthenticationToken(
              name, password, new ArrayList<>());
        } else {
            return null;
        }
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
          UsernamePasswordAuthenticationToken.class);
    }
}

