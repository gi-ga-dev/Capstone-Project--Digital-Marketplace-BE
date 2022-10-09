package com.gigadev.digitalmarketplace.auth.users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class UserConfiguration {
		
	@Bean("newUser")	
	public UserDtoRegister newUser() {
		UserDtoRegister user = new UserDtoRegister("gino", "fettuccino", "gino.fett@gmail.com", "gino", "12345");
		return user;
	}	
}
