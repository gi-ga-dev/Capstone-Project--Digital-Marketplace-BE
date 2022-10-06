package com.gigadev.digitalmarketplace.auth.users;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialsDto {
	// Campi modifica Credenziali (Profile page -> Modal)
	@NotBlank
	private String userName;
	@NotBlank
	private String password;

}
