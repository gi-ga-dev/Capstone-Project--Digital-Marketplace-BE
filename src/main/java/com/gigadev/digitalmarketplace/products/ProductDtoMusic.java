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
				this.artist.length() == 0 ||
				this.album.length() == 0 ||
				this.duration.length() == 0);
	}

}
