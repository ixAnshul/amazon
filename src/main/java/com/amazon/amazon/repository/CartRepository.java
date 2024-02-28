package com.amazon.amazon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazon.amazon.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
//	Optional<Cart> findByProductId(String name);
}
