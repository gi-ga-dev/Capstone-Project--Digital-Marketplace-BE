package com.gigadev.digitalmarketplace.shopsystem;

import java.util.List;
import java.util.Set;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.gigadev.digitalmarketplace.auth.users.User;
import com.gigadev.digitalmarketplace.auth.users.UserRepository;
import com.gigadev.digitalmarketplace.products.AbstractProduct;
import com.gigadev.digitalmarketplace.products.AbstractProductRepo;
import com.gigadev.digitalmarketplace.products.EProductPayment;
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
			// aggiorno carrello per riportare proprieta' nella pagina profilo
			updateCart(shopId);
			
			return ShopSystemDto
			.builder()
			.id(shopSystem.getId())
			.cartSubtotal(shopSystem.getCartSubtotal())
			.prodQnt(shopSystem.getProdQnt())
			.build();
		}
	}
		
	public Set<AbstractProduct> getListByShopId(Long shopId, Set<AbstractProduct> list) {		
		// da getListByShopId ottengo lista da ciclare e aggiorno proprieta' carrello
		// da getShopSystemBasicInfo ottengo le cartSubtotal e prodQnt
		return shopRepo.findById(shopId).get().getList(list);
	}	

	// ============== POST ==============
	// ---> Metodi relativi all'acquisto prodotti singoli senza utilizzo dello [Shopping Cart]
	
	public ResponseEntity<ShopSystem> purchaseWithBalance(Long shopId, Long productId) throws Exception {
		// metodo identico al commitPurchase fatta eccezione che acquisto un singolo prodotto, senza ref. carrello
		User user = userRepo.findById(shopId).get();
		ShopSystem shopSystem = shopRepo.findById(shopId).get();
		AbstractProduct prod = productRepo.findById(productId).get();			
		
		if(shopSystem.getLibraryList().contains(prod)) {
			throw new Exception("Product is already in your Library...");				
		} else if(user.getAccountBalance() >= prod.getPrice()) {
			prod.setPaymentMethod(EProductPayment.BALANCE);
			// se ha abbastanza balance spostare prodotto cliccato nella lista library	
			user.setAccountBalance(user.getAccountBalance() - prod.getPrice());
			user.setQntPurchased(user.getQntPurchased() + 1);
			shopSystem.addProductToList(shopSystem.getLibraryList(), prod);
			log.info("--> ADD TO LIST - Product: " + prod.getTitle() + " saved in Library of Shop System w/ id: " + shopSystem.getId());
			shopRepo.flush();
			shopRepo.save(shopSystem);
			return ResponseEntity.ok(shopSystem);
		} else throw new Exception("Credit is insufficient, please recharge your balance.");
	}
	
	public ResponseEntity<ShopSystem> purchaseWithSub(Long shopId, Long productId) throws Exception {
		User user = userRepo.findById(shopId).get();
		ShopSystem shopSystem = shopRepo.findById(shopId).get();
		AbstractProduct prod = productRepo.findById(productId).get();			
		
		if(shopSystem.getLibraryList().contains(prod)) {
			throw new Exception("Product is already in your Library...");				
		} else if(user.getIsSubscribed() == true) {
				prod.setPaymentMethod(EProductPayment.SUBSCRIPTION);
				// se subscribed spostare prodotto cliccato nella lista library senza detrazioni	
				user.setQntPurchased(user.getQntPurchased() + 1);
				shopSystem.addProductToList(shopSystem.getLibraryList(), prod);
				log.info("--> ADD TO LIST - Product: " + prod.getTitle() + " saved in Library of Shop System w/ id: " + shopSystem.getId());
				shopRepo.flush();
				shopRepo.save(shopSystem);
				return ResponseEntity.ok(shopSystem);
		} else throw new Exception("You are not subscribed...");
	}
	
	// ---> Metodi relativi all'acquisto di prodotti multipli utilizzando lo [Shopping Cart]
	
	public ShopSystem updateCart(Long shopId) {
		ShopSystem shopSystem = shopRepo.findById(shopId).get();
		// variabile temporanea per riportare totale nella pagina Shopping Cart
		Double subTotal = 0.00;
		Integer prodQnt = 0;		
		for (AbstractProduct ele : shopSystem.getCartList()) {
			// aggiornare subTotal e prodQnt (senza aver ancora acquistato)
			// Il metodo di pagamento sara' necessariamente Balance perche' siamo nello Shopp. Cart
			ele.setPaymentMethod(EProductPayment.BALANCE);
			subTotal += ele.getPrice();	
			prodQnt ++;
			log.info("--> SubTotal: " + subTotal + " Prod. Qnt: " + prodQnt);	
		}				
		shopSystem.setCartSubtotal(subTotal);
		shopSystem.setProdQnt(prodQnt);		
		shopRepo.flush();
		return shopRepo.save(shopSystem);
	}
	
	public ResponseEntity<ShopSystem> addToCart(Long shopId, Long productId, Set<AbstractProduct> list, Set<AbstractProduct> purchasedList) throws Exception {
		// prendo shopsystem e prodotto tramite id
		ShopSystem shopSystem = shopRepo.findById(shopId).get();
		AbstractProduct prod = productRepo.findById(productId).get();
		
		if(purchasedList.contains(prod)) {
			throw new Exception("Product is already in your Library...");	
		} else if(list.contains(prod)) {
			throw new Exception("Product is already in your Shopping Cart...");	
		} else {
			// salvo la lista con gli elementi al suo interno, nel db
			shopSystem.addProductToList(list, prod);	
			shopRepo.flush();
			shopRepo.save(shopSystem);
			log.info("--> ADD TO CART - Product: " + prod.getTitle() + " saved in List of Shop System w/ id: " + shopSystem.getId());
			return ResponseEntity.ok(shopSystem);
		} 		
	}	
	
	public ResponseEntity<ShopSystem> addToWishList(Long shopId, Long productId, Set<AbstractProduct> list, Set<AbstractProduct> purchasedList) throws Exception {
		// prendo shopsystem e prodotto tramite id
		ShopSystem shopSystem = shopRepo.findById(shopId).get();
		AbstractProduct prod = productRepo.findById(productId).get();
		
		if(purchasedList.contains(prod)) {
			throw new Exception("Product is already in your Library...");	
		} else if(list.contains(prod)) {
			throw new Exception("Product is already in your Wishlist...");	
		} else {
			// salvo la lista con gli elementi al suo interno, nel db
			shopSystem.addProductToList(list, prod);	
			shopRepo.flush();
			shopRepo.save(shopSystem);
			log.info("--> ADD TO WISHLIST - Product: " + prod.getTitle() + " saved in List of Shop System w/ id: " + shopSystem.getId());
			return ResponseEntity.ok(shopSystem);
		} 		
	}	
		
	public ResponseEntity<ShopSystem> commitPurchase(Long shopId) throws Exception {
		// Prendo ref. shopSystem -> per cartList, user -> per accountBalance (utente che sta acquistando)
		User user = userRepo.findById(shopId).get();
		ShopSystem shopSystem = shopRepo.findById(shopId).get();
		// aggiorna price e qnt totale Shopping Cart
		updateCart(shopId); 	
		if(user.getAccountBalance() >=  shopSystem.getCartSubtotal()) {
			// detrarre saldo, incrementare qnt prod. acquistati
			user.setAccountBalance(user.getAccountBalance() - shopSystem.getCartSubtotal());
			user.setQntPurchased(user.getQntPurchased() + shopSystem.getProdQnt());
			// prendere tutti gli elementi presenti nel carrello, ed agg. nelle liste acquisti/libreria			
			shopSystem.addAllToList(shopSystem.getCartList(), shopSystem.getLibraryList());
			// dopodiche' cancellare contenuto carrello
			shopSystem.getCartList().clear();
			shopSystem.getWishList().clear();
			shopRepo.flush();
			shopRepo.save(shopSystem);
			log.info("--> PURCHASE COMPLETED - for Shop System w/ id: " + shopSystem.getId());
			return ResponseEntity.ok(shopSystem);			
		} else throw new Exception("Account Balance insufficient.");	
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
