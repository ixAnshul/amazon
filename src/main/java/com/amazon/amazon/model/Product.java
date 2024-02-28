package com.amazon.amazon.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Cart> carts;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Order> order;

    public Product() {
        this.carts = new HashSet<>();
        this.order = new HashSet<>();
    }

    public Product(Long id, String name, String description, double price, Set<Cart> carts, Set<Order> order) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.carts = carts;
        this.order = order;
    }

    // Getter and setter methods for all fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }
}
