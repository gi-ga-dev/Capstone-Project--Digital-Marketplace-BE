package com.gigadev.digitalmarketplace.auth.users;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoSubscription {
	
	// Campi da modificare dopo il patch subscription
	//private Double accountBalance;
	
	// se si vuole fare un patch di dati senza scriverli nei campi di input,
	// bisogna copiare la proprieta' dto gia' inizializzata
	// clicco btn e faccio patch request (FE)
	// il controller (BE) autorizza la chiamata se le condizioni si avverano
	// il service lancia il metodo subscribe che se rispetta le condizioni si avvera
	private Boolean isSubscribed = true;
	
	//private LocalDate subStart;
	//private LocalDate subEnd;  
	//private Integer subTotalTime;
	//private Integer subRemaining;
}
