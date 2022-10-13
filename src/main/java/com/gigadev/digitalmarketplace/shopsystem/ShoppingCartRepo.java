package com.gigadev.digitalmarketplace.shopsystem;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gigadev.digitalmarketplace.products.AbstractProduct;

@Repository
public interface ShoppingCartRepo extends JpaRepository<ShoppingCart, Long> {

	void save(Set<AbstractProduct> cartList);

}
