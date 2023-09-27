package com.dbaotrung.example.coffee.store.repository;

import com.dbaotrung.example.coffee.store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
