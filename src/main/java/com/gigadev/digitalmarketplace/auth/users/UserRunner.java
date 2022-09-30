package com.gigadev.digitalmarketplace.auth.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.gigadev.digitalmarketplace.auth.roles.Role;
import com.gigadev.digitalmarketplace.auth.roles.RoleService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class UserRunner implements ApplicationRunner {
	
	@Autowired UserService userServ;
	@Autowired RoleService roleServ;
	@Autowired @Qualifier("newUser") UserDto user;	
	@Autowired @Qualifier("admin") Role adminRole;
	@Autowired @Qualifier("user") Role userRole;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("--> Application Started Successfully!!!");
		
		roleServ.create(adminRole);
		roleServ.create(userRole);
		userServ.saveAdmin(user);
	}

	
}
