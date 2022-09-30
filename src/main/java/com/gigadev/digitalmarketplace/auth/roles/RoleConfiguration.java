package com.gigadev.digitalmarketplace.auth.roles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RoleConfiguration {
	
	@Bean("admin")
	public Role newAdmin() {
		return new Role(ERole.ROLE_ADMIN);
	}
	
	@Bean("user")
	public Role newUser() {
		return new Role(ERole.ROLE_USER);
	}

}
