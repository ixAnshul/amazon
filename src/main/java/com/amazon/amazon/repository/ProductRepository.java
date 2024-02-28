package com.amazon.amazon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazon.amazon.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	Optional<Product> findByName(String name);
}
