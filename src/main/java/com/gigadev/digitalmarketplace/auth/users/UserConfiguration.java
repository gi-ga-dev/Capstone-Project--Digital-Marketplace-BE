package com.gigadev.digitalmarketplace.auth.users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class UserConfiguration {
		
	@Bean("newUser")	
	public UserDto newUser() {
		UserDto user = new UserDto("gino fettuccino", "gino.fett@gmail.com", "gino", "12345");
		return user;
	}
	

}
