package com.gigadev.digitalmarketplace.prod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDtoMusic {
	
	// AbstractProduct
	private Double priceInitial;
	private Double priceActual;
	private String title;	
	private String description;
	private String platform;
	private String publisher;
	private String releaseDate;
	private String language;
	private String genre;
	
	// ProductMusic
	private Long isrcCode;
	private String artist;
	private String album;
	private String duration;

}
