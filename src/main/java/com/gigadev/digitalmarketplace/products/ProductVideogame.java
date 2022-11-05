package com.gigadev.digitalmarketplace.products;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.gigadev.digitalmarketplace.downloadcode.DownloadCode;
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
	
	// i codici per riscattare i giochi da vari retailers
	//private Set<DownloadCode> downloadCodes;
	@OneToOne
	private DownloadCode downloadCode;
	
	//private String downloadCode;
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
