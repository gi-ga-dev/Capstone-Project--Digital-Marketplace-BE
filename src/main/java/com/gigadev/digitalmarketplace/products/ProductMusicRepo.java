package com.gigadev.digitalmarketplace.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMusicRepo extends JpaRepository<ProductMusic, Long>{

}
