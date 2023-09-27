package com.dbaotrung.example.coffee.order.dto;

import com.dbaotrung.example.coffee.order.model.CoffeeOrder;
import com.dbaotrung.example.coffee.order.model.OrderItem;
import lombok.Data;

@Data
public class OrderItemDto {

    private ProductDto product;
    private Double sellingPrice;
    private int quantity;

    public static OrderItemDto fromEntity(OrderItem orderItem) {
        var result = new OrderItemDto();
        result.setProduct(orderItem.getProduct());
        result.setQuantity(orderItem.getQuantity());
        result.setSellingPrice(orderItem.getSellingPrice());
        return result;
    }
}
