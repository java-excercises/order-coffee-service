package com.dbaotrung.example.coffee.store.dto;

import lombok.Data;

@Data
public class MenuItemDto {

    private long productId;
    private double sellingPrice;
    private int quantity;
}
