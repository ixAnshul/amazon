package com.amazon.amazon.model;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orderNumber")
    private String orderNumber;

    @Column(name = "orderDate")
    private Date orderDate;

    @ManyToMany
    @JoinTable(
        name = "order_product",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    public Order() {
        // Default constructor
    }

    public Order(Long id, String orderNumber, Date orderDate, Set<Product> products, Cart cart) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.products = products;
        this.cart = cart;
    }

    // Getter and setter methods for all fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
