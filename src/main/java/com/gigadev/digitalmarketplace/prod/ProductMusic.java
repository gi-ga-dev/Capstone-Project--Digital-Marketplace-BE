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
@Table(name = "music")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductMusic extends AbstractProduct {
	
	private Long plays;
	private Long isrcCode;
	private String artist;
	private String album;
	private String duration;
	
	@ManyToOne
	private User user;

}
