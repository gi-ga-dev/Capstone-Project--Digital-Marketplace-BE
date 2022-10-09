package com.gigadev.digitalmarketplace.auth.users;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.gigadev.digitalmarketplace.auth.roles.Role;
import com.gigadev.digitalmarketplace.prod.AbstractProduct;
import com.gigadev.digitalmarketplace.prod.ProductBook;
import com.gigadev.digitalmarketplace.prod.ProductMusic;
import com.gigadev.digitalmarketplace.prod.ProductVideogame;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class User {

	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@NotBlank @Size(max = 50) private String firstName;	
	@NotBlank @Size(max = 50) private String lastName;	
	@NotBlank @Size(max = 50) private String email;
	@NotBlank @Size(max = 50) private String userName;
	@NotBlank @Size(max = 90) private String password; // lunghezza token 60 char.
	
	@Builder.Default private Integer qntPurchased = 0;
	@Builder.Default private Double accountBalance = 100.00;
	@Builder.Default private Boolean isSubscribed = false;
	private LocalDate subStart;
	private LocalDate subEnd;  
	private Integer subTotalTime;
	private Integer subRemaining;	
	
	// @ManyToMany e' necessaria in modo da creare molti utenti con i ruoli istanziati nel runner
	// questo JoinTable definisce solo i nomi del table/columns dell'associazione user/role
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles_assoc", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();
	
	@OneToMany
	@JoinColumn(name = "user_id")
	private Set<AbstractProduct> purchaseHistory = new HashSet<AbstractProduct>(); // tutte le tipol. di prodotti
	
	@OneToMany
	@JoinColumn(name = "user_id")
	private Set<ProductVideogame> videogamesList = new HashSet<ProductVideogame>();
	
	@OneToMany
	@JoinColumn(name = "user_id")
	private Set<ProductMusic> musicList = new HashSet<ProductMusic>();
	
	@OneToMany
	@JoinColumn(name = "user_id")
	private Set<ProductBook> bookList = new HashSet<ProductBook>();
		
	public void addRole(Role role) {
		roles.add(role);
	}
	
	public void addToPurchaseHistory(AbstractProduct product) {
		purchaseHistory.add(product);
	}
	
	public void addVideogame(ProductVideogame videogame) {
		videogamesList.add(videogame);
	}
	
	public void addMusic(ProductMusic music) {
		musicList.add(music);
	}
	
	public void addBook(ProductBook book) {
		bookList.add(book);
	}

}
