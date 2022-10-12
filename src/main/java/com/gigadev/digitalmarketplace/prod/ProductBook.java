package com.gigadev.digitalmarketplace.prod;

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
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductBook extends AbstractProduct {
	
	private Long read;
	private Long isbnCode;
	private String bookSeries;
	private String author;
		
}
