package com.gigadev.digitalmarketplace.auth.users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {
		
	@Bean("systemAdmin")	
	public UserDtoRegister systemAdmin() {
		UserDtoRegister admin = new UserDtoRegister("admin", "system", "admin.system@gmail.com", "gino", "12345");
		return admin;
	}	
}
