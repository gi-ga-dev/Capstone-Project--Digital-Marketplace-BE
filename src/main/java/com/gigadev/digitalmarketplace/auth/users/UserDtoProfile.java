package com.gigadev.digitalmarketplace.auth.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoProfile {
	// Campi modifica Informazioni Utente (Profile page -> Modal)
	private String firstName;
	private String lastName;
	private String email;
	
	public boolean allEmptyFields() {
		return (this.firstName.length() == 0 ||
				this.lastName.length() == 0 ||
				this.email.length() == 0);
	}

}
