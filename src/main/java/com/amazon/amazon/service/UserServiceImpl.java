package com.amazon.amazon.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amazon.amazon.model.Cart;
import com.amazon.amazon.model.Users;
import com.amazon.amazon.repository.CartRepository;
import com.amazon.amazon.repository.UserRepository;
import com.amazon.amazon.response.Response;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
    private CartRepository cartRepository;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<Response> getUserById(long id) {
        try {
            Optional<Users> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                Users user = userOptional.get();
                return ResponseEntity.ok(new Response("Successfully Fetched record", user));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("User not found with id: " + id, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Internal Server Error", null));
        }
    }
    
    public ResponseEntity<Response> addUser(Users user){
    	try {
    		Optional<Users> usersEmail = userRepository.findByEmail(user.getEmail());
    		Optional<Users> usersName = userRepository.findByUserName(user.getUsername());
    		
    		if (usersName.isPresent()) {
    			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("UserName already exists", null));
    		}
    		if (usersEmail.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("User with this email already exists", null));
            }
    		
    		String userName = user.getUsername();
        	String email = user.getEmail();
        	String password = user.getPassword();
        	
        	Users newUser = new Users();
        	
        	newUser.setUsername(userName);
        	newUser.setEmail(email);
        	newUser.setPassword(password);
        	Users saveUser = userRepository.save(newUser);
        	// Check if the user has a cart
            if (user.getCart() == null) {
                
                Cart newCart = new Cart();
                newCart.setUser(saveUser);
                cartRepository.save(newCart);

                // Update the user's cart
                user.setCart(newCart);
            }
        	
        	return ResponseEntity.ok(new Response("Task added successfully", saveUser));
    	}catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Internal Server Error", e));
    	}
		
    }

    public ResponseEntity<Response> updateUser(Long id, Users user){
    	try {
    		Optional<Users> userOptional = userRepository.findById(id);
    		if (userOptional.isPresent()) {
    			String userName = user.getUsername();
            	String password = user.getPassword();
    			Optional<Users> usersName = userRepository.findByUserName(userName);
    			if (usersName.isPresent()) {
        			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Username already exists", null));
        		}
    			Users existingUser = userOptional.get();
    			if(user.getUsername()!= null) {
    				existingUser.setUsername(userName);
    			}
    			if(user.getPassword()!= null) {
    				existingUser.setPassword(password);
    			}
    			Users updatedUser = userRepository.save(existingUser);
                return ResponseEntity.ok(new Response("Successfully Fetched record", updatedUser));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("User not found with id: " + id, null));
            }
    	}catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Internal Server Error", e));
    	}
    }
    
    public ResponseEntity<Response> deleteUser(Long id){
    	try {
    		Optional<Users> userData = userRepository.findById(id);
            if (userData.isPresent()) {
                userRepository.deleteById(id);
                return ResponseEntity.ok(new Response("User Deleted Successfully", userData));
            } else {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("User not found with id: " + id, null));
            }
    		
    	}catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Internal Server Error", e));
    	}
    }
    
}
