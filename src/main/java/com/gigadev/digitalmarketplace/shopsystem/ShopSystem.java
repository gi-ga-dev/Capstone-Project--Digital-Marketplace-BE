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
import javax.persistence.ManyToMany;
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
@Table(name = "shop_system")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopSystem {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// Id utente, shop system id delle liste partono da 1 per ogni obj istanziato
	private Long id;	
	
	// Uno shop system per un solo utente
	@OneToOne
	private User user;	
	
	private Double cartSubtotal;
		
	// ManyToMany per fare in modo che piu' utenti possano avere gli stessi prodotti nel carrello
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "assoc_shopsystem_cartlist", joinColumns = @JoinColumn(name = "shop_system_id"), inverseJoinColumns = @JoinColumn(name = "cart_prod_id"))
	private Set<AbstractProduct> cartList = new HashSet<AbstractProduct>();
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "assoc_shopsystem_librarylist", joinColumns = @JoinColumn(name = "shop_system_id"), inverseJoinColumns = @JoinColumn(name = "library_prod_id"))
	private Set<AbstractProduct> libraryList = new HashSet<AbstractProduct>();
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "assoc_shopsystem_historylist", joinColumns = @JoinColumn(name = "shop_system_id"), inverseJoinColumns = @JoinColumn(name = "history_prod_id"))
	private Set<AbstractProduct> historyList = new HashSet<AbstractProduct>();
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "assoc_shopsystem_wishlist", joinColumns = @JoinColumn(name = "shop_system_id"), inverseJoinColumns = @JoinColumn(name = "wishlist_prod_id"))
	private Set<AbstractProduct> wishList = new HashSet<AbstractProduct>();
	
	// -----------------
	
	public void addProductToList(Set<AbstractProduct> list, AbstractProduct product) {
		list.add(product);
	}	
		
	public Set<AbstractProduct> getList(Set<AbstractProduct> list) {
		return list;
	}
	
}
