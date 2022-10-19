package com.gigadev.digitalmarketplace.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDtoVideogame {
	
	// AbstractProduct
	private String imgLink;
	private Double price;
	private String title;	
	private String description;
	private String platform;
	private String publisher;
	private String releaseDate;
	private String language;
	private String genre;	
	
	// ProductVideogame
	private String vgSeries;
	private String developer;
	private Integer ageRecommendation;
	private Integer players;
	private String coopPlay;
	private String controllerSupport;
	private String subtitles;
	private Integer requiredSpace;
	
	

}
