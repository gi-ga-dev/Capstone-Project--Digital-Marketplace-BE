package com.gigadev.digitalmarketplace.auth.roles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RoleConfiguration {
	
	@Bean("roleAdmin")
	@Scope("prototype")
	public Role roleAdmin() {
		return new Role(ERole.ROLE_ADMIN);
	}
	
	@Bean("roleUser")
	@Scope("prototype")
	public Role roleUser() {
		return new Role(ERole.ROLE_USER);
	}

}
