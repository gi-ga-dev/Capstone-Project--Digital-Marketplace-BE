package com.gigadev.digitalmarketplace.auth.users;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*")
@RequestMapping("/users")
public class UserController {
	
	@Autowired UserService userService;	
	
	// ============== POST ==============
		
	@PostMapping("/createAdmin")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> createAdmin(@RequestBody UserDtoRegister admin) throws Exception {	
		try {
			return userService.createAdmin(admin);
		} catch (Exception e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}	
	
	@PostMapping("/createUser")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> createUser(@RequestBody UserDtoRegister user) throws Exception {
		// prova ad eseguire il metodo, se riscontra eccezione ritorna il testo relativo
		try {
			return userService.createUser(user);
		} catch (Exception e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}
	
	// ============== GET ==============
		
	@GetMapping("/getAllInfo")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get all users info", security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<List<UserDtoGetResponse>> findAllUsersInfo() {
		return ResponseEntity.ok(userService.getAllUsersInfo());
	}
		
	@GetMapping("/{id}")
	//@PreAuthorize("isAuthenticated()")
	@Operation(summary = "Get single user info", security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<UserDtoGetResponse> findUserInfo(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserInfo(id));
	}
		
	// ============== PUT/PATCH ==============
	
	@PatchMapping("/{id}/updateAvatarViaUrl")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> updateAvatarViaUrl(@RequestBody UserDtoAvatar dto, @PathVariable Long id) throws Exception {
		try {
			return userService.updateAvatarViaUrl(dto, id);
		} catch (Exception e) {
			return ResponseEntity.ok(e.getMessage());
		}		
	}
	
	@PatchMapping("/{id}/updateAvatarPreSet")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<User> updateAvatarPreSet(@RequestBody String avatar, @PathVariable Long id) {
		return ResponseEntity.ok(userService.updateAvatarPreSet(avatar, id));
	}
		
	@PatchMapping("/{id}/updateProfileInfo")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> updateProfileInfo(@RequestBody UserDtoProfile user, @PathVariable Long id) throws Exception {
		try {
			return userService.updateProfileInfo(user, id);
		} catch (Exception e) {
			return ResponseEntity.ok(e.getMessage());
		}	
	}
		
	@PatchMapping("/{id}/updateCredentials")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> updateCredentials(@RequestBody UserDtoCredentials user, @PathVariable Long id) throws Exception {
		try {
			return userService.updateCredentials(user, id);
		} catch (Exception e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}
	
	// Per un PATCH senza scrittura nei campi di input (FE) omettere il @RequestBody, il metodo verra' lanciato		
	@PatchMapping("/{id}/subscribeMonthly")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> subscribeMonthly(@PathVariable Long id) throws Exception {
		try {
			return userService.beginSubscription(id, 4.90, 30);
		} catch (Exception e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}
	
	@PatchMapping("/{id}/subscribeSemestral")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> subscribeSemestral(@PathVariable Long id) throws Exception {
		try {
			return userService.beginSubscription(id, 24.90, 180);
		} catch (Exception e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}
	
	@PatchMapping("/{id}/subscribeAnnual")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> subscribeAnnual(@PathVariable Long id) throws Exception {
		try {
			return userService.beginSubscription(id, 44.90, 365);
		} catch (Exception e) {
			return ResponseEntity.ok(e.getMessage());
		}
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
		return ResponseEntity.ok("Account deletion successfull. Re-directing you to the Login page...");
	}

}
