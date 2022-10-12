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
	
	public AbstractProduct saveMusic(ProductDtoMusic music) {			
		if(productRepo.existsByTitle(music.getTitle())) {
			throw new EntityExistsException("Music already exist...");
		} else {	
			ProductMusic finalMusic = new ProductMusic();
			BeanUtils.copyProperties(music, finalMusic);						
			log.info("--> SAVE MUSIC - Inserting new music: " + finalMusic.getTitle());
			return productRepo.save(finalMusic);			
		}
	}
	
	public AbstractProduct saveBook(ProductDtoBook book) {			
		if(productRepo.existsByTitle(book.getTitle())) {
			throw new EntityExistsException("Book already exist...");
		} else {	
			ProductBook finalBook = new ProductBook();
			BeanUtils.copyProperties(book, finalBook);						
			log.info("--> SAVE BOOK - Inserting new book: " + finalBook.getTitle());
			return productRepo.save(finalBook);			
		}
	}
	
	// ============== PATCH/PUT ==============
	
	// ============== DELETE ==============

}
