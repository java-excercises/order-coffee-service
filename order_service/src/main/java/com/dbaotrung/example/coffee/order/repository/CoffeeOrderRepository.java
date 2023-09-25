package com.dbaotrung.example.coffee.order.repository;

import com.dbaotrung.example.coffee.order.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
