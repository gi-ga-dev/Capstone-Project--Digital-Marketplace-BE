package com.gigadev.digitalmarketplace.auth.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
	// Campi modifica Informazioni Utente (Profile page -> Modal)
	private String firstName;
	private String lastName;
	private String email;

}
