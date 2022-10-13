package com.gigadev.digitalmarketplace.shopsystem;

import java.util.HashSet;
import java.util.Set;
import com.gigadev.digitalmarketplace.auth.users.User;
import com.gigadev.digitalmarketplace.products.AbstractProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDtoList {
		
	private Long id;	
	private Set<AbstractProduct> cartList = new HashSet<AbstractProduct>();

}
