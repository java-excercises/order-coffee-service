package com.dbaotrung.example.coffee.order.service;

import com.dbaotrung.example.coffee.order.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface OrderService {

    OrderDto placeOrder(OrderDto orderDto);

    OrderDto processNextOrder();

    OrderDto completeOrder(long orderId);

    Page<OrderDto> loadAllOrdersByStore(int pageSize, int pageIndex, List<Sort.Order> orders, long storeId);
}
