package com.jz.nebula.controller;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.entity.User;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserRepository userRepository;

	@GetMapping("/me")
	public ResponseEntity<?> currentUser(@AuthenticationPrincipal UserDetails userDetails) {
		Map<Object, Object> model = new HashMap<>();
		System.out.println(userDetails.getUsername());
		model.put("username", userDetails.getUsername());
		model.put("roles",
				userDetails.getAuthorities().stream().map(a -> ((GrantedAuthority) a).getAuthority()).collect(toList()));

		User user = userRepository.findByUsername(userDetails.getUsername()).get();

		return ok(user);
	}
}
