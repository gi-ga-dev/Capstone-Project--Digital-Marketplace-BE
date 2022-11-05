package com.gigadev.digitalmarketplace.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVideogameRepo extends JpaRepository<ProductVideogame, Long> {
	
}
