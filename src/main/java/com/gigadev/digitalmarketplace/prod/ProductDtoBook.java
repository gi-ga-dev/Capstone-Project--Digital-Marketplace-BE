package com.gigadev.digitalmarketplace.prod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDtoBook {
	
	// AbstractProduct
	private Double priceInitial;
	private String title;	
	private String description;
	private String platform;
	private String publisher;
	private String releaseDate;
	private String language;
	private String genre;
	
	// ProductBook
	private Long isbnCode;
	private String bookSeries;
	private String author;

}
