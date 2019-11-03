package com.jz.nebula.controller.api;

import com.jz.nebula.entity.Role;
import com.jz.nebula.service.RefreshTokenService;
import com.jz.nebula.service.TokenService;
import com.jz.nebula.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/token")
public class TokenController {
    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    TokenService tokenService;

    @Autowired
    private UserService userService;

    /**
     * @param refreshToken
     * @return
     * @throws Exception
     */
    @PostMapping("/refresh")
    public @ResponseBody
    Map<?, ?> refreshToken(@RequestParam String refreshToken) throws Exception {
        return refreshTokenService.refreshToken(refreshToken);
    }

    /**
     * @return
     */
    @GetMapping
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Map<String, String> getRefreshToken() {
        Map<String, String> resultMap = new ConcurrentHashMap<>();
        String refreshToken = refreshTokenService.getRefreshToken();
        resultMap.put("refreshToken", refreshToken);
        return resultMap;
    }

    /**
     * Check token is alive or not
     *
     * @param token
     * @return
     */
    @GetMapping("/{token}/alive")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Map<String, Boolean> isTokenAlive(@PathVariable("token") String token) {
        Map<String, Boolean> resultMap = new ConcurrentHashMap<>();
        Boolean isTokenAlive = tokenService.validateToken(token);
        resultMap.put("isTokenAlive", isTokenAlive);
        return resultMap;
    }

    @GetMapping("/{token}/admin-validation")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Map<String, Boolean> isAdminToken(@PathVariable("token") String token, @AuthenticationPrincipal UserDetails userDetails) {
        Map<String, Boolean> resultMap = new ConcurrentHashMap<>();
        Boolean isAdminToken = tokenService.isAdminToken(token, userService.getCurrentUser(userDetails));
        resultMap.put("isAdminToken", isAdminToken);
        return resultMap;
    }
}
