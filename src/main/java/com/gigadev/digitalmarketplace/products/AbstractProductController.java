package com.gigadev.digitalmarketplace.products;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*")
@RequestMapping("/products")
public class AbstractProductController {
	
	@Autowired AbstractProductService productServ;
	
	// ============== GET ==============
	
	@GetMapping("/getAllProducts")
	@Operation(summary = "Get List of all Products", security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<List<AbstractProduct>> getAllProducts() {
		return ResponseEntity.ok(productServ.getAllProducts());
	}
	
	@GetMapping("/getAllVideogames")
	@Operation(summary = "Get List of all Videogames", security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<List<ProductVideogame>> getAllVideogames() {
		return ResponseEntity.ok(productServ.getAllVideogames());
	}
	
	@GetMapping("/getAllMusic")
	@Operation(summary = "Get List of all Music", security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<List<ProductMusic>> getAllMusic() {
		return ResponseEntity.ok(productServ.getAllMusic());
	}
	
	@GetMapping("/getAllBooks")
	@Operation(summary = "Get List of all Books", security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<List<ProductBook>> getAllBooks() {
		return ResponseEntity.ok(productServ.getAllBooks());
	}
	
	// ----------------
	
	@GetMapping("/{id}/getProductById")
	@Operation(summary = "Get Product By Id", security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<AbstractProduct> getProductById(@PathVariable Long id) {
		return ResponseEntity.ok(productServ.getProductById(id));
	}
		
	// ============== POST (articoli acquistabili) ==============
		
	@PostMapping("/videogames/saveVideogame")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<AbstractProduct> saveVideogame(@RequestBody ProductDtoVideogame dto) throws Exception {
		return ResponseEntity.ok(productServ.saveVideogame(dto));
	}
	
	@PostMapping("/music/saveMusic")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<AbstractProduct> saveMusic(@RequestBody ProductDtoMusic dto) throws Exception {
		return ResponseEntity.ok(productServ.saveMusic(dto));
	}
	
	@PostMapping("/books/saveBook")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<AbstractProduct> saveBook(@RequestBody ProductDtoBook dto) throws Exception {
		return ResponseEntity.ok(productServ.saveBook(dto));
	}
	
	// ============== PATCH/PUT ==============
	
	// ============== DELETE ==============
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		productServ.deleteProduct(id);
		return ResponseEntity.ok("- DELETE - successfull");
	}

}
