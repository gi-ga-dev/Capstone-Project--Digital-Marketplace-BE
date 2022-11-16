package com.gigadev.digitalmarketplace.auth.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.gigadev.digitalmarketplace.auth.roles.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class UserRunner implements ApplicationRunner {
	
	@Autowired UserService userServ;
	@Autowired RoleRepository roleRepo;
	//@Autowired @Qualifier("systemAdmin") UserDtoRegister admin;	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("--> UserRunner started...");
			
		//userServ.createAdmin(admin);
		
	}

	
}
