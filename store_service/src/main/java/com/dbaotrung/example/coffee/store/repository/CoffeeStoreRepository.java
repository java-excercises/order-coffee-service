package com.dbaotrung.example.coffee.store.repository;

import com.dbaotrung.example.coffee.store.entity.CoffeeStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeStoreRepository extends JpaRepository<CoffeeStore, Long> {
}
