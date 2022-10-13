package com.gigadev.digitalmarketplace.shopsystem;

import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.gigadev.digitalmarketplace.products.AbstractProduct;

@Configuration
public class ShoppingCartConfiguration {
	
//	@Bean("shoppingCart")
//	@Scope("prototype")
//	public ShoppingCart shoppingCart() {
//		// creo lista vuota da assegnare a carrello persistente attribuito alla creazione utente
//		Set<AbstractProduct> productList = new HashSet<AbstractProduct>();
//		// carrello con solo la proprieta lista prodotti nel carrello
//		ShoppingCart cart = new ShoppingCart();
//		cart.setCartList(productList);
//		return cart;
//	}

}
