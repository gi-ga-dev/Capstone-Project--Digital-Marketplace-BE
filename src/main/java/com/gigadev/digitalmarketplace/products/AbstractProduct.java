package com.gigadev.digitalmarketplace.products;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractProduct{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE) // IDENTITY non funziona su classe abstract
	private Long id;	
	private String imgLink;
	private String productType;		
	private Double price;
	// var. per memorizzare prezzo base da visualizzare a fianco del price (senza oppure con sconto)
	private Double priceMemorized; 
	private Integer discount;		
	private String title;	
	private String description;
	private String platform;
	private String publisher;
	private String releaseDate;
	private String language;
	private String genre;
	private Double ratings;
	private Long reviews;
	

}
