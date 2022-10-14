package com.gigadev.digitalmarketplace.shopsystem;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gigadev.digitalmarketplace.products.AbstractProduct;
import com.gigadev.digitalmarketplace.products.AbstractProductRepo;
import com.gigadev.digitalmarketplace.products.ProductDtoVideogame;
import com.gigadev.digitalmarketplace.products.ProductVideogame;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShopSystemService {
	
	@Autowired ShopSystemRepository shopRepo;
	@Autowired AbstractProductRepo productRepo;
	
	// ============== GET ==============
		
	public List<ShopSystem> getAllShopSystem() {
		return shopRepo.findAll();
	}
	
	public ShopSystem getShopSystemById(Long id) {	
		// ritorna tutto l'oggetto shop system con all'interno liste
		if(!shopRepo.existsById(id)) {
			throw new EntityNotFoundException("Shop System not found...");
		} else return shopRepo.findById(id).get();		
	}
	
	public Set<AbstractProduct> getCartListById(Long shopId) {
		// dall'id dello shop system risalgo alla lista shopping cart
		ShopSystem shopSystem = shopRepo.findById(shopId).get();
		return shopSystem.getCartList();
	}
	
	// ============== POST ==============
	// post nel carrello di prodotti esistenti (no campi input - no DTO)
	public ShopSystem addToCart(Long shopId, Long productId) {
		if(!shopRepo.existsById(shopId)) {
			throw new EntityNotFoundException("Shop System not implemented...");
		} else { 
			ShopSystem shopSystem = shopRepo.findById(shopId).get();
			// E' necessario passare il prodotto tramite id
			AbstractProduct prod = productRepo.findById(productId).get();
			shopSystem.addToCartList(prod);
			log.info("--> SAVE PRODUCT - Product: " + prod.getTitle() + " saved in Shopping Cart w/ id: " + shopSystem.getId());
			return shopRepo.save(shopSystem);
		}
	}
	
	// ---- AGGIUNGERE ADD TO WISHLIST ECC... ----
	
	
	// ============== PATCH/PUT ==============
	
	// ============== DELETE ==============
	

	

}
