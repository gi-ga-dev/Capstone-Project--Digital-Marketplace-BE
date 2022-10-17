package com.gigadev.digitalmarketplace.shopsystem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopSystemDto {
	private Long id;
	private Double cartSubtotal;
	private Integer prodQnt;
}
