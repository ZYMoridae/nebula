package com.jz.nebula.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import com.jz.nebula.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.jz.nebula.entity.User;
import com.jz.nebula.service.AuthService;
import com.jz.nebula.service.TokenService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
@Api(value = "authentication")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private static String VERIFICATION_HEADER = "X-VERIFICATION";

    @Autowired
    AuthService authService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService jwtTokenProvider;

    @Autowired
    UserRepository users;

    /**
     * Sign in the Nebula API with Basic Auth
     *
     * @param headers
     * @return
     */
    @PostMapping("/signin")
    @ApiOperation(value = "Sign in Nebula API", response = ResponseEntity.class)
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
            Optional<User> userOptional = this.users.findByUsername(username);

            Map<String, String> tokenMap = jwtTokenProvider.createToken(username, userOptional
                    .orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getUserRoles().stream().map(userRole -> userRole.getRole()).collect(Collectors.toList()));
            Map<Object, Object> resultMap = new HashMap<>();
            User user = userOptional.get();
            user.setRoles(user.getUserRoles().stream().map(userRole -> userRole.getRole()).collect(Collectors.toList()));

            resultMap.put("user", user);
            resultMap.put("token", tokenMap.get("accessToken"));
            resultMap.put("refreshToken", tokenMap.get("refreshToken"));
            return ok(resultMap);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    /**
     * Verify the authentication
     *
     * @param headers
     * @return
     */
    @PostMapping("/verify")
    @ApiOperation(value = "Verify the authentication", response = ResponseEntity.class)
    public ResponseEntity<?> verify(@RequestHeader HttpHeaders headers) {
        String verificationValue = headers.getFirst(VERIFICATION_HEADER);
        String credential = new String(DatatypeConverter.parseBase64Binary(verificationValue));

        boolean isVerified = authService.verifyUser(credential);

        Map<Object, Object> resultMap = new HashMap<>();
        resultMap.put("isVerified", isVerified);
        return ok(resultMap);
    }
}
