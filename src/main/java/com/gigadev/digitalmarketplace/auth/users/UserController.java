package com.gigadev.digitalmarketplace.auth.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/users")
public class UserController {
	
	@Autowired UserService userService;
		
	//@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	@PostMapping("/admin")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> createAdmin(@RequestBody UserDto admin) {			
		return ResponseEntity.ok(userService.saveAdmin(admin));
	}
	
	//@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	@PostMapping("/user")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> createUser(@RequestBody UserDto user) {
		return ResponseEntity.ok(userService.saveUser(user));
	}
	
	@Operation(summary = "Get all users", security = @SecurityRequirement(name = "bearer-authentication"))
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> findAllUsers() {
		return ResponseEntity.ok(userService.searchAllUsers());
	}
	
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.read(id));
	}
	
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	@PatchMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> update(@RequestBody UserDto user, Long id) {
		return ResponseEntity.ok(userService.update(user, id));
	}
	
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.ok("Delete successfull");
	}

}
