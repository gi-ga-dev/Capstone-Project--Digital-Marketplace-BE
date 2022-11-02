package com.gigadev.digitalmarketplace.auth.users;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.gigadev.digitalmarketplace.auth.roles.Role;
import com.gigadev.digitalmarketplace.shopsystem.ShopSystem;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank @Size(max = 50) private String firstName;	
	@NotBlank @Size(max = 50) private String lastName;	
	@NotBlank @Size(max = 50) private String email;
	@NotBlank @Size(max = 50) private String userName;
	@NotBlank @Size(max = 90) private String password; // lunghezza token 60 char.
	
	@Builder.Default private Integer qntPurchased = 0;
	@Builder.Default private Double accountBalance = 0.00;
	@Builder.Default private Boolean isSubscribed = false;
	private String avatar;
	private LocalDate subStart;
	private LocalDate subEnd;  
	private Integer subTotalTime;
	private Integer subRemaining;
	
	@OneToOne(mappedBy = "user")
	//@JoinTable(name = "assoc_users_shopsystem", joinColumns = @JoinColumn(name = "shop_system_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private ShopSystem shopSystem;
	
	// @ManyToMany e' necessaria in modo da creare molti utenti con i ruoli istanziati nel runner
	// JoinTable definisce solo i nomi del table/columns dell'associazione user/role
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "assoc_users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();	
		
	public void addRole(Role role) {
		roles.add(role);
	}
	
}
