package com.amazon.amazon.model;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CART")
public class Cart {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total")
    private double total;
    @OneToOne
    @JoinColumn(name = "userId")
    private Users user;

    @ManyToMany
    @JoinTable(
        name = "cart_product",
        joinColumns = @JoinColumn(name = "cart_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products;

    @OneToMany(mappedBy = "cart")
    @JsonIgnore
    private Set<Order> orders;
    // Constructors, getters, and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(LinkedHashSet<Product> products) {
        this.products = products;
    }
   

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cart(Long id, double total, Users user, Set<Product> products, Set<Order> orders) {
		super();
		this.id = id;
		this.total = total;
		this.user = user;
		this.products = products;
		this.orders = orders;
	}

	
	
}
