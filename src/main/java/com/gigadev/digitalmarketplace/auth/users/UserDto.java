package com.gigadev.digitalmarketplace.auth.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	// dati che devono matchare con i campi di input nel register del front-end
	private String fullName;
	private String email;
	private String username;
	private String password;

}
