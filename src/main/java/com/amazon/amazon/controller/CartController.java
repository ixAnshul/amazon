package com.amazon.amazon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.amazon.response.Response;
import com.amazon.amazon.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<Response> getCartProducts(@PathVariable Long userId){
		System.out.println("hy");
		return cartService.getProducts(userId);
	}
	
	@PostMapping("/{userId}/{productId}")
	public ResponseEntity<Response> addProductToCart(@PathVariable Long userId, @PathVariable Long productId) {
	    return cartService.addProductToCart(userId, productId);
	}
	@DeleteMapping("/{userId}/{productId}")
	public ResponseEntity<Response> removeProduct(@PathVariable Long userId, @PathVariable Long productId){
		return cartService.removeProducts(userId, productId);
	}

}
