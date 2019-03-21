package com.jz.nebula.controller;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jz.nebula.dao.UserRepository;
//import com.jz.nebula.AuthenticationRequest;
import com.jz.nebula.jwt.JwtTokenProvider;

import static org.springframework.http.ResponseEntity.ok;
@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;
    
  @Autowired
  JwtTokenProvider jwtTokenProvider;
    
  @Autowired
  UserRepository users;
    
  @PostMapping("/signin")
//  @RequestBody AuthenticationRequest data, 
  public ResponseEntity<?> signin(@RequestHeader HttpHeaders headers) {
    try {
    	String authValue = headers.getFirst(HttpHeaders.AUTHORIZATION);
    	String credentials = authValue.substring("Basic".length()).trim();
    	byte[] decoded = DatatypeConverter.parseBase64Binary(credentials);
    	String decodedString = new String(decoded);
    	String[] actualCredentials = decodedString.split(":");
    	
//    String username = data.getUsername();
//   	String password = data.getPassword();
    	String username = actualCredentials[0];
    	String password = actualCredentials[1];
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      String token = jwtTokenProvider.createToken(username, this.users.findByUsername(username)
      		.orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"))
      		.getRoles());
      Map<Object, Object> model = new HashMap<>();
      model.put("username", username);
      model.put("token", token);
      return ok(model);
    } catch (AuthenticationException e) {
        throw new BadCredentialsException("Invalid username/password supplied");
    }
  }
}
