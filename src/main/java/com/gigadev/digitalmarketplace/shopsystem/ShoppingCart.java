package com.gigadev.digitalmarketplace.shopsystem;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.gigadev.digitalmarketplace.auth.users.User;
import com.gigadev.digitalmarketplace.products.AbstractProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "shopping_cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCart {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// Un carrello ad un solo utente
	@OneToOne
	private User user;	
	
	// un carrello ha molti prodotti
	@OneToMany(cascade = CascadeType.PERSIST)
	//@JoinTable(name = "users_cart_assoc", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "shopping_cart_id"))
	private Set<AbstractProduct> cartList = new HashSet<AbstractProduct>();
	
	public void addToCartList(AbstractProduct product) {
		cartList.add(product);
	}

	
}
