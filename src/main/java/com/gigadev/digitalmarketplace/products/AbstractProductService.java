package com.gigadev.digitalmarketplace.products;

import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	
	public ResponseEntity<AbstractProduct> saveVideogame(ProductDtoVideogame videogame) throws Exception {			
		if(abstractRepo.existsByTitle(videogame.getTitle())) {
			throw new EntityExistsException("Videogame already exist...");
		} else if(!videogame.allEmptyFields()) {	
			ProductVideogame finalVideogame = new ProductVideogame();
			BeanUtils.copyProperties(videogame, finalVideogame);
			finalVideogame.setProductType("Videogame");
			log.info("--> SAVE VIDEOGAME - Inserting new videogame: " + finalVideogame.getTitle());
			abstractRepo.save(finalVideogame);	
			return ResponseEntity.ok(finalVideogame);
		} else throw new Exception ("All fields must be filled!");
	}
	
	public ResponseEntity<AbstractProduct> saveMusic(ProductDtoMusic music) throws Exception {			
		if(abstractRepo.existsByTitle(music.getTitle())) {
			throw new EntityExistsException("Music already exist...");	
		} else if (!music.allEmptyFields()) {			
			if (!music.getDuration().matches("(((0[1-9])|[^0]\\d):)?[0-5]\\d:[0-5]\\d$")) {
				throw new Exception ("Duration Format must be mm:ss or hh:mm:ss");				
			}	
			ProductMusic finalMusic = new ProductMusic();
			BeanUtils.copyProperties(music, finalMusic);
			finalMusic.setProductType("Music");
			log.info("--> SAVE MUSIC - Inserting new music: " + finalMusic.getTitle());
			abstractRepo.save(finalMusic);	
			return ResponseEntity.ok(finalMusic);
		} else throw new Exception ("All fields must be filled!");			
	}
	
	public ResponseEntity<AbstractProduct> saveBook(ProductDtoBook book) throws Exception {			
		if(abstractRepo.existsByTitle(book.getTitle())) {
			throw new EntityExistsException("Book already exist...");			
		} else if (!book.allEmptyFields()) {		
			ProductBook finalBook = new ProductBook();
			BeanUtils.copyProperties(book, finalBook);	
			finalBook.setProductType("Book");
			log.info("--> SAVE BOOK - Inserting new book: " + finalBook.getTitle());
			abstractRepo.save(finalBook);	
			return ResponseEntity.ok(finalBook);
		} else throw new Exception ("All fields must be filled!");		
	}
	
	// ============== PATCH/PUT ==============
	
	// ============== DELETE ==============
	
	public void deleteProduct(Long productId) {
		// eliminare oggetto presente nella pagine prodotti
		if(!abstractRepo.existsById(productId)) {
			throw new EntityNotFoundException("Product not found...");
		} else {			
			abstractRepo.deleteById(productId);
			log.info("--> DELETE - successfull...");
		}
	}

}
