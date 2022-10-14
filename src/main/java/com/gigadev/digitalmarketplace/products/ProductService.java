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
public class ProductService {
	
	@Autowired AbstractProductRepo productRepo;
	@Autowired ProductVideogameRepo videogameRepo;
	@Autowired ProductMusicRepo musicRepo;
	@Autowired ProductBookRepo bookRepo;	
	
	// ============== GET ==============
	
	public List<AbstractProduct> getAllProducts() {
		return productRepo.findAll();
	}
	
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
	
	public ProductVideogame getVideogameById(Long id) {
		if(!videogameRepo.existsById(id)) {
			throw new EntityNotFoundException("Videogame not found...");
		} else return videogameRepo.findById(id).get();
	}
	
	public ProductMusic getMusicById(Long id) {
		if(!musicRepo.existsById(id)) {
			throw new EntityNotFoundException("Music not found...");
		} else return musicRepo.findById(id).get();
	}
	
	public ProductBook getBookById(Long id) {
		if(!bookRepo.existsById(id)) {
			throw new EntityNotFoundException("Book not found...");
		} else return bookRepo.findById(id).get();
	}
		
	// ============== POST ==============
	// post dati compilati nei campi di input 
	
	public AbstractProduct saveVideogame(ProductDtoVideogame videogame) {			
		if(productRepo.existsByTitle(videogame.getTitle())) {
			throw new EntityExistsException("Videogame already exist...");
		} else {	
			ProductVideogame finalVideogame = new ProductVideogame();
			BeanUtils.copyProperties(videogame, finalVideogame);
			finalVideogame.setProductType("Videogame");
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
			finalMusic.setProductType("Music");
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
			finalBook.setProductType("Book");
			log.info("--> SAVE BOOK - Inserting new book: " + finalBook.getTitle());
			return productRepo.save(finalBook);			
		}
	}
	
	// ============== PATCH/PUT ==============
	
	// ============== DELETE ==============

}
