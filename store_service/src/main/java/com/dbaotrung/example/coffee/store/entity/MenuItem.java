package com.dbaotrung.example.coffee.store.entity;

import com.dbaotrung.example.coffee.store.dto.ProductDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class MenuItem implements Serializable {

    private ProductDto product;
    private double sellingPrice;
    private int quantity;
}
