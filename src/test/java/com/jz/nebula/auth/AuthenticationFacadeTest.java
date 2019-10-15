package com.jz.nebula.auth;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;

import com.jz.nebula.Application;
import com.jz.nebula.dao.UserRepository;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SecurityContextHolder.class})
@SpringBootTest(classes = Application.class)
public class AuthenticationFacadeTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationFacade authenticationFacade;

    @Test
    public void getAuthenticationTest() {
        PowerMockito.mockStatic(SecurityContextHolder.class);
        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
        User fakeUserDetails = new User("test", "test", authorities);

        Authentication expectedAuthentication = new TestingAuthenticationToken(fakeUserDetails, "test");

        SecurityContextImpl mockSecurityContextImpl = PowerMockito.mock(SecurityContextImpl.class);

        when(mockSecurityContextImpl.getAuthentication()).thenReturn(expectedAuthentication);
        when(SecurityContextHolder.getContext()).thenReturn(mockSecurityContextImpl);

        Authentication returnedAuthentication = authenticationFacade.getAuthentication();
        assertEquals(expectedAuthentication.getPrincipal(), returnedAuthentication.getPrincipal());
        assertEquals(expectedAuthentication.getCredentials(), returnedAuthentication.getCredentials());
    }

    @Test
    public void getUserTest() {
        PowerMockito.mockStatic(SecurityContextHolder.class);
        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
        User fakeUserDetails = new User("test", "test", authorities);

        Authentication expectedAuthentication = new TestingAuthenticationToken(fakeUserDetails, "test");

        SecurityContextImpl mockSecurityContextImpl = PowerMockito.mock(SecurityContextImpl.class);

        when(mockSecurityContextImpl.getAuthentication()).thenReturn(expectedAuthentication);
        when(SecurityContextHolder.getContext()).thenReturn(mockSecurityContextImpl);

        com.jz.nebula.entity.User expectedUser = new com.jz.nebula.entity.User();
        expectedUser.setUsername("test");
        expectedUser.setPassword("test");
        Optional<com.jz.nebula.entity.User> optionalUser = Optional.of(expectedUser);
        when(userRepository.findByUsername("test")).thenReturn(optionalUser);


        com.jz.nebula.entity.User returnedUser = authenticationFacade.getUser();
        assertEquals(expectedUser.getUsername(), returnedUser.getUsername());
        assertEquals(expectedUser.getPassword(), returnedUser.getPassword());
    }
}
