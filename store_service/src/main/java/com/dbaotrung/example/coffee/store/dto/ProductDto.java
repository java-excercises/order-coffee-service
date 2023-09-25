package com.dbaotrung.example.coffee.store.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDto implements Serializable {

    private Long id;
    private String name;
    private Double originalPrice;

}
