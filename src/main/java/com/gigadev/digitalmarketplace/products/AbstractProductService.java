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
	
	public AbstractProduct saveVideogame(ProductDtoVideogame videogame) throws Exception {			
		if(abstractRepo.existsByTitle(videogame.getTitle())) {
			throw new EntityExistsException("Videogame already exist...");
		} else if (
			videogame.getImgLink().length() == 0 ||
			videogame.getPrice() == 0 || 
			videogame.getTitle().length() == 0 ||
			videogame.getDescription().length() == 0 ||
			videogame.getPlatform().length() == 0 ||
			videogame.getPublisher().length() == 0 || 
			videogame.getReleaseDate() == null|| 
			videogame.getLanguage().length() == 0 || 
			videogame.getGenre().length() == 0 || 
			videogame.getVgSeries().length() == 0 || 
			videogame.getDeveloper().length() == 0 || 
			videogame.getAgeRecommendation() == 0 || 
			videogame.getPlayers() == 0 ||
			videogame.getCoopPlay().length() == 0 || 
			videogame.getControllerSupport().length() == 0 || 
			videogame.getSubtitles().length() == 0 || 
			videogame.getRequiredSpace() == 0) {		
		
			throw new Exception ("Fields cannot be blank");
		}		
		
		else {	
			ProductVideogame finalVideogame = new ProductVideogame();
			BeanUtils.copyProperties(videogame, finalVideogame);
			finalVideogame.setProductType("Videogame");
			log.info("--> SAVE VIDEOGAME - Inserting new videogame: " + finalVideogame.getTitle());
			return abstractRepo.save(finalVideogame);			
		}
	}
	
	public AbstractProduct saveMusic(ProductDtoMusic music) throws Exception {			
		if(abstractRepo.existsByTitle(music.getTitle())) {
			throw new EntityExistsException("Music already exist...");
		} else if (
			music.getImgLink().length() == 0 ||
			music.getPrice() == 0 || 
			music.getTitle().length() == 0 ||
			music.getDescription().length() == 0 ||
			music.getPlatform().length() == 0 ||
			music.getPublisher().length() == 0 || 
			music.getReleaseDate() == null || 
			music.getLanguage().length() == 0 || 
			music.getGenre().length() == 0 || 				
			music.getArtist().length() == 0 || 
			music.getAlbum().length() == 0 || 
			music.getDuration().length() == 0) {		
		
			throw new Exception ("Fields cannot be blank");
			
		} else if (!music.getDuration().matches("(((0[1-9])|[^0]\\d):)?[0-5]\\d:[0-5]\\d$")) {
			throw new Exception ("Duration Format must be mm:ss or hh:mm:ss");
		} else {	
			ProductMusic finalMusic = new ProductMusic();
			BeanUtils.copyProperties(music, finalMusic);
			finalMusic.setProductType("Music");
			log.info("--> SAVE MUSIC - Inserting new music: " + finalMusic.getTitle());
			return abstractRepo.save(finalMusic);			
		}
	}
	
	public AbstractProduct saveBook(ProductDtoBook book) throws Exception {			
		if(abstractRepo.existsByTitle(book.getTitle())) {
			throw new EntityExistsException("Book already exist...");
		} else if (
			book.getImgLink().length() == 0 ||
			book.getPrice() == 0 || 
			book.getTitle().length() == 0 ||
			book.getDescription().length() == 0 ||
			book.getPlatform().length() == 0 ||
			book.getPublisher().length() == 0 || 
			book.getReleaseDate() == null || 
			book.getLanguage().length() == 0 || 
			book.getGenre().length() == 0 || 
			book.getPages() == 0 || 
			book.getIsbnCode().length() == 0 || 
			book.getBookSeries().length() == 0 || 
			book.getAuthor().length() == 0) {		
		
			throw new Exception ("Fields cannot be blank");
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
