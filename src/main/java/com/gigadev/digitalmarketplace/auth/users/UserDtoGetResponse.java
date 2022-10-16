package com.gigadev.digitalmarketplace.auth.users;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.gigadev.digitalmarketplace.products.AbstractProduct;
import com.gigadev.digitalmarketplace.products.ProductBook;
import com.gigadev.digitalmarketplace.products.ProductMusic;
import com.gigadev.digitalmarketplace.products.ProductVideogame;
import com.gigadev.digitalmarketplace.shopsystem.ShopSystem;
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
	private Integer qntPurchased;
	private Double accountBalance;
	private Boolean isSubscribed;
	private LocalDate subStart;
	private LocalDate subEnd;  
	private Integer subTotalTime;
	private Integer subRemaining;
	private ShopSystem shopSystem;
	
}
