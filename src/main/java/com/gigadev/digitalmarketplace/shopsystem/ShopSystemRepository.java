package com.gigadev.digitalmarketplace.shopsystem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopSystemRepository extends JpaRepository<ShopSystem, Long> {

	
}
