package com.gigadev.digitalmarketplace.auth.users;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gigadev.digitalmarketplace.auth.roles.ERole;
import com.gigadev.digitalmarketplace.auth.roles.Role;
import com.gigadev.digitalmarketplace.shopsystem.ShopSystem;
import com.gigadev.digitalmarketplace.shopsystem.ShopSystemRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	
	@Autowired UserRepository userRepository;
	@Autowired ShopSystemRepository shopSystemRepo;
	@Autowired PasswordEncoder encoder;
	
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
			.role( user.getRoles().stream().findFirst().get().getRoleName().name().replace("ROLE_", "") )
			.qntPurchased(user.getQntPurchased())
			.accountBalance(user.getAccountBalance())	
			.avatar(user.getAvatar())
			.isSubscribed(user.getIsSubscribed())			
			.subStart(user.getSubStart())			
			.subEnd(user.getSubEnd())
			.subTotalTime(user.getSubTotalTime())
			.subRemaining(user.getSubRemaining())
			.shopSystem(user.getShopSystem())
			.build()   
		).collect(Collectors.toList());
	}
	
	public UserDtoGetResponse getUserInfo(Long id) {
		// Restituisce tutte le prop. di un singolo User
		User user = userRepository.findById(id).get();		
		if(user.getIsSubscribed() == true) { checkSubscription(user); }
		
		return UserDtoGetResponse
		.builder()
		.userName(user.getUserName())
		.id(user.getId())
		.firstName(user.getFirstName())
		.lastName(user.getLastName())
		.email(user.getEmail())
		.userName(user.getUserName())				
		.role( user.getRoles().stream().findFirst().get().getRoleName().name().replace("ROLE_", "") )
		.qntPurchased(user.getQntPurchased())
		.accountBalance(user.getAccountBalance())
		.avatar(user.getAvatar())
		.isSubscribed(user.getIsSubscribed())			
		.subStart(user.getSubStart())			
		.subEnd(user.getSubEnd())
		.subTotalTime(user.getSubTotalTime())
		.subRemaining(user.getSubRemaining())
		.shopSystem(user.getShopSystem())
		.build();	
	}
	
	public UserDtoGetResponse getBasicUsersInfo(String userName) {
		// Restituisce le prop. Username e Ruolo di tutti gli User
		// questo metodo viene utilizzato da JwtUtils per il metodo generateJwtToken()
		User user = userRepository.findByUserName(userName).get();				
		return UserDtoGetResponse
		.builder()
		.userName(user.getUserName())
		.role( user.getRoles().stream().findFirst().get().getRoleName().name().replace("ROLE_", ""))
		.build();		
	}
	
	// ============== doBeforeSave ==============
	
