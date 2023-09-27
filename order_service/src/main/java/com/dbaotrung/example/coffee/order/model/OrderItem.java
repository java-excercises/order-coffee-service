package com.dbaotrung.example.coffee.order.model;

import com.dbaotrung.example.coffee.order.dto.ProductDto;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class OrderItem implements Serializable {

    private ProductDto product;
    private Double sellingPrice;
    private int quantity;

}
