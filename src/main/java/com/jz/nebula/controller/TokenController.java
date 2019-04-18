package com.jz.nebula.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jz.nebula.entity.Role;
import com.jz.nebula.service.RefreshTokenService;

@RestController
@RequestMapping("/token")
public class TokenController {
	@Autowired
	RefreshTokenService refreshTokenService;

	@PostMapping("/refresh")
	public @ResponseBody Map<?, ?> refreshToken(@RequestParam String refreshToken) throws Exception {
		return refreshTokenService.refreshToken(refreshToken);
	}

	@GetMapping
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody Map<String, String> getRefreshToken() {
		Map<String, String> resultMap = new ConcurrentHashMap<>();
		String refreshToken = refreshTokenService.getRefreshToken();
		resultMap.put("refreshToken", refreshToken);
		return resultMap;
	}
}
