package com.gigadev.digitalmarketplace.auth.users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class UserConfiguration {
		
	@Bean("systemAdmin")	
	public UserDtoRegister systemAdmin() {
		UserDtoRegister admin = new UserDtoRegister("gino", "fettuccino", "gino.fett@gmail.com", "gino", "12345");
		return admin;
	}	
}
