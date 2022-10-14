package com.gigadev.digitalmarketplace.products;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gigadev.digitalmarketplace.auth.users.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products_videogames")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVideogame extends AbstractProduct {
	
	private Long downloads; //?
	private Long uniqueCode;
	private String vgSeries;
	private String dlc;     //?
	private String developer;
	private Integer ageRecommendation;
	private Integer players;
	private String coopPlay;
	private String controllerSupport;
	private String subtitles;
	private Integer minResolution;
	private Integer maxResolution;
	private Integer requiredSpace;
	
}
