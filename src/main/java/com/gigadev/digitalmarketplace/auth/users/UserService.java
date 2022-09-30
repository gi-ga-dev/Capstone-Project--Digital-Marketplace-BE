package com.gigadev.digitalmarketplace.auth.users;

import java.beans.Beans;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gigadev.digitalmarketplace.auth.roles.Role;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	
	@Autowired UserRepository userRepository;
	@Autowired PasswordEncoder encoder;
	@Autowired @Qualifier("admin") Role adminRole;
	@Autowired @Qualifier("user") Role userRole;
	
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
			
	public void doBeforeSave(UserDto savedUser) {
		// Encoding della password prima di salvare utente nel db
		String encodedPass = encoder.encode(savedUser.getPassword());
		savedUser.setPassword(encodedPass);
	}	
	
	public User saveAdmin(UserDto admin) {			
		if(userRepository.existsByUsername(admin.getUsername())) {
			throw new EntityExistsException("User already exist...");
		} else {
			doBeforeSave(admin);		
			User finalUser = new User();						
			BeanUtils.copyProperties(admin, finalUser);		
			finalUser.addRole(adminRole);	
			log.info("--> Inserting new admin: " + admin.getUsername());
			return userRepository.save(finalUser);						
		} 		
	}
		
	public User saveUser(UserDto user) {		
		if(userRepository.existsByUsername(user.getUsername())) {
			throw new EntityExistsException("User already exist...");
		} else {
			doBeforeSave(user);		
			User finalUser = new User();
			BeanUtils.copyProperties(user, finalUser);		
			finalUser.addRole(userRole);	
			log.info("--> Inserting new user: " + user.getUsername());
			return userRepository.save(finalUser);			
		}
	}
	
	public List<User> searchAllUsers() {
		if(userRepository.findAll() == null) {
			throw new EntityNotFoundException("No results found...");
		} else return (List<User>) userRepository.findAll();
	}
	
	public User read(Long id) {
		if(!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User does not exist...");
		} else return userRepository.findById(id).get();
	}
	
	// --- NOTA: --- questo metodo necessita di modificare tutti i campi
	// si potrebbero creare dei metodi che modificano singoli campi
	public User update(UserDto user, Long id) {	
		
		if(!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User does not exist...");
		} else { 
			doBeforeSave(user);
			User finalUser = userRepository.findById(id).get();
			BeanUtils.copyProperties(user, finalUser);
			log.info("--> Updating user: " + user.getUsername());			
			return userRepository.save(finalUser);
		}
	}
	
	public void delete(Long id) {
		if(!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User not found...");
		} else userRepository.deleteById(id);
	}
	

}
