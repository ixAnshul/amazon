package com.amazon.amazon.service;

import org.springframework.http.ResponseEntity;

import com.amazon.amazon.model.Users;
import com.amazon.amazon.response.Response;

public interface UserService {

	public ResponseEntity<Response> getUserById(long id);
	
	public ResponseEntity<Response> addUser(Users user);
	
	public ResponseEntity<Response> updateUser(Long id, Users user);
	
	public ResponseEntity<Response> deleteUser(Long id);
	
}
