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
	
	// ============== POST ==============
		
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	@PostMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> createAdmin(@RequestBody UserDto admin) {			
		return ResponseEntity.ok(userService.saveAdmin(admin));
	}	
	
	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody UserDto user) {
		return ResponseEntity.ok(userService.saveUser(user));
	}
	
	// ============== GET ==============
		
	@Operation(summary = "Get all users info", security = @SecurityRequirement(name = "bearer-authentication"))
	@GetMapping("/getAllInfo")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserResponse>> findAllUsersInfo() {
		return ResponseEntity.ok(userService.getAllUsersInfo());
	}
	
	@Operation(summary = "Get single user info", security = @SecurityRequirement(name = "bearer-authentication"))
	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<UserResponse> findUserInfo(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserInfo(id));
	}
		
	// ============== PUT/PATCH ==============
	
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	@PatchMapping("/updateProfileInfo/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<User> updateProfileInfo(@RequestBody UserProfileDto user, @PathVariable Long id) {
		return ResponseEntity.ok(userService.updateProfileInfo(user, id));
	}
	
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	@PatchMapping("/updateCredentials/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<User> updateCredentials(@RequestBody UserCredentialsDto user, @PathVariable Long id) {
		return ResponseEntity.ok(userService.updateCredentials(user, id));
	}
	
	// ============== DELETE ==============
	
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	@DeleteMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.ok("Delete successfull");
	}

}
