package com.gigadev.digitalmarketplace.auth.users;

import java.time.LocalDate;
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
	private Long id;
	private String firstName;
	private String lastName;	
	private String email;	
	private String userName;		
	private String role;
	private Integer qntPurchased;
	private Double accountBalance;
	private Boolean isSubscribed;
	private String avatar;
	private LocalDate subStart;
	private LocalDate subEnd;  
	private Integer subTotalTime;
	private Integer subRemaining;
	private ShopSystem shopSystem;	
}
