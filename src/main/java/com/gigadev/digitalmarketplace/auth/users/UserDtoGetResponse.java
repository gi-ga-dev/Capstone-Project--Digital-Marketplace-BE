package com.gigadev.digitalmarketplace.auth.users;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoGetResponse {
	// Ottengo il get nel front-end di tutti gli Utenti e le loro proprieta'
	private Long id;
	private String firstName;
	private String lastName;	
	private String email;	
	private String userName;		
	private String role;
	
}
