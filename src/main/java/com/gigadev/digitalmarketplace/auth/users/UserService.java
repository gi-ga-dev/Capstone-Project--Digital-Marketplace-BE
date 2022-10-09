package com.gigadev.digitalmarketplace.auth.users;

import java.beans.Beans;
import java.time.LocalDate;
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
	
	// ============== GET ==============
	
	public List<UserDtoGetResponse> getAllUsersInfo() {
		// Restituisce tutte le prop. di tutti gli User
		return userRepository.findAll().stream()
		.map( user ->  UserDtoGetResponse
			.builder()
			.id(user.getId())
			.firstName(user.getFirstName())
			.lastName(user.getLastName())
			.email(user.getEmail())
			.userName(user.getUserName())				
			// restituisce il ruolo (nella lista ruoli dell'user) come stringa
			.role( user.getRoles().stream().findFirst().get().getRoleName().name() )								
			.accountBalance(user.getAccountBalance())			
			.isSubscribed(user.getIsSubscribed())			
			.subStart(user.getSubStart())			
			.subEnd(user.getSubEnd())
			.subTotalTime(user.getSubTotalTime())
			.subRemaining(user.getSubRemaining())
			.purchaseHistory(user.getPurchaseHistory())
			.videogamesList(user.getVideogamesList())
			.musicList(user.getMusicList())
			.bookList(user.getBookList())
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
		.accountBalance(user.getAccountBalance())			
		.isSubscribed(user.getIsSubscribed())			
		.subStart(user.getSubStart())			
		.subEnd(user.getSubEnd())
		.subTotalTime(user.getSubTotalTime())
		.subRemaining(user.getSubRemaining())
		.purchaseHistory(user.getPurchaseHistory())
		.videogamesList(user.getVideogamesList())
		.musicList(user.getMusicList())
		.bookList(user.getBookList())
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
	
	// ============== doBeforeSave ==============
			
	public void doBeforeSaveUser(UserDtoRegister savedUser) {
		// Encoding password prima di salvare nel db utente con [dati completi] 
		String encodedPass = encoder.encode(savedUser.getPassword());
		savedUser.setPassword(encodedPass);
	}	
	
	public void doBeforeSaveCredentials(UserDtoCredentials savedUser) {
		// Encoding password prima di salvare nel db utente con [solo credenziali] 
		String encodedPass = encoder.encode(savedUser.getPassword());
		savedUser.setPassword(encodedPass);
	}
	
	public void doBeforeSubscribe(User user, Double subCost, Integer subDays) {
		// solo se l'utente ha denaro sufficiente e non e' gia' iscritto
		if(user.getAccountBalance() >= subCost && user.getIsSubscribed() == false) {
			user.setAccountBalance(user.getAccountBalance() - subCost);
			user.setIsSubscribed(true);			
			// start, end e tempo totale sono prestabiliti ad inizio sub
			user.setSubStart(LocalDate.now());
			user.setSubEnd(LocalDate.now().plusDays(subDays));
			user.setSubTotalTime(subDays); // 30-180-365 days			
			log.info("--> User: " + user.getUserName() + " has subscribed!");
		} else throw new EntityNotFoundException("--> Account balance is insufficient, or user is already subscribed!");
	}
	
	// ============== POST ==============
	
	public User saveAdmin(UserDtoRegister admin) {			
		if(userRepository.existsByUserName(admin.getUserName())) {
			throw new EntityExistsException("User already exist...");
		} else {
			doBeforeSaveUser(admin);		
			User finalUser = new User();						
			BeanUtils.copyProperties(admin, finalUser);		
			finalUser.addRole(adminRole);	
			log.info("--> Inserting new admin: " + finalUser.getUserName());
			return userRepository.save(finalUser);						
		} 		
	}
		
	public User saveUser(UserDtoRegister user) {		
		if(userRepository.existsByUserName(user.getUserName())) {
			throw new EntityExistsException("User already exist...");
		} else {
			doBeforeSaveUser(user);		
			User finalUser = new User();
			BeanUtils.copyProperties(user, finalUser);		
			finalUser.addRole(userRole);	
			log.info("--> Inserting new user: " + finalUser.getUserName());
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
	
	// ============== PUT/PATCH ==============
		
	public User updateProfileInfo(UserDtoProfile user, Long id) {
		// restituisco solo l'obj con i primi 3 parametri del profilo
		if(!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User does not exist...");
		} else { 			
			User finalUser = userRepository.findById(id).get();
			BeanUtils.copyProperties(user, finalUser);
			log.info("--> Updating profile info for user: " + finalUser.getUserName());			
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
				log.info("--> Updating credentials for user: " + finalUser.getUserName());	
				return userRepository.save(finalUser);
			} 
		}						
	}	
	
	public User beginSubscription(Long id, Double subCost, Integer subDays) {
		// restituisco solo l'obj con i parametri sub patchati
		if(!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User does not exist...");
		} else { 	
			User finalUser = userRepository.findById(id).get();	
			// se si avverano le condizioni del metodo patcha i dati di finalUser e viene restituito
			doBeforeSubscribe(finalUser, subCost, subDays);
			log.info("--> Updating subscription info for user: " + finalUser.getUserName());			
			return userRepository.save(finalUser);
		}
	}
		
	// ============== DELETE ==============
	
	public void delete(Long id) {
		if(!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User not found...");
		} else {			
			userRepository.deleteById(id);
			log.info("--> Delete successfull...");
		}
	}
	

}
