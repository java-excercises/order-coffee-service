package com.dbaotrung.example.coffee.store.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "coffee_store")
public class CoffeeStore {

    @Id
    private Long id;
    private String name;
}
