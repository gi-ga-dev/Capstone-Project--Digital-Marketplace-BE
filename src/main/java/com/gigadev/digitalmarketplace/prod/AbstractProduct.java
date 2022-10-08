package com.gigadev.digitalmarketplace.prod;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.gigadev.digitalmarketplace.auth.users.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;	
	private Double priceInitial;
	private Double priceActual;
	private Double discount;
	private String title;	
	private String description;
	private String platform;
	private String publisher;
	private String releaseDate;
	private String language;
	private String genre;
	private Double ratings;
	private Long reviews;

	@ManyToOne
	private User user;

}
