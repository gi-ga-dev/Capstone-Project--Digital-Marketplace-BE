package com.gigadev.digitalmarketplace.products;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AbstractProductService {
	
	@Autowired AbstractProductRepo abstractRepo;
	@Autowired ProductVideogameRepo videogameRepo;
	@Autowired ProductMusicRepo musicRepo;
	@Autowired ProductBookRepo bookRepo;	
	
	// ============== GET ==============
	
	public List<AbstractProduct> getAllProducts() {
		return abstractRepo.findAll();
	}
	
	// ---- Necessari per ritornare array specifici in Front-End (Pagine Products) ----
	
	public List<ProductVideogame> getAllVideogames() {
		return videogameRepo.findAll();				
	}
	
	public List<ProductMusic> getAllMusic() {
		return musicRepo.findAll();				
	}
	
	public List<ProductBook> getAllBooks() {
		return bookRepo.findAll();				
	}
	
	// ----------------
	
	public AbstractProduct getProductById(Long id) {
		if(!abstractRepo.existsById(id)) {
			throw new EntityNotFoundException("Product not found...");
		} else return abstractRepo.findById(id).get();
	}
			
	// ============== POST ==============
	// ---> Classi Products e Metodi relativi, si riferiscono sempre ai prodotti nelle schede (Pagine Products)
	// ---> Post dati compilati nei campi di input (Pagina Profilo Admin Actions), no polimorfismo perche' AbstractProd non si puo' istanziare
	
	public AbstractProduct saveVideogame(ProductDtoVideogame videogame) {			
		if(abstractRepo.existsByTitle(videogame.getTitle())) {
			throw new EntityExistsException("Videogame already exist...");
		} else {	
			ProductVideogame finalVideogame = new ProductVideogame();
			BeanUtils.copyProperties(videogame, finalVideogame);
			finalVideogame.setProductType("Videogame");
			log.info("--> SAVE VIDEOGAME - Inserting new videogame: " + finalVideogame.getTitle());
			return abstractRepo.save(finalVideogame);			
		}
	}
	
	public AbstractProduct saveMusic(ProductDtoMusic music) {			
		if(abstractRepo.existsByTitle(music.getTitle())) {
			throw new EntityExistsException("Music already exist...");
		} else {	
			ProductMusic finalMusic = new ProductMusic();
			BeanUtils.copyProperties(music, finalMusic);
			finalMusic.setProductType("Music");
			log.info("--> SAVE MUSIC - Inserting new music: " + finalMusic.getTitle());
			return abstractRepo.save(finalMusic);			
		}
	}
	
	public AbstractProduct saveBook(ProductDtoBook book) {			
		if(abstractRepo.existsByTitle(book.getTitle())) {
			throw new EntityExistsException("Book already exist...");
		} else {	
			ProductBook finalBook = new ProductBook();
			BeanUtils.copyProperties(book, finalBook);	
			finalBook.setProductType("Book");
			log.info("--> SAVE BOOK - Inserting new book: " + finalBook.getTitle());
			return abstractRepo.save(finalBook);			
		}
	}
	
	// ============== PATCH/PUT ==============
	
	// ============== DELETE ==============

}
