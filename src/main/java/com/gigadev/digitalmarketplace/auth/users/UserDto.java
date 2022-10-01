package com.gigadev.digitalmarketplace.auth.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	// dati che devono matchare (in order sequenziale per via del costruttore) 
	// con i campi di input nel form register del front-end
	private String fullName;
	private String email;
	private String username;
	private String password;

}
