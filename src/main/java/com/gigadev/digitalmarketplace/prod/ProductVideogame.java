package com.gigadev.digitalmarketplace.prod;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gigadev.digitalmarketplace.auth.users.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "videogames")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVideogame extends AbstractProduct {
	
	private Long downloads;
	private Long uniqueCode;
	private String vgSeries;
	private String dlc;
	private String developer;
	private Integer ageRecommendation;
	private Integer players;
	private Boolean coopPlay;
	private Boolean controllerSupport;
	private String subtitles;
	// pc specifications:
	private Integer minResolution;
	private Integer maxResolution;
	private Integer requiredSpace;
	private String minOS;
	private String minCPU;
	private String minGPU;
	private String minDirectX;
	private String recOS;
	private String recCPU;
	private String recGPU;
	private String recDirectX;
	
	@ManyToOne
	private User user;
	
}
