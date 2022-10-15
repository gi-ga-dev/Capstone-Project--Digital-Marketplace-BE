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

import com.fasterxml.jackson.databind.node.LongNode;
import com.gigadev.digitalmarketplace.auth.users.User;
import com.gigadev.digitalmarketplace.auth.users.UserRepository;
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
	@Autowired UserRepository userRepo;
	
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
	
	public Set<AbstractProduct> getListByShopId(Long shopId, Set<AbstractProduct> list) {
		// dall'id dello shop system risalgo alla lista 
		return shopRepo.findById(shopId).get().getList(list);
	}	
		
	// ============== POST ==============
	// post nel carrello di prodotti esistenti (no campi input - no DTO)
	
	public ShopSystem addToList(Long shopId, Long productId, Set<AbstractProduct> list) {
		if(!shopRepo.existsById(shopId)) {
			throw new EntityNotFoundException("Shop System not implemented...");
		} else { 
			// prendo shopsystem e prodotto tramite id
			ShopSystem shopSystem = shopRepo.findById(shopId).get();
			AbstractProduct prod = productRepo.findById(productId).get();
			
			if(list.contains(prod)) {
				throw new EntityExistsException("Product already in List...");				
			} else {
				// salvo la lista con gli elementi al suo interno, nel db
				shopSystem.addProductToList(list, prod);		
				shopRepo.save(shopSystem);
				log.info("--> ADD TO LIST - Product: " + prod.getTitle() + " saved in List of Shop System w/ id: " + shopSystem.getId());
			} return shopSystem;
		}
	}	
	
//	public ShopSystem purchaseProduct(Long shopId, Long userId, Set<AbstractProduct> cartList, Set<AbstractProduct> purchasingList) {
//		// shopId per risalire alle liste, userId per detrarre denaro
//		if(!shopRepo.existsById(shopId)) {
//			throw new EntityNotFoundException("Shop System not implemented...");
//		} else { 
//			ShopSystem shopSystem = shopRepo.findById(shopId).get();
//			User user = userRepo.findById(userId).get();
//			// prendere tutti gli elementi presenti nel carrello, ed agg. nella lista acquisti/libreria
//			shopSystem.addAllToList(cartList, purchasingList);
//						
//		}
//	}
	
	
	// ============== PATCH/PUT ==============
	
	// ============== DELETE ==============
	
	public void deleteFromList(Long shopId, Long productId, Set<AbstractProduct> list) {
		// eliminare oggetto presente nel carrello (non dalla lista prodotti)
		if(!shopRepo.existsById(shopId)) {
			throw new EntityNotFoundException("Shop system not found...");
		} else {	
			ShopSystem shopSystem = shopRepo.findById(shopId).get();
			// rimuove obj dalla lista se rispetta la condizione (id oggetto e' uguale all'id prodotto - ogg. cliccato)
			list.removeIf(ele -> ele.getId().equals(productId));			
			shopRepo.flush();
			log.info("--> DELETE FROM LIST - product with id: " + productId + " from shop system with id: " + shopSystem.getId());
		}
	}
	

	

}
