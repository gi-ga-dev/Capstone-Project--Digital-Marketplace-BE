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
	
	public List<UserDtoGetResponse> getAllUsersInfo() {
		// Restituisce tutte le prop. di tutti gli User
		return userRepository.findAll()
				.stream()
				.map( user ->  UserDtoGetResponse
								.builder()
								.id(user.getId())
								.firstName(user.getFirstName())
								.lastName(user.getLastName())
								.email(user.getEmail())
								.userName(user.getUserName())				
								// restituisce il ruolo (nella lista ruoli dell'user) come stringa
								.role( user.getRoles().stream().findFirst().get().getRoleName().name() )
								.password(user.getPassword())
								.build()   
				).collect(Collectors.toList());
	}
	
	public UserDtoGetResponse getUserInfo(Long id) {
		// Restituisce tutte le prop. di un singolo User
		User user = userRepository.findById(id).get();
		return UserDtoGetResponse
		.builder()
		.userName(user.getUserName())
		.id(id)
		.firstName(user.getFirstName())
		.lastName(user.getLastName())
		.email(user.getEmail())
		.userName(user.getUserName())				
		.role( user.getRoles().stream().findFirst().get().getRoleName().name() )
		.password(user.getPassword())
		.build();	
	}
	
	public UserDtoGetResponse getBasicUsersInfo(String userName) {
		// Restituisce le prop. Username e Ruolo di tutti gli User
		// questo metodo viene utilizzato da JwtUtils per il metodo generateJwtToken()
		User user = userRepository.findByUserName(userName).get();				
		return UserDtoGetResponse
		.builder()
		.userName(userName)
		.role( user.getRoles().stream().findFirst().get().getRoleName().name()).build();		
	}
	
	//========================================================================
			
	public void doBeforeSave(UserDto savedUser) {
		// Encoding password prima di salvare nel db utente con [dati completi] 
		String encodedPass = encoder.encode(savedUser.getPassword());
		savedUser.setPassword(encodedPass);
	}	
	
	public void doBeforeSaveCredentials(UserDtoCredentials savedUser) {
		// Encoding password prima di salvare nel db utente con [solo credenziali] 
		String encodedPass = encoder.encode(savedUser.getPassword());
		savedUser.setPassword(encodedPass);
	}
	
	//========================================================================
	
	public User saveAdmin(UserDto admin) {			
		if(userRepository.existsByUserName(admin.getUserName())) {
			throw new EntityExistsException("User already exist...");
		} else {
			doBeforeSave(admin);		
			User finalUser = new User();						
			BeanUtils.copyProperties(admin, finalUser);		
			finalUser.addRole(adminRole);	
			log.info("--> Inserting new admin: " + admin.getUserName());
			return userRepository.save(finalUser);						
		} 		
	}
		
	public User saveUser(UserDto user) {		
		if(userRepository.existsByUserName(user.getUserName())) {
			throw new EntityExistsException("User already exist...");
		} else {
			doBeforeSave(user);		
			User finalUser = new User();
			BeanUtils.copyProperties(user, finalUser);		
			finalUser.addRole(userRole);	
			log.info("--> Inserting new user: " + user.getUserName());
			return userRepository.save(finalUser);			
		}
	}
	
	// ==================== METODI NON IN USO =======================
	
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
	
	// ===============================================================
		
	public User updateProfileInfo(UserDtoProfile user, Long id) {
		// restituisco solo l'obj con i primi 3 parametri del profilo
		if(!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User does not exist...");
		} else { 			
			User finalUser = userRepository.findById(id).get();
			BeanUtils.copyProperties(user, finalUser);
			log.info("--> Updating user: " + user.getFirstName());			
			return userRepository.save(finalUser);
		}
	}
	
	public User updateCredentials(UserDtoCredentials user, Long id) {	
		// restituisco solo l'obj con i 2 parametri credenziali
		if(!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User does not exist...");
		} else {	
			// Al momento del patch se il campo password e' vuoto il sistema genera un token lo stesso
			// se la password prima di essere trasformata in token (60 char) e' 0 lancia eccezione
			if(user.getPassword().length() == 0) {
				throw new EntityNotFoundException("Password field is blank!!!");
			} else {						
				doBeforeSaveCredentials(user);
				User finalUser = userRepository.findById(id).get();
				BeanUtils.copyProperties(user, finalUser);
				log.info("--> Updating user: " + user.getUserName());	
				return userRepository.save(finalUser);
			} 
		}						
	}	
	
	public void delete(Long id) {
		if(!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User not found...");
		} else userRepository.deleteById(id);
	}
	

}
