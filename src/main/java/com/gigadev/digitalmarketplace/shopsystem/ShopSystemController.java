package com.gigadev.digitalmarketplace.shopsystem;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import com.gigadev.digitalmarketplace.products.AbstractProduct;
import com.gigadev.digitalmarketplace.products.ProductBook;
import com.gigadev.digitalmarketplace.products.ProductDtoVideogame;
import com.gigadev.digitalmarketplace.products.ProductMusic;
import com.gigadev.digitalmarketplace.products.ProductVideogame;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/shop-system")
public class ShopSystemController {
	
	@Autowired ShopSystemService shopServ;
	
	// ============== GET ==============
	
	@GetMapping("/getAllShopSystem")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<List<ShopSystem>> getAllShopSystem() {
		return ResponseEntity.ok(shopServ.getAllShopSystem());
	}
	
	@GetMapping("/{id}/getShopSystemById")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<ShopSystem> getShopSystemById(@PathVariable Long id) {
		// restituisce lo shop system di un utente che al suo interno ha tutte le liste
		return ResponseEntity.ok(shopServ.getShopSystemById(id));
	}	
	
	@GetMapping("/{shopId}/getCartListById")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<Set<AbstractProduct>> getCartListById(@PathVariable Long shopId) {
		return ResponseEntity.ok(shopServ.getCartListById(shopId));
	}
	
	// ============== POST (articoli da aggiungere al carrello) ==============
		
	@PostMapping("/{shopId}/{productId}/addToCart")
	// con Pre-Authorize genera errore autenticazione
	public ResponseEntity<ShopSystem> addToCart(@PathVariable Long shopId, @PathVariable Long productId) {
		return ResponseEntity.ok(shopServ.addToCart(shopId, productId));
	}
	
	// ============== PATCH/PUT ==============
	
	// ============== DELETE ==============
	
	@DeleteMapping("/{shopId}/{productId}/deleteFromCart")
	@Operation(summary = "Delete Product from Cart by shopId and productId",security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<String> deleteFromCart(@PathVariable Long shopId, @PathVariable Long productId) {
		shopServ.deleteFromCart(shopId, productId);
		return ResponseEntity.ok("- DELETE - successfull");
	}
	
	
}
