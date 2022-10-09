package com.gigadev.digitalmarketplace.auth.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.gigadev.digitalmarketplace.auth.roles.Role;
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
	@Autowired @Qualifier("systemAdmin") UserDtoRegister admin;	
	@Autowired @Qualifier("roleAdmin") Role roleAdmin;
	@Autowired @Qualifier("roleUser") Role roleUser;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("--> Application Started Successfully!!!");
		
		// all'avvio creazione 2 ruoli ed 1 admin di sistema persistenti
		roleRepo.save(roleAdmin);
		roleRepo.save(roleUser);		
		userServ.saveUser(admin, roleAdmin);
		
	}

	
}
