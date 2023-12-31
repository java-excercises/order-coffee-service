package com.dbaotrung.example.coffee.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {

    private Long id;
    private String name;
    private Double originalPrice;

}
