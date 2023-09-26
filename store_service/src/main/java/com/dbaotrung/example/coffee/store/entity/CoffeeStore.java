package com.dbaotrung.example.coffee.store.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "coffee_store")
public class CoffeeStore {

    @Id
    private Long id;
    private String name;
}
