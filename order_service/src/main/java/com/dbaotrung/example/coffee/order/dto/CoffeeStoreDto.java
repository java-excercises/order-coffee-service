package com.dbaotrung.example.coffee.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class CoffeeStoreDto {

    private Long id;
    private String name;
    private List<MenuItem> menuItems;
    private String storeLocation;
    private int maxNoQueues;
}
