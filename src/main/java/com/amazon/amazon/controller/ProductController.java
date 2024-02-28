package com.amazon.amazon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.amazon.model.Product;
import com.amazon.amazon.response.Response;
import com.amazon.amazon.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping("/")
	public ResponseEntity<Response> getAllProducts(){
		return productService.getAllProducts();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response> getProductById(@PathVariable Long id){
		return productService.getProductById(id);
	}
	
	@PostMapping("/")
	public ResponseEntity<Response> addProduct(@RequestBody Product product){
		return productService.addProduct(product);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response> updateProduct(@PathVariable Long id,@RequestBody Product product){
		return productService.updateProduct(id, product);
	}
	
}
