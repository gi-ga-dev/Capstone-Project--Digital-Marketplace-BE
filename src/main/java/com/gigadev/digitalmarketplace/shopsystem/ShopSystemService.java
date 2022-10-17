package com.gigadev.digitalmarketplace.shopsystem;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
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

import lombok.extern.java.Log;
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
	
	public ShopSystem getShopSystemById(Long shopId) {	
		// ritorna tutto l'oggetto shop system con all'interno liste
		if(!shopRepo.existsById(shopId)) {
			throw new EntityNotFoundException("Shop System not found...");
		} else return shopRepo.findById(shopId).get();		
	}
	
	public ShopSystemDto getShopSystemBasicInfo(Long shopId) {	
		// ritorna solo proprieta' dto dell'oggetto
		if(!shopRepo.existsById(shopId)) {
			throw new EntityNotFoundException("Shop System not found...");
		} else {
			ShopSystem shopSystem = shopRepo.findById(shopId).get();
			updateSubTotal(shopId);
			return ShopSystemDto
			.builder()
			.id(shopSystem.getId())
			.cartSubtotal(shopSystem.getCartSubtotal())
			.build();
		}
	}
	
	// -------------
	
	public Set<AbstractProduct> getListByShopId(Long shopId, Set<AbstractProduct> list) {		
		// da getListByShopId ottengo lista da ciclare 
		// da getShopSystemBasicInfo aggiorno ed ottengo la prop. cartSubtotal dove ho memorizzato il prezzo totale degli elem nel carrello
		return shopRepo.findById(shopId).get().getList(list);
	}	

	public ShopSystem updateSubTotal(Long shopId) {
		ShopSystem shopSystem = shopRepo.findById(shopId).get();
		// variabile temporanea per riportare totale nella pagina Shopping Cart
		Double subTotal = 0.00;		
		for (AbstractProduct ele : shopSystem.getCartList()) {
			subTotal += ele.getPriceInitial();	
		}			
		shopSystem.setCartSubtotal(subTotal);	
		return shopRepo.save(shopSystem);
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
				log.info("--> " + shopSystem.getCartList().size() + " Product/s in the List");
			} return shopSystem;
		}
	}	
		
	public ShopSystem commitPurchase(Long shopId) {
		if(!shopRepo.existsById(shopId)) {
			throw new EntityNotFoundException("Shop System not implemented...");
		} else { 
			// Prendo ref. shopSystem -> per cartList, user -> per accountBalance (utente che sta acquistando)
			User user = userRepo.findById(shopId).get();
			ShopSystem shopSystem = shopRepo.findById(shopId).get();
			Double subTotal = 0.00;
			Integer prodQnt = 0;
			
			for (AbstractProduct ele : shopSystem.getCartList()) {
				// aggiornare subTotal e prodQnt (senza aver ancora acquistato)
				subTotal += ele.getPriceInitial();	
				prodQnt ++;
				log.info("--> SubTotal: " + subTotal + " Prod. Qnt: " + prodQnt);	
			}
			
			// il subTotal mi serve solo per controllare se l'utente puo' effettuare l'acquisto
			if(user.getAccountBalance() >=  subTotal) {
				// detrarre saldo, incrementare qnt prod. acquistati
				user.setAccountBalance(user.getAccountBalance() - subTotal);
				user.setQntPurchased(user.getQntPurchased() + prodQnt);
				// prendere tutti gli elementi presenti nel carrello, ed agg. nelle liste acquisti/libreria			
				shopSystem.addAllToList(
						shopRepo.findById(shopId).get().getCartList(), 
						shopRepo.findById(shopId).get().getLibraryList(), 
						shopRepo.findById(shopId).get().getHistoryList());
				// dopodiche' cancellare contenuto carrello
				shopSystem.getCartList().clear();
				//shopSystem.getWishList().clear();
				shopRepo.flush();	
			} else throw new EntityNotFoundException("Account Balance insufficient...");
			
			log.info("--> PURCHASE COMPLETED - for Shop System w/ id: " + shopSystem.getId());			
			return shopRepo.save(shopSystem);
		}
	}	
	
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
