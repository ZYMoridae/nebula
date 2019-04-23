package com.jz.nebula.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

import com.jz.nebula.auth.AuthenticationFacade;
import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.entity.User;
import com.jz.nebula.service.AuthService;
import com.jz.nebula.service.TokenService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private static String VERIFICATION_HEADER = "X-VERIFICATION";
	
	@Autowired
	AuthService authService;
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	TokenService jwtTokenProvider;

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

//    		String username = data.getUsername();
//   		String password = data.getPassword();
			String username = actualCredentials[0];
			String password = actualCredentials[1];
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			Optional<User> user = this.users.findByUsername(username);
			Map<String, String> tokenMap = jwtTokenProvider.createToken(username, user
					.orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());
			Map<Object, Object> resultMap = new HashMap<>();
			resultMap.put("user", user.get());
			resultMap.put("token", tokenMap.get("accessToken"));
			resultMap.put("refreshToken", tokenMap.get("refreshToken"));
			return ok(resultMap);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied");
		}
	}
	
	@PostMapping("/verify")
	public ResponseEntity<?> verify(@RequestHeader HttpHeaders headers) {
		String verificationValue = headers.getFirst(VERIFICATION_HEADER);
		String credential = new String(DatatypeConverter.parseBase64Binary(verificationValue));
		
		boolean isVerified = authService.verifyUser(credential);
		
		Map<Object, Object> resultMap = new HashMap<>();
		resultMap.put("isVerified", isVerified);
		return ok(resultMap);
	}
}
