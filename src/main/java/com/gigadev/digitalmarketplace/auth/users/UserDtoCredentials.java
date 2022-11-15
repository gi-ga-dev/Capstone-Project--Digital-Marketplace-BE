package com.gigadev.digitalmarketplace.auth.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoCredentials {
	// Campi modifica Credenziali (Profile page -> Modal)
	private String userName;
	private String password;
	
	public boolean allEmptyFields() {
		return (this.userName.length() == 0 ||
				this.password.length() == 0);
	}

}
