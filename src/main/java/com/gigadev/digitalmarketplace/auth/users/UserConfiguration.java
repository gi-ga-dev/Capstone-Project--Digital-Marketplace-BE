package com.gigadev.digitalmarketplace.auth.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gigadev.digitalmarketplace.auth.roles.Role;

@Configuration
public class UserConfiguration {
	
	@Autowired @Qualifier("admin") Role admin;
	@Autowired @Qualifier("user") Role user;
	
	@Bean("newUser")
	public User newUser() {
		User user = new User("user", "password");
		user.addRole(admin);
		return user;
	}
	

}
