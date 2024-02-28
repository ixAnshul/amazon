package com.amazon.amazon.service;

import org.springframework.http.ResponseEntity;

import com.amazon.amazon.model.Product;
import com.amazon.amazon.response.Response;

public interface ProductService {
	
	public ResponseEntity<Response> getAllProducts();
	
	public ResponseEntity<Response> getProductById(long id);
	
	public ResponseEntity<Response> addProduct(Product productRequest);
	
	public ResponseEntity<Response> updateProduct(Long id, Product productRequest);
	
//	public ResponseEntity<Response> deleteProduct(Long id);
}
