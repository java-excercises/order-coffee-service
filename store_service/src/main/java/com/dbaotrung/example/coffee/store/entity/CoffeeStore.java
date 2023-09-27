package com.dbaotrung.example.coffee.store.entity;

import jakarta.persistence.*;

import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "coffee_store")
@Data
public class CoffeeStore {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    @JdbcTypeCode(SqlTypes.JSON)
    private List<MenuItem> menuItems;
    private String storeLocation;
    private String maxNoQueues;
}
