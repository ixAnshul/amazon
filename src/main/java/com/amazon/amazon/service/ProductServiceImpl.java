package com.amazon.amazon.service;

import java.util.List;

import java.util.Optional; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amazon.amazon.model.Product;
import com.amazon.amazon.repository.ProductRepository;
import com.amazon.amazon.response.Response;

@Service
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
	public ResponseEntity<Response> getAllProducts() {
		try {
			List<Product> ProductData = productRepository.findAll();
			return ResponseEntity.ok(new Response("Successfully Fetched record", ProductData));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Internal Server Error", e));
		}
   	}

	
	public ResponseEntity<Response> getProductById(long id) {
	    try {
	    	Optional<Product> productData = productRepository.findById(id);
	        if (productData.isPresent()) {
	            Product product = productData.get();
	            return ResponseEntity.ok(new Response("Successfully Fetched record", product));
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Product not found with id: " + id, null));
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Internal Server Error", null));
	    }
	}


	public ResponseEntity<Response> addProduct(Product productRequest) {
    try {
        // Check if a product with the given name already exists
        Optional<Product> existingProduct = productRepository.findByName(productRequest.getName());

        if (existingProduct.isPresent()) {
            // Product with the given name already exists
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response("Product with this name already exists", null));
        }

        // If the product does not exist, proceed to create and save a new product
        String productName = productRequest.getName();
        String productDescription = productRequest.getDescription();
        Double productPrice = productRequest.getPrice();

        Product newProduct = new Product();
        newProduct.setName(productName);
        newProduct.setDescription(productDescription);
        newProduct.setPrice(productPrice);

        // Save the new product
        Product savedProduct = productRepository.save(newProduct);

        return ResponseEntity.ok(new Response("Product added successfully", savedProduct));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Response("Internal Server Error", e.getMessage()));
    }
}

	public ResponseEntity<Response> updateProduct(Long id, Product productRequest) {
	    try {
	        // Check if the product with the given ID exists
	        Optional<Product> existingProductOptional = productRepository.findById(id);
	        if (existingProductOptional.isPresent()) {
	            Product existingProduct = existingProductOptional.get();

	            // Check if the product is associated with any cart
	            if (!existingProduct.getCarts().isEmpty()) {
	                // Product is associated with at least one cart
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                        .body(new Response("Product is associated with one or more carts and cannot be updated", null));
	            }

	            // Update properties of the existing product with the values from productRequest
	            existingProduct.setName(productRequest.getName());
	            existingProduct.setPrice(productRequest.getPrice());
	            existingProduct.setDescription(productRequest.getDescription());
	            // Update other fields as needed

	            // Save the updated product
	            Product updatedProduct = productRepository.save(existingProduct);

	            return ResponseEntity.ok(new Response("Product updated successfully", updatedProduct));
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Product not found with id: " + id, null));
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Internal Server Error", null));
	    }
	}

//	public ResponseEntity<Response> deleteProduct(Long id) {
//		
//		return null;
//	}

}
