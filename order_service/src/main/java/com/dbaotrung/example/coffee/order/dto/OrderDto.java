package com.dbaotrung.example.coffee.order.dto;

import com.dbaotrung.example.coffee.order.model.CoffeeOrder;
import com.dbaotrung.example.coffee.order.model.OrderStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {

    private Long id;
    private List<OrderItemDto> items;
    private OrderStatus orderStatus;
    private double subTotal;
    private double vat;
    private double total;
    private long storeId;
    private long queueId;

    public static OrderDto fromEntity(CoffeeOrder coffeeOrder) {
        var result = new OrderDto();
        result.setId(coffeeOrder.getId());
        result.setOrderStatus(coffeeOrder.getOrderStatus());
        result.setVat(coffeeOrder.getVat());
        result.setTotal(coffeeOrder.getTotal());
        result.setSubTotal(coffeeOrder.getSubTotal());
        result.setQueueId(coffeeOrder.getQueueId());
        result.setStoreId(coffeeOrder.getStoreId());
        var orderItems = coffeeOrder.getItems();
        var items = new ArrayList<OrderItemDto>(orderItems.size());
        orderItems.forEach( it -> items.add(OrderItemDto.fromEntity(it)));
        result.setItems(items);
        return result;
    }
}
