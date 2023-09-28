package com.dbaotrung.example.coffee.order.controller;

import com.dbaotrung.example.coffee.order.dto.GeneralApiResponse;
import com.dbaotrung.example.coffee.order.dto.OrderDto;
import com.dbaotrung.example.coffee.order.dto.PageDto;
import com.dbaotrung.example.coffee.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place-order")
    public GeneralApiResponse<OrderDto> placeOrder(@RequestBody OrderDto orderDto) {
        return GeneralApiResponse.createSuccessResponse(orderService.placeOrder(orderDto));
    }

    @GetMapping("/process-next")
    public GeneralApiResponse<OrderDto> processNextOrder() {
        return GeneralApiResponse.createSuccessResponse(orderService.processNextOrder());
    }

    @PutMapping("/complete")
    public GeneralApiResponse<OrderDto> completeOrder(@RequestParam("orderId") long orderId) {
        return GeneralApiResponse.createSuccessResponse(orderService.completeOrder(orderId));
    }

    @PostMapping(value = "/")
    public PageDto<OrderDto> loadAllOrdersByStore(@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
                                           @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                           @RequestParam(value = "orders", required = false, defaultValue = "[]")
                                              List<Sort.Order> orders, @RequestParam("storeId") long storeId) {
        return PageDto.createSuccessResponse(orderService.loadAllOrdersByStore(pageSize, pageIndex, orders, storeId));
    }
}
