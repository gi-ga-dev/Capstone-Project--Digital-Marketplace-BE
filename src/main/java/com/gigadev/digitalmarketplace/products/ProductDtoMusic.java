package com.gigadev.digitalmarketplace.products;

import java.time.LocalDate;

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
	private String imgLink;
	private Double price;
	private String title;	
	private String description;
	private String platform;
	private String publisher;
	private LocalDate releaseDate;
	private String language;
	private String genre;
	
	// ProductMusic
	private String artist;
	private String album;
	private String duration;

}
