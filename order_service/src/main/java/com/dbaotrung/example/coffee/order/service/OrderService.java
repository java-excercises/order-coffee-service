package com.dbaotrung.example.coffee.order.service;

import com.dbaotrung.example.coffee.order.dto.OrderDto;

public interface OrderService {

    OrderDto placeOrder(OrderDto orderDto);
}
