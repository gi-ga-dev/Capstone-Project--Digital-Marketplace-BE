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
public abstract class AbstractProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE) // IDENTITY non funziona su classe abstract
	private Long id;	
	private String productType;	
	
	// lo scrivo nel campo di input alla creazione oggetto, 
	// serve per tenere memorizzato il prezzo iniziale nel caso di uno sconto
	private Double priceInitial;
	
	// inizialmente sono uguali, ma priceFinal sara' quello sempre visualizzato
	// che cambiera a seconda se c'e' promozione oppure no
	private Double priceFinal; 

	// if(isDiscounted) --> priceFinal = priceInitial - (priceInitial/100*discount)
	private Integer discount;	
	//private Double priceDiscounted = priceInitial - (priceInitial/100*discount); 
	
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
