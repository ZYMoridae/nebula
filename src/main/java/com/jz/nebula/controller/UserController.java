package com.jz.nebula.controller;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jz.nebula.auth.IAuthenticationFacade;
import com.jz.nebula.dao.RoleRepository;
import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.User;
import com.jz.nebula.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@GetMapping("/me")
	public ResponseEntity<?> currentUser(@AuthenticationPrincipal UserDetails userDetails) {
		Map<Object, Object> model = new HashMap<>();
		System.out.println(userDetails.getUsername());
		model.put("username", userDetails.getUsername());
		model.put("roles", userDetails.getAuthorities().stream().map(a -> ((GrantedAuthority) a).getAuthority())
				.collect(toList()));

		User user = userRepository.findByUsername(userDetails.getUsername()).get();

		return ok(user);
	}

	/**
	 * This is public route
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setRole(roleRepository.findByCode(Role.ROLE_USER.replaceAll("ROLE_", "")).get());
		ObjectMapper oMapper = new ObjectMapper();
		User savedUser = userRepository.save(user);
		@SuppressWarnings("unchecked")
		Map<String, Object> map = oMapper.convertValue(savedUser, Map.class);
		List<String> roles = new ArrayList<>();
		roles.add(Role.ROLE_USER);
		map.put("token", jwtTokenProvider.createToken(savedUser.getUsername(), roles));

		return ok(map);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) throws Exception {
		if (id != authenticationFacade.getUser().getId()) {
			throw new Exception();
		}
		return ok(userRepository.save(user));
	}

}
