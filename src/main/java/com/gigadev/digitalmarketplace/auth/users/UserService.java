package com.gigadev.digitalmarketplace.auth.users;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	
	@Autowired UserRepository userRepository;
	@Autowired PasswordEncoder encoder;
	
	public List<UserResponse> getAllUsersBasicInformations() {
		return userRepository.findAll()
				.stream()
				.map( user ->  UserResponse
								.builder()
								.userName(  user.getUsername()  )
								.role( user.getRoles().stream().findFirst().get().getRoleName().name() )
								.build()   
				).collect(Collectors.toList());
	}
	
	public UserResponse getUserBasicInformations(String userName) {
		User user = userRepository.findByUsername(userName).get();
				
		return UserResponse
		.builder()
		.userName(userName)
		.role( user.getRoles().stream().findFirst().get().getRoleName().name()).build();
		
	}
	
	public List<User> searchAllUsers() {
		if(userRepository.findAll() == null) {
			throw new EntityNotFoundException("No results found...");
		} else return (List<User>) userRepository.findAll();
	}
	
	public void doBeforeSave(User savedUser) {
		// Encoding della password prima di salvare utente nel db
		String encodedPass = encoder.encode(savedUser.getPassword());
		savedUser.setPassword(encodedPass);
	}
		
	public User create(User user) {
		log.info("Inserting object: " + user);
		doBeforeSave(user);
		return userRepository.save(user);
	}
	

}
