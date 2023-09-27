package com.dbaotrung.example.coffee.order.controller;

import com.dbaotrung.example.coffee.order.dto.GeneralApiResponse;
import com.dbaotrung.example.coffee.order.dto.OrderDto;
import com.dbaotrung.example.coffee.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place-order")
    public GeneralApiResponse<OrderDto> placeOrder(@RequestBody OrderDto orderDto) {
        return GeneralApiResponse.createSuccessResponse(orderService.placeOrder(orderDto));
    }
}
