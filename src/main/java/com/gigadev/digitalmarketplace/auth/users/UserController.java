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
		
	@PostMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<User> createAdmin(@RequestBody UserDtoRegister admin) {			
		return ResponseEntity.ok(userService.saveAdmin(admin));
	}	
	
	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody UserDtoRegister user) {
		return ResponseEntity.ok(userService.saveUser(user));
	}
	
	// ============== GET ==============
		
	@GetMapping("/getAllInfo")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get all users info", security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<List<UserDtoGetResponse>> findAllUsersInfo() {
		return ResponseEntity.ok(userService.getAllUsersInfo());
	}
		
	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary = "Get single user info", security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<UserDtoGetResponse> findUserInfo(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserInfo(id));
	}
		
	// ============== PUT/PATCH ==============
		
	@PatchMapping("/updateProfileInfo/{id}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<User> updateProfileInfo(@RequestBody UserDtoProfile user, @PathVariable Long id) {
		return ResponseEntity.ok(userService.updateProfileInfo(user, id));
	}
		
	@PatchMapping("/updateCredentials/{id}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<User> updateCredentials(@RequestBody UserDtoCredentials user, @PathVariable Long id) {
			return ResponseEntity.ok(userService.updateCredentials(user, id));
	}
	
	// ============== DELETE ==============
		
	@DeleteMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.ok("Delete successfull");
	}

}
