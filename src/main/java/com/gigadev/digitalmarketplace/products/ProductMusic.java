package com.gigadev.digitalmarketplace.products;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products_music")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductMusic extends AbstractProduct {
	
	private Long plays;
	private String artist;
	private String album;
	private String duration;
	
}
