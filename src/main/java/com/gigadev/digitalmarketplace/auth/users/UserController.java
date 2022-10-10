package com.gigadev.digitalmarketplace.auth.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.gigadev.digitalmarketplace.auth.roles.Role;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/users")
public class UserController {
	
	@Autowired UserService userService;	
	@Autowired @Qualifier("roleAdmin") Role roleAdmin;
	@Autowired @Qualifier("roleUser") Role roleUser;
	
	// ============== POST ==============
		
	@PostMapping("/createAdmin")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<User> createAdmin(@RequestBody UserDtoRegister admin) {	
		return ResponseEntity.ok(userService.saveUser(admin, roleAdmin));
	}	
	
	@PostMapping("/createUser")
	public ResponseEntity<User> createUser(@RequestBody UserDtoRegister user) {
		return ResponseEntity.ok(userService.saveUser(user, roleUser));
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
		
	@PatchMapping("/{id}/updateProfileInfo")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<User> updateProfileInfo(@RequestBody UserDtoProfile user, @PathVariable Long id) {
		return ResponseEntity.ok(userService.updateProfileInfo(user, id));
	}
		
	@PatchMapping("/{id}/updateCredentials")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<User> updateCredentials(@RequestBody UserDtoCredentials user, @PathVariable Long id) {
			return ResponseEntity.ok(userService.updateCredentials(user, id));
	}
	
	// >>>Polimorfismo<<<
	// Per un PATCH senza scrittura nei campi di input (FE) omettere il @RequestBody, il metodo verra' lanciato	
	
	// Omettere @PreAuthorize altrimenti da errore di authentication
	// il componente modal non verra' comunque caricato perche' non si e' fornito un id.
	@PatchMapping("/{id}/subscribeMonthly")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<User> subscribeMonthly(@PathVariable Long id) {
			return ResponseEntity.ok(userService.beginSubscription(id, 4.90, 30));
	}
	
	@PatchMapping("/{id}/subscribeSemestral")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<User> subscribeSemestral(@PathVariable Long id) {
			return ResponseEntity.ok(userService.beginSubscription(id, 24.90, 180));
	}
	
	@PatchMapping("/{id}/subscribeAnnual")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<User> subscribeAnnual(@PathVariable Long id) {
			return ResponseEntity.ok(userService.beginSubscription(id, 44.90, 365));
	}	
	
	// --------------------------------------
	
	@PatchMapping("/{id}/addFiveDollars")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<User> addFiveDollars(@PathVariable Long id) {
		return ResponseEntity.ok(userService.addBalance(id, 5.00));
	}
	
	@PatchMapping("/{id}/addTwentyFiveDollars")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<User> addTwentyFiveDollars(@PathVariable Long id) {
		return ResponseEntity.ok(userService.addBalance(id, 25.00));
	}
	
	@PatchMapping("/{id}/addFiftyDollars")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<User> addFiftyDollars(@PathVariable Long id) {
		return ResponseEntity.ok(userService.addBalance(id, 50.00));
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
