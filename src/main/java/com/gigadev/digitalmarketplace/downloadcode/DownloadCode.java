package com.gigadev.digitalmarketplace.downloadcode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.gigadev.digitalmarketplace.products.ProductVideogame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "download_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class DownloadCode {
	// [Response]
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// retailer a cui e' collegato il codice
	private String retailer;
	
	private String code;
	
	@OneToOne
	private ProductVideogame videogame;

}
