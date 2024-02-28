package com.amazon.amazon.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.amazon.amazon.model.Cart;
import com.amazon.amazon.model.Product;
import com.amazon.amazon.model.Users;
import com.amazon.amazon.repository.CartRepository;
import com.amazon.amazon.repository.ProductRepository;
import com.amazon.amazon.repository.UserRepository;
import com.amazon.amazon.response.Response;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public ResponseEntity<Response> addProductToCart(Long userId, Long productId) {
        try {
            // Check if the user exists
            Optional<Users> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                Users user = userOptional.get();

                Optional<Product> productOptional = productRepository.findById(productId);

                if (productOptional.isPresent()) {
                    Product product = productOptional.get();
                    Set<Product> productsInCart = user.getCart().getProducts();

                    // Check if the product is already in the cart
                    if (productsInCart.contains(product)) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new Response("Product already added to the cart", null));
                    }

                    // Add the product to the user's cart
                    productsInCart.add(product);
                    
                    // Update the total
                    double newTotal = productsInCart.stream().mapToDouble(Product::getPrice).sum();
                    user.getCart().setTotal(newTotal);

                    userRepository.save(user);

                    return ResponseEntity.ok(new Response("Product added to the cart successfully", null));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new Response("Product not found with id: " + productId, null));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("User not found with id: " + userId, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Internal Server Error", e));
        }
    }

    
    @Transactional
    public ResponseEntity<Response> removeProducts(Long userId, Long productId) {
        try {
            Optional<Users> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                Users user = userOptional.get();

                // Check if the user has a cart
                if (user.getCart() != null) {
                    // Check if the product exists in the cart
                    Set<Product> productsInCart = user.getCart().getProducts();
                    Optional<Product> productToRemoveOptional = productsInCart.stream()
                            .filter(product -> product.getId().equals(productId))
                            .findFirst();

                    if (productToRemoveOptional.isPresent()) {
                        Product productToRemove = productToRemoveOptional.get();
                        productsInCart.remove(productToRemove);
                        
                        
                     // Update the total
                        double newTotal = productsInCart.stream().mapToDouble(Product::getPrice).sum();
                        user.getCart().setTotal(newTotal);
                        
                        // Save the user to update the cart
                        userRepository.save(user);

                        return ResponseEntity.ok(new Response("Product removed from the cart successfully", null));
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new Response("Product not found in the user's cart with id: " + productId, null));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new Response("User does not have a cart", null));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("User not found with id: " + userId, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Internal Server Error", e));
        }
    }

    @Transactional
    public ResponseEntity<Response> getProducts(Long userId) {
        try {
            Optional<Users> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                Users user = userOptional.get();

                // Check if the user has a cart
                if (user.getCart() != null) {
                    // Retrieve products from the user's cart
                    Set<Product> productsInCart = user.getCart().getProducts();
                    List<Cart> Carts = cartRepository.findAll();
                    
                    return ResponseEntity.ok(new Response("Products in the cart", Carts));
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new Response("User does not have a cart", null));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("User not found with id: " + userId, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Internal Server Error", e));
        }
    }
}
