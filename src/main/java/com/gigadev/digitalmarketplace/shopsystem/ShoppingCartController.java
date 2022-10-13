package com.gigadev.digitalmarketplace.shopsystem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gigadev.digitalmarketplace.products.AbstractProduct;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/shopping-cart")
public class ShoppingCartController {
	
	@Autowired ShoppingCartService shoppingServ;
	
	// ============== GET ==============
	
	@GetMapping("/getAllShoppingCart")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<List<ShoppingCart>> getAllShoppingCart() {
		return ResponseEntity.ok(shoppingServ.getAllShoppingCart());
	}
	
	// ============== POST (articoli da aggiungere al carrello) ==============
		
	@PostMapping("/{id}/addToCart")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<ShoppingCart> addToCart(@PathVariable Long cartId, @RequestBody AbstractProduct product) {
		return ResponseEntity.ok(shoppingServ.addToCart(cartId, product));
	}
	
	// ============== PATCH/PUT ==============
	
	// ============== DELETE ==============

}
