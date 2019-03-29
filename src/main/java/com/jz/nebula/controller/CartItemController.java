package com.jz.nebula.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jz.nebula.entity.CartItem;
import com.jz.nebula.entity.Role;
import com.jz.nebula.service.CartItemService;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {
	@Autowired
	private CartItemService cartItemService;

	@GetMapping("/{id}")
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody CartItem findById(@PathVariable("id") long id) {
		return cartItemService.findById(id);
	}

	@PostMapping("")
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody CartItem create(@RequestBody CartItem cartItem) throws Exception{
		CartItem savedCartItem = cartItemService.save(cartItem);
		if(savedCartItem == null) {
			//TODO: Create exception
			throw new Exception();
		}
		return savedCartItem;
	}

	@PutMapping("/{id}")
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody CartItem update(@PathVariable("id") long id, @RequestBody CartItem cartItem) {
		cartItem.setId(id);
		return cartItemService.save(cartItem);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed({ Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN })
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") long id) {
		cartItemService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
