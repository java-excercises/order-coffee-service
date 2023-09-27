package com.dbaotrung.example.coffee.order.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MenuItem implements Serializable {

    private ProductDto product;
    private double sellingPrice;
    private int quantity;
}
