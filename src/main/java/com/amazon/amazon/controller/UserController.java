package com.amazon.amazon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.amazon.model.Users;
import com.amazon.amazon.response.Response;
import com.amazon.amazon.service.UserService; // Add the import

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable Long id) {
            return userService.getUserById(id);
    }
    
    @PostMapping("/")
    public ResponseEntity<Response> addUser(@RequestBody Users user ){
    	return userService.addUser(user);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateUserById(@PathVariable Long id, @RequestBody Users user){
    	return userService.updateUser(id, user);
    }
    
    @DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteTask(@PathVariable Long id) {
    	return userService.deleteUser(id);
    }
}
