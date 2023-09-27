package com.dbaotrung.example.coffee.store.entity;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(name = "original_price")
    private double originalPrice;
}
