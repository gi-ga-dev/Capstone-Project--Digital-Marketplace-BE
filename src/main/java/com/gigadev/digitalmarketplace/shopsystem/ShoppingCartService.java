package com.gigadev.digitalmarketplace.shopsystem;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gigadev.digitalmarketplace.products.AbstractProduct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShoppingCartService {
	
	@Autowired ShoppingCartRepo cartRepo;
	
	// ============== GET ==============
	
	public ShoppingCartDtoList getCartList(Long id) {
		ShoppingCart cart = cartRepo.findById(id).get();
		return ShoppingCartDtoList
				.builder()
				.cartList(cart.getCartList())				
				.build();
	}
	
	public List<ShoppingCart> getAllShoppingCart() {
		return cartRepo.findAll();
	}
	
	// ============== POST ==============
	
	public ShoppingCart addToCart(Long cartId, AbstractProduct product) {
		// salva un prodotto nel carrello relativo allo user
		if(!cartRepo.existsById(cartId)) {
			throw new EntityNotFoundException("Cart does not exist...");
		} else { 
			// prendo id del carrello tramite local (FE)
			ShoppingCart finalCart = cartRepo.findById(cartId).get();
			// aggiungo prodotto alla [lista] dell'oggetto carrello
			finalCart.addToCartList(product);
			log.info("--> SAVE PRODUCT - Product: " + product.getTitle() + "in Cart of user: " + finalCart.getUser());
			return cartRepo.save(finalCart);
		}
	}
	
	
	// ============== PATCH/PUT ==============
	
	// ============== DELETE ==============
	

	

}
