package com.amazon.amazon.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amazon.amazon.dto.OrderInfoDTO;
import com.amazon.amazon.model.Cart;
import com.amazon.amazon.model.Order;
import com.amazon.amazon.model.Product;
import com.amazon.amazon.model.Users;
import com.amazon.amazon.repository.CartRepository;
import com.amazon.amazon.repository.OrderRepository;
import com.amazon.amazon.repository.UserRepository;
import com.amazon.amazon.response.Response;

import jakarta.transaction.Transactional;


@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Override
    @Transactional
    public ResponseEntity<Response> orderProducts(Long cartId) {
        try {
            Optional<Cart> cartOptional = cartRepository.findById(cartId);
 
            if (cartOptional.isPresent()) {
                Cart cart = cartOptional.get();
 
                Set<Product> productsInCart = cart.getProducts();
 
                if (!productsInCart.isEmpty()) {
                    Order newOrder = new Order();
                    newOrder.setOrderNumber(generateOrderNumber());
                    newOrder.setOrderDate(new Date());
                    Set<Product> orderedProducts = new HashSet<>();
        		    
        		    // Iterate over products in the cart and add them to the order's product set
        		    for (Product product : productsInCart) {
        		        orderedProducts.add(product);
        		    }
        		    
        		    newOrder.setProducts(orderedProducts); 
         
                    newOrder.setCart(cart);  
 
                    cart.getOrders().add(newOrder);
 
                    cart.getProducts().clear();
                    cart.setTotal(0.0);
 
                    cartRepository.save(cart);
                    orderRepository.save(newOrder);
 
                    return ResponseEntity.ok(new Response( "Products ordered successfully",newOrder));
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new Response("Cart is empty. Cannot place an order.", null));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Cart not found with id: " + cartId, null));
            }
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Internal Server Error", e));
        }
    }
    private String generateOrderNumber() {
        // Use a combination of timestamp and a random component for uniqueness
        return "ord-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString();
    }

    @Override
    public ResponseEntity<Response> getAllOrders(Long userId) {
        try {
            Optional<Users> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                Users user = userOptional.get();

                // Check if the user has a cart
                if (user.getCart() != null) {
                    // Retrieve orders for the user's cart
                    Set<Order> userOrders = user.getCart().getOrders();

                    // Create a list to store order information
                    List<OrderInfoDTO> orderInfoList = new ArrayList<>();

                    // Iterate through each order
                    for (Order order : userOrders) {
                        // Extract relevant information from the order and products
                        String userName = user.getUsername();
                        Long orderId = order.getId();

                        // Create a map to store product count
                        Map<Long, Integer> productCountMap = new HashMap<>();

                        // Iterate through each product in the order
                        for (Product product : order.getProducts()) {
                            // Update product count in the map
                            productCountMap.put(product.getId(),
                                    productCountMap.getOrDefault(product.getId(), 0) + 1);
                        }

                        // Create OrderInfoDTO and add it to the list
                        OrderInfoDTO orderInfoDTO = new OrderInfoDTO(userName, orderId, productCountMap);
                        orderInfoList.add(orderInfoDTO);
                    }

                    return ResponseEntity.ok(new Response("Orders retrieved successfully", orderInfoList));
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
