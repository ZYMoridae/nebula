package com.jz.nebula;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jz.nebula.entity.User;
import com.jz.nebula.repository.UserRepository;

@Service("userDetailsService")
public class NebulaUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepository;
    
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserBuilder builder = null;
        builder = org.springframework.security.core.userdetails.User.withUsername(username);
        builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
        builder.roles("user");
        
        return builder.build();
    }
}
