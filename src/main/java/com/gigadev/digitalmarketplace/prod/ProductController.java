package com.gigadev.digitalmarketplace.prod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/products")
public class ProductController {
	
	@Autowired ProductService productServ;
	
	// ============== GET ==============
	
	// ============== POST ==============
	
	@PostMapping("/videogames/saveVideogame")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<AbstractProduct> saveVideogame(@RequestBody ProductDtoVideogame dto) {
		return ResponseEntity.ok(productServ.saveVideogame(dto));
	}
	
	// ============== PATCH/PUT ==============
	
	// ============== DELETE ==============

}
