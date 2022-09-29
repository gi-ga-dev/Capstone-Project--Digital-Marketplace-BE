package com.gigadev.digitalmarketplace.auth.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class UserRunner implements ApplicationRunner {
	
	UserService userServ;
	@Autowired @Qualifier("newUser") User user;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("--> Application Started Successfully!!!");
		
		userServ.create(user);
	}

	
}
