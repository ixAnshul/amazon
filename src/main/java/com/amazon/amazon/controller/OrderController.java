package com.amazon.amazon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.amazon.response.Response;
import com.amazon.amazon.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<Response> getAllOrders(@PathVariable Long userId){
		return orderService.getAllOrders(userId);
	}
	
	@PostMapping("/{cartId}")
	public ResponseEntity<Response> orderProduct(@PathVariable Long cartId){
		return orderService.orderProducts(cartId);
	}
}
