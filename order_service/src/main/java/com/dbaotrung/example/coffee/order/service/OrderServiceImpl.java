package com.dbaotrung.example.coffee.order.service;

import com.dbaotrung.example.coffee.order.client.ProductServiceClient;
import com.dbaotrung.example.coffee.order.client.StoreServiceClient;
import com.dbaotrung.example.coffee.order.dto.CoffeeStoreDto;
import com.dbaotrung.example.coffee.order.dto.OrderDto;
import com.dbaotrung.example.coffee.order.dto.QueueCountDto;
import com.dbaotrung.example.coffee.order.model.CoffeeOrder;
import com.dbaotrung.example.coffee.order.model.OrderItem;
import com.dbaotrung.example.coffee.order.model.OrderStatus;
import com.dbaotrung.example.coffee.order.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Transactional
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;
    @Autowired
    private StoreServiceClient storeServiceClient;
    @Autowired
    private ProductServiceClient productServiceClient;

    @Override
    public OrderDto placeOrder(OrderDto orderDto) {
        var coffeeOrder = new CoffeeOrder();
        coffeeOrder.setOrderStatus(OrderStatus.ORDERED);
        coffeeOrder.setVat(orderDto.getVat());
        coffeeOrder.setTotal(orderDto.getTotal());
        coffeeOrder.setSubTotal(orderDto.getSubTotal());
        coffeeOrder.setStoreId(orderDto.getStoreId());
        var store = storeServiceClient.viewDetails(orderDto.getStoreId()).getResult();
        log.debug("Store [{}]", store);
        // Order items
        var submittedItems = orderDto.getItems();
        var orderItems = new ArrayList<OrderItem>(submittedItems.size());
        for (var dto : submittedItems) {
            var item = new OrderItem();
            orderItems.add(item);
            var product = productServiceClient.viewDetails(dto.getProduct().getId()).getResult();
            item.setProduct(product);
            item.setQuantity(dto.getQuantity());
            item.setSellingPrice(dto.getSellingPrice());
        }
        coffeeOrder.setItems(orderItems);
        // Put to queue
        var queueCount = findSuitableQueue(store, orderDto);
        coffeeOrder.setQueueId(queueCount.getQueueId());
        coffeeOrder.setQueueIndex(queueCount.getCount() + 1);
        return OrderDto.fromEntity(coffeeOrderRepository.save(coffeeOrder));
    }

    private QueueCountDto findSuitableQueue(CoffeeStoreDto store, OrderDto orderDto) {
        List<Map<String, Object>> queueCountDtos = coffeeOrderRepository.loadQueueCount(orderDto.getStoreId());
        log.info("Queue count {}", queueCountDtos);
        int maxQueue = store.getMaxNoQueues();
        String minQueueId = "";
        int minQueueCount = Integer.MAX_VALUE;
        for (var i = 0; i < maxQueue; i++) {
            var queueId = "store-" + store.getId() + "-queue-" + (i + 1);
            // Find queue in database
            QueueCountDto foundQueue = null;
            for (var queueCount : queueCountDtos) {
                var dbQueueId = (String) queueCount.get("queueid");
                if (dbQueueId.equalsIgnoreCase(queueId)) {
                    var dbCount = Integer.valueOf(queueCount.get("cnt").toString());
                    foundQueue = new QueueCountDto(dbQueueId, dbCount);
                    break;
                }
            }
            if (foundQueue == null) {
                return new QueueCountDto(queueId, 0);
            }
            if (foundQueue.getCount() < minQueueCount) {
                minQueueId = queueId;
                minQueueCount = foundQueue.getCount();
            }
        }
        return new QueueCountDto(minQueueId, minQueueCount);
    }
}
