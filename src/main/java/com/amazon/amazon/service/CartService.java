package com.amazon.amazon.service;

import org.springframework.http.ResponseEntity;

import com.amazon.amazon.response.Response;

public interface CartService {
	public ResponseEntity<Response> addProductToCart(Long userId, Long productId);
	
	public ResponseEntity<Response> removeProducts(Long userId, Long productId);
	
	public ResponseEntity<Response> getProducts(Long userId);
	
}
