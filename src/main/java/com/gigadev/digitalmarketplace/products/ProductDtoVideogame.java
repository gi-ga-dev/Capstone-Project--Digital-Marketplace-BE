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
public class ProductDtoVideogame {
	
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
	
	// ProductVideogame
	private String vgSeries;
	private String developer;
	private Integer ageRecommendation;
	private Integer players;
	private String coopPlay;
	private String controllerSupport;
	private String subtitles;
	private Integer requiredSpace;
	
	public boolean allEmptyFields() {
		// se anche solo uno dei campi e' null return true
		return (this.imgLink.length() == 0 || 
				this.price == null ||
				this.title.length() == 0 ||
				this.description.length() == 0 ||
				this.platform.length() == 0 ||
				this.publisher.length() == 0 ||
				this.releaseDate == null ||
				this.language.length() == 0 ||
				this.genre.length() == 0 ||
				this.vgSeries.length() == 0 ||
				this.developer.length() == 0 ||
				this.ageRecommendation == null ||
				this.players == null ||
				this.coopPlay.length() == 0 ||
				this.controllerSupport.length() == 0 ||
				this.subtitles.length() == 0 ||
				this.requiredSpace == null);
	}
	

}
