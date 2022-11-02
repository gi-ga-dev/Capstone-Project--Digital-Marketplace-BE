package com.gigadev.digitalmarketplace.products;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.gigadev.digitalmarketplace.downloadcode.DownloadCode;

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
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private EProductPayment paymentMethod;
		
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
	private LocalDate releaseDate;
	private String language;
	private String genre;
	private Double ratings;
	private Long reviews;	

}
