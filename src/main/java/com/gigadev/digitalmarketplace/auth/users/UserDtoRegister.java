package com.gigadev.digitalmarketplace.auth.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRegister {
	
	// **[Register Page]** dati che devono matchare (in order sequenziale per via del costruttore) 
	// con i campi di input nel form register del front-end, che saranno inviati nel db
	private String firstName;
	private String lastName;
	private String email;	
	private String userName;
	private String password;
	
	public boolean allEmptyFields() {
		return (this.firstName.length() == 0 ||
				this.lastName.length() == 0 ||
				this.email.length() == 0 ||
				this.userName.length() == 0 ||
				this.password.length() == 0);
	}

}
