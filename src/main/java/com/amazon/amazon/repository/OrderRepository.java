package com.amazon.amazon.repository;

import com.amazon.amazon.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface OrderRepository extends JpaRepository<Order, Long> {
	@Query("SELECT MAX(CAST(SUBSTRING(o.orderNumber, 5) AS long)) FROM Order o")
	 Long findLatestOrderNumber();
}


