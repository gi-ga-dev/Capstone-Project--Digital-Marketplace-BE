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
