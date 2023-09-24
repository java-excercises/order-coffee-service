package com.dbaotrung.example.coffee.order.repository;

import com.dbaotrung.example.coffee.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
