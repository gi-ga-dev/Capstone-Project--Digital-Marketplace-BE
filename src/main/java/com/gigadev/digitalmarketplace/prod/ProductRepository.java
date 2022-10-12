package com.gigadev.digitalmarketplace.prod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<AbstractProduct, Long> {

	Boolean existsByTitle(String title);
}
