package com.amazon.amazon.service;


import org.springframework.http.ResponseEntity;

import com.amazon.amazon.response.Response;

public interface OrderService {
	public ResponseEntity<Response> orderProducts(Long cartId);
	
	public ResponseEntity<Response> getAllOrders(Long userId);
}
