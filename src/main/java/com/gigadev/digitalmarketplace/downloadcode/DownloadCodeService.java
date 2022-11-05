package com.gigadev.digitalmarketplace.downloadcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.gigadev.digitalmarketplace.products.ProductVideogame;
import com.gigadev.digitalmarketplace.products.ProductVideogameRepo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DownloadCodeService {
	
	@Autowired DownloadCodeRepo codeRepo;
	@Autowired ProductVideogameRepo videogameRepo;
	@Autowired PasswordEncoder encoder;
	
	public DownloadCode saveCode(String code, Long prodId) {
		// Referenziare prodotto tramite id e cryptare codice di quel prodotto cliccato
		ProductVideogame videogame = videogameRepo.findById(prodId).get();
		DownloadCode objCode = new DownloadCode();
		String encodedCode = encoder.encode(code);		
		//videogame.getDownloadCode().setCode(encodedCode);		
		objCode.setCode(encodedCode);
		codeRepo.save(objCode);
		log.info("--> SAVE CODE - Encrypting new code for : " + videogame.getTitle());
		log.info("Code: --> " + encodedCode);
		return objCode;
	}

}
