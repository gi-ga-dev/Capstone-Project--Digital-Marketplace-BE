package com.gigadev.digitalmarketplace.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class AbstractProductRunner implements ApplicationRunner {

	@Autowired @Qualifier("shadowTombRaider") ProductDtoVideogame shadowTombRaider;
	@Autowired @Qualifier("grandTheftAutoV") ProductDtoVideogame grandTheftAutoV;
	@Autowired @Qualifier("control") ProductDtoVideogame control;
	@Autowired @Qualifier("underPressure") ProductDtoMusic underPressure;
	@Autowired @Qualifier("natural") ProductDtoMusic natural;
	@Autowired @Qualifier("withYou") ProductDtoMusic withYou;
	@Autowired @Qualifier("thinkLikeaProgrammer") ProductDtoBook thinkLikeaProgrammer;
	@Autowired @Qualifier("harryPotter1") ProductDtoBook harryPotter1;
	@Autowired @Qualifier("mortalInstrument1") ProductDtoBook mortalInstrument1;

	
	@Autowired AbstractProductService absProdServ;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("--> AbstractProductRunner - Card Products created...");
		
		absProdServ.saveVideogame(shadowTombRaider);
		absProdServ.saveVideogame(grandTheftAutoV);
		absProdServ.saveVideogame(control);
		
		absProdServ.saveMusic(underPressure);
		absProdServ.saveMusic(natural);
		absProdServ.saveMusic(withYou);
		
		absProdServ.saveBook(thinkLikeaProgrammer);
		absProdServ.saveBook(harryPotter1);
		absProdServ.saveBook(mortalInstrument1);
		
	}

}
