package com.gigadev.digitalmarketplace.auth.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired UserService userService;
	@Autowired PasswordEncoder passEncoder;
	
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> findAllUsers() {
		return ResponseEntity.ok(userService.searchAllUsers());
	}
	
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> create(@RequestBody User user) {
		return ResponseEntity.ok(userService.create(user));
		
	}

}
