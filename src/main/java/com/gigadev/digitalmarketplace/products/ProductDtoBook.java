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
public class ProductDtoBook {
	
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
	
	// ProductBook
	
	private Integer pages;
	private String isbnCode;
	private String bookSeries;
	private String author;

}
