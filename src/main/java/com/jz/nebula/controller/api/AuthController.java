/*
 * Copyright (c) 2019. Nebula Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jz.nebula.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;

import com.jz.nebula.entity.Role;
import com.jz.nebula.exception.auth.BadBasicAuthInfo;
import com.jz.nebula.exception.auth.UserNotFoundException;
import com.jz.nebula.service.ReceiptingService;
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
@RequestMapping("/api/sso")
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
    UserRepository userRepository;

    @Autowired
    ReceiptingService receiptingService;

    /**
     * Sign in the Nebula API with Basic Auth
     *
     * @param headers
     * @return
     */
    @PostMapping("/auth")
    @ApiOperation(value = "Authenticate user", response = ResponseEntity.class)
//  @RequestBody AuthenticationRequest data,
    public ResponseEntity<?> authenticate(@RequestHeader HttpHeaders headers) {
        try {
            String authValue = headers.getFirst(HttpHeaders.AUTHORIZATION);
            String credentials = authValue.substring("Basic".length()).trim();
            byte[] decoded = DatatypeConverter.parseBase64Binary(credentials);
            String decodedString = new String(decoded);
            String[] actualCredentials = decodedString.split(":");

            if(actualCredentials.length != 2) {
                logger.debug("authenticate:: bad basic authentication info");
                throw new BadBasicAuthInfo("Bad basic authentication info");
            }
            String username = actualCredentials[0];
            String password = actualCredentials[1];
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            Optional<User> userOptional = this.userRepository.findByUsername(username);
            Map<Object, Object> resultMap = new HashMap<>();

            if(userOptional.isPresent()) {
                User user = userOptional.get();
                List<Role> roles = user.getUserRoles().stream().map(userRole -> userRole.getRole()).collect(Collectors.toList());

                Map<String, String> tokenMap = jwtTokenProvider.createToken(username, roles);
                user.setRoles(roles);

                resultMap.put("user", user);
                resultMap.put("token", tokenMap.get("accessToken"));
                resultMap.put("refreshToken", tokenMap.get("refreshToken"));
            }else {
                logger.debug("authenticate:: user not found");
                throw new UserNotFoundException("User not found");
            }

            return ok(resultMap);
        } catch (AuthenticationException e) {
            logger.debug("authenticate:: invalid username/password");
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
