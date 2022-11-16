package com.gigadev.digitalmarketplace.shopsystem;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gigadev.digitalmarketplace.products.AbstractProduct;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*")
@RequestMapping("/shop-system")
public class ShopSystemController {
	
	@Autowired ShopSystemService shopServ;
	@Autowired ShopSystemRepository shopRepo;
	
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
		return ResponseEntity.ok(shopServ.getShopSystemById(id));
	}	
	
	@GetMapping("/{id}/getShopSystemBasicInfo")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<ShopSystemDto> getShopSystemBasicInfo(@PathVariable Long id) {
		return ResponseEntity.ok(shopServ.getShopSystemBasicInfo(id));
	}	
	
	// ---------------------
	
	@GetMapping("/{shopId}/getCartListByShopId")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<Set<AbstractProduct>> getCartListByShopId(@PathVariable Long shopId) {
		return ResponseEntity.ok(shopServ.getListByShopId(shopId, shopRepo.findById(shopId).get().getCartList()));
	}
	
	@GetMapping("/{shopId}/getWishListByShopId")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	// con Pre-Authorize genera errore autenticazione
	public ResponseEntity<Set<AbstractProduct>> getWishListByShopId(@PathVariable Long shopId) {
		return ResponseEntity.ok(shopServ.getListByShopId(shopId, shopRepo.findById(shopId).get().getWishList()));
	}
	
	@GetMapping("/{shopId}/getLibraryListByShopId")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<Set<AbstractProduct>> getLibraryListByShopId(@PathVariable Long shopId) {
		return ResponseEntity.ok(shopServ.getListByShopId(shopId, shopRepo.findById(shopId).get().getLibraryList()));
	}
		
	// ============== POST (articoli da aggiungere alle liste) ==============
	
	@PostMapping("/{shopId}/{productId}/purchaseWithSub")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> purchaseWithSub(@PathVariable Long shopId, @PathVariable Long productId) throws Exception {
		try {
			return shopServ.purchaseWithSub(shopId, productId);
		} catch (Exception e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}
	
	@PostMapping("/{shopId}/{productId}/purchaseWithBalance")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> purchaseWithBalance(@PathVariable Long shopId, @PathVariable Long productId) throws Exception {
		try {
			return shopServ.purchaseWithBalance(shopId, productId);
		} catch (Exception e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}
		
	@PostMapping("/{shopId}/{productId}/addToCart")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	// con Pre-Authorize genera errore autenticazione
	public ResponseEntity<?> addToCart(@PathVariable Long shopId, @PathVariable Long productId) throws Exception {
		try {
			return shopServ.addToCart(shopId, productId, 
					shopRepo.findById(shopId).get().getCartList(), shopRepo.findById(shopId).get().getLibraryList());
		} catch (Exception e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}
	
	@PostMapping("/{shopId}/{productId}/addToWishList")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> addToWishList(@PathVariable Long shopId, @PathVariable Long productId) throws Exception {
		try {
			return shopServ.addToWishList(shopId, productId, 
					shopRepo.findById(shopId).get().getWishList(), shopRepo.findById(shopId).get().getLibraryList());
		} catch (Exception e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}
	
	@PostMapping("/{shopId}/commitPurchase")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> commitPurchase(@PathVariable Long shopId) throws Exception {		
		try {
			return shopServ.commitPurchase(shopId);
		} catch (Exception e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}
	
	// ============== PATCH/PUT ==============
	
	// ============== DELETE ==============
	
	@DeleteMapping("/{shopId}/{productId}/deleteFromCart")
	@Operation(summary = "Delete Product from Cart (by shopId and productId)",security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<String> deleteFromCart(@PathVariable Long shopId, @PathVariable Long productId) {
		shopServ.deleteFromList(shopId, productId, shopRepo.findById(shopId).get().getCartList());
		return ResponseEntity.ok("Delete from Shopping Cart successfull");
	}
	
	@DeleteMapping("/{shopId}/{productId}/deleteFromWishList")
	@Operation(summary = "Delete Product from WishList (by shopId and productId)",security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<String> deleteFromWishList(@PathVariable Long shopId, @PathVariable Long productId) {
		shopServ.deleteFromList(shopId, productId, shopRepo.findById(shopId).get().getWishList());
		return ResponseEntity.ok("Delete from Wishlist successfull");
	}
	
	
}