//	public void doBeforeSaveCode(UserDtoRegister savedUser) {
//		// Encoding password prima di salvare nel db utente con [dati completi] 
//		String encodedPass = encoder.encode(savedUser.getPassword());
//		savedUser.setPassword(encodedPass);
//	}	
				
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
			log.info("--> SUB UPDATE -  User: " + user.getUserName() + " has subscribed for: " + user.getSubTotalTime());
		} else throw new EntityNotFoundException("--> ERROR - Account balance is insufficient, or user is already subscribed!");
	}
	
	public void checkSubscription(User user) {		
		// currentDay e' il momento in cui si fa il check (nel get info)
		LocalDate currentDay = LocalDate.now();
		LocalDate subEnd = user.getSubEnd();		
		Integer subUsed = subEnd.compareTo(currentDay); // giorni utilizzati		
		Integer subTotalTime = user.getSubTotalTime(); // giorni totali sub
		
		user.setSubRemaining(subTotalTime-subUsed);	// giorni rimanenti	
		if(user.getSubRemaining() <= 0) { 
			user.setIsSubscribed(false); 
			// NOTA: aggiungere disable sui prodotti presi a 0.00 dato che il periodo di sub e' terminato
		}
		
		userRepository.flush();
		userRepository.save(user);
	}
	
	// ============== POST ==============
			
	public User saveUser(UserDtoRegister user) {		
		if(userRepository.existsByUserName(user.getUserName())) {
			throw new EntityExistsException("User already exist...");
		} else {
			doBeforeSaveUser(user);				
			User finalUser = new User();
			BeanUtils.copyProperties(user, finalUser);				
			// istanzio shopSystem (con all'interno liste) e lo attr. all'utente
			// setto l'id utente = id shopSystem 
			ShopSystem shopSystem = new ShopSystem();
			shopSystem.setId(finalUser.getId());
			shopSystemRepo.save(shopSystem);		
			finalUser.setShopSystem(shopSystem);
			
			Role userRole = new Role(ERole.ROLE_USER);			
			finalUser.addRole(userRole);		
			
			userRepository.save(finalUser);			
			log.info("--> SAVE USER - Inserting new user: " + finalUser.getUserName());
			return finalUser;			
		}
	}	
			
	// ============== PUT/PATCH ==============
	
	public User updateAvatarViaUrl(UserDtoAvatar dto, Long id) throws Exception {
		// patch proprieta' avatar tramite campo input
		User finalUser = userRepository.findById(id).get();
		BeanUtils.copyProperties(dto, finalUser);
		
		if(finalUser.getAvatar().length() == 0) {
			throw new Exception("Invalid Field");
		} else log.info("--> PROFILE AVATAR UPDATE - for user: " + finalUser.getUserName());		
		
		return userRepository.save(finalUser);
	}
	
	public User updateAvatarPreSet(String avatar, Long id) {
		// patch proprieta' avatar senza campo input, selezionando da dei pre-set
		// passo la stringa contenuta nell'array di avatar (FE), che e' attribuita a ciascun button,
		// al back-end per patchare la proprieta' avatar dell'utente, tramite setAvatar() 
		User finalUser = userRepository.findById(id).get();
		finalUser.setAvatar(avatar);
		return userRepository.save(finalUser);
	}
		
	public User updateProfileInfo(UserDtoProfile user, Long id) {
		// restituisco solo l'obj con i primi 3 parametri del profilo
		if(!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User does not exist...");
		} else { 			
			User finalUser = userRepository.findById(id).get();
			BeanUtils.copyProperties(user, finalUser);
			log.info("--> PROFILE INFO UPDATE - for user: " + finalUser.getUserName());			
			return userRepository.save(finalUser);
		}
	}
	
	public User updateCredentials(UserDtoCredentials user, Long id) throws Exception{	
		// restituisco solo l'obj con i 2 parametri credenziali
		if(!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User does not exist...");
		} else {	
			// Al momento del patch se il campo password e' vuoto il sistema genera un token lo stesso
			// se la password prima di essere trasformata in token (60 char) e' 0 lancia eccezione
			if(user.getPassword().length() == 0) {
				throw new Exception("Password field is blank!!!");
			} else {						
				doBeforeSaveCredentials(user);
				User finalUser = userRepository.findById(id).get();
				BeanUtils.copyProperties(user, finalUser);
				log.info("--> CREDENTIALS UPDATE - for user: " + finalUser.getUserName());	
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
			return userRepository.save(finalUser);
		}
	}
	
	public User addBalance(Long id, Double balance) {
		if(!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User does not exist...");
		} else { 
			User finalUser = userRepository.findById(id).get();
			finalUser.setAccountBalance(finalUser.getAccountBalance() +  balance);
			log.info("--> BALANCE UPDATE - User: " + finalUser.getUserName() + " Account Balance: " + finalUser.getAccountBalance() + "$");
			return userRepository.save(finalUser);
		}
	}
		
	// ============== DELETE ==============
	
	public void delete(Long id) {
		if(!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User not found...");
		} else {			
			userRepository.deleteById(id);
			log.info("--> DELETE - successfull...");
		}
	}
	

}
