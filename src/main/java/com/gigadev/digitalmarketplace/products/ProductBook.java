package com.gigadev.digitalmarketplace.products;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products_books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductBook extends AbstractProduct {
	
	private Long read;
	private Integer pages;
	private String isbnCode;
	private String bookSeries;
	private String author;

		
}
