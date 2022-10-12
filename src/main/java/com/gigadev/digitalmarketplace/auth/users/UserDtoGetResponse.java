package com.gigadev.digitalmarketplace.auth.users;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gigadev.digitalmarketplace.prod.AbstractProduct;
import com.gigadev.digitalmarketplace.prod.ProductBook;
import com.gigadev.digitalmarketplace.prod.ProductMusic;
import com.gigadev.digitalmarketplace.prod.ProductVideogame;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoGetResponse {
	// Ottengo il get nel front-end di tutti gli Utenti e le loro proprieta'
	private Long id;
	private String firstName;
	private String lastName;	
	private String email;	
	private String userName;		
	private String role;
	private Double accountBalance;
	private Boolean isSubscribed;
	private LocalDate subStart;
	private LocalDate subEnd;  
	private Integer subTotalTime;
	private Integer subRemaining;
	private Set<AbstractProduct> purchaseHistory;
	private Set<ProductVideogame> purchasedVg;
	private Set<ProductMusic> purchasedMusic;
	private Set<ProductBook> purchasedBook;

}
