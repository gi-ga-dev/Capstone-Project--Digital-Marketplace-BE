package com.gigadev.digitalmarketplace.shopsystem;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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
			// prendo shopsystem e prodotto tramite id
			ShopSystem shopSystem = shopRepo.findById(shopId).get();
			AbstractProduct prod = productRepo.findById(productId).get();
			
			if(shopSystem.getCartList().contains(prod)) {
				throw new EntityExistsException("Product already in Shopping Cart...");				
			} else {
				// salvo il carrello con gli elementi al suo interno, nel db
				shopSystem.addToCartList(prod);		
				shopRepo.save(shopSystem);
				log.info("--> SAVE PRODUCT - Product: " + prod.getTitle() + " saved in Shopping Cart w/ id: " + shopSystem.getId());
			} return shopSystem;
		}
	}
	
	// ---- AGGIUNGERE ADD TO WISHLIST ECC... ----
	
	
	// ============== PATCH/PUT ==============
	
	// ============== DELETE ==============
	
	public void deleteFromCart(Long shopId, Long productId) {
		// eliminare oggetto presente nel carrello (non dalla lista prodotti)
		if(!shopRepo.existsById(shopId)) {
			throw new EntityNotFoundException("Shop system not found...");
		} else {	
			ShopSystem shopSystem = shopRepo.findById(shopId).get();
			// rimuove obj dalla lista se rispetta la condizione (id oggetto e' uguale all'id prodotto - ogg. cliccato)
			shopSystem.getCartList().removeIf(ele -> ele.getId().equals(productId));			
			shopRepo.flush();
			log.info("--> DELETE FROM CART - product with id: " + productId + " from shop system with id: " + shopSystem.getId());
		}
	}
	

	

}
