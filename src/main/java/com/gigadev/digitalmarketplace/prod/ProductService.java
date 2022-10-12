package com.gigadev.digitalmarketplace.prod;

import javax.persistence.EntityExistsException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {
	
	@Autowired ProductRepository productRepo;
	
	// ============== GET ==============
	
	// ============== POST ==============
	
	public AbstractProduct saveVideogame(ProductDtoVideogame videogame) {			
		if(productRepo.existsByTitle(videogame.getTitle())) {
			throw new EntityExistsException("Videogame already exist...");
		} else {	
			ProductVideogame finalVideogame = new ProductVideogame();
			BeanUtils.copyProperties(videogame, finalVideogame);						
			log.info("--> SAVE VIDEOGAME - Inserting new videogame: " + finalVideogame.getTitle());
			return productRepo.save(finalVideogame);			
		}
	}	
	
	// ============== PATCH/PUT ==============
	
	// ============== DELETE ==============

}
