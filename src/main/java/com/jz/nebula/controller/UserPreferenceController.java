package com.jz.nebula.controller;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.UserPreference;
import com.jz.nebula.service.UserPreferenceService;

public class UserPreferenceController {
	@Autowired
	private UserPreferenceService userPreferenceService;

	@GetMapping
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody PagedResources<Resource<UserPreference>> all(Pageable pageable, final UriComponentsBuilder uriBuilder,
			final HttpServletResponse response, PagedResourcesAssembler<UserPreference> assembler) {
		return userPreferenceService.findAll(pageable, assembler);
	}

	@GetMapping("/{id}")
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody UserPreference findById(@PathVariable("id") long id) {
		return userPreferenceService.findById(id);
	}

	@PostMapping("")
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody UserPreference create(@RequestBody UserPreference userPreference) {
		return userPreferenceService.save(userPreference);
	}

	@PutMapping("/{id}")
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody UserPreference update(@PathVariable("id") long id, @RequestBody UserPreference userPreference) {
		userPreference.setId(id);
		return userPreferenceService.save(userPreference);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") long id) {
		userPreferenceService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
