package com.dbaotrung.example.coffee.order.service;

import com.dbaotrung.example.coffee.order.client.ProductServiceClient;
import com.dbaotrung.example.coffee.order.client.StoreServiceClient;
import com.dbaotrung.example.coffee.order.dto.CoffeeStoreDto;
import com.dbaotrung.example.coffee.order.dto.OrderDto;
import com.dbaotrung.example.coffee.order.dto.QueueCountDto;
import com.dbaotrung.example.coffee.order.model.CoffeeOrder;
import com.dbaotrung.example.coffee.order.model.OrderItem;
import com.dbaotrung.example.coffee.order.model.OrderQueue;
import com.dbaotrung.example.coffee.order.model.OrderStatus;
import com.dbaotrung.example.coffee.order.repository.CoffeeOrderRepository;
import com.dbaotrung.example.coffee.order.repository.OrderQueueRepository;
import jakarta.persistence.LockModeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;
    @Autowired
    private OrderQueueRepository orderQueueRepository;
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
        return OrderDto.fromEntity(coffeeOrderRepository.save(coffeeOrder));
    }

    @Override
    @Lock(LockModeType.PESSIMISTIC_READ)
    public OrderDto processNextOrder() {
        var queues = orderQueueRepository.findAllByHavingOrderProcessing(false);
        if (queues.isEmpty()) {
            return null;
        }
        for (var queue: queues) {
            var orderOptional = coffeeOrderRepository.findFirstByQueueIdOrderByIdAsc(queue.getId());
            if (orderOptional.isEmpty()) {
                continue;
            }
            var order = orderOptional.get();
            order.setOrderStatus(OrderStatus.PROCESSING);
            order = coffeeOrderRepository.save(order);
            queue.setHavingOrderProcessing(true);
            orderQueueRepository.save(queue);
            return OrderDto.fromEntity(order);
        }
        return null;
    }

    @Override
    public OrderDto completeOrder(long orderId) {
        var orderOptional = coffeeOrderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return null;
        }
        var order = orderOptional.get();
        var queueId = order.getQueueId();
        order.setOrderStatus(OrderStatus.COMPLETED);
        order.setQueueId(0);
        order = coffeeOrderRepository.save(order);
        var queueOptional = orderQueueRepository.findById(queueId);
        queueOptional.ifPresent( q -> {
            q.setHavingOrderProcessing(false);
            orderQueueRepository.save(q);
        });
        return OrderDto.fromEntity(order);
    }

    private QueueCountDto findSuitableQueue(CoffeeStoreDto store, OrderDto orderDto) {
        List<OrderQueue> orderQueues = orderQueueRepository.findAllByStoreId(store.getId());
        List<Map<String, Object>> queueCountDtos = coffeeOrderRepository.loadQueueCount(orderDto.getStoreId());
        log.info("Queue item count in DB {}", queueCountDtos);
        // Fill up missing queue
        int maxQueue = store.getMaxNoQueues();
        orderQueues = fillingMissingQueueInDb(orderQueues, maxQueue, store.getId());
        // Find min queue to put order
        Long minQueueId = null;
        int minQueueCount = Integer.MAX_VALUE;
        for (var queue : orderQueues) {
            if (queue.getQueueIndex() > maxQueue) {
                continue;
            }
            // Find queue in database
            QueueCountDto foundQueue = null;
            for (var queueCount : queueCountDtos) {
                var countedQueueId = Long.valueOf(queueCount.get("queueid").toString());
                if (queue.getId() == countedQueueId) {
                    var dbCount = Integer.valueOf(queueCount.get("cnt").toString());
                    foundQueue = new QueueCountDto(countedQueueId, dbCount);
                    break;
                }
            }
            if (foundQueue == null) {
                return new QueueCountDto(queue.getId(), 0);
            }
            if (foundQueue.getCount() < minQueueCount) {
                minQueueId = queue.getId();
                minQueueCount = foundQueue.getCount();
            }
        }
        return new QueueCountDto(minQueueId, minQueueCount);
    }

    private List<OrderQueue> fillingMissingQueueInDb(List<OrderQueue> orderQueues, int maxQueue, long storeId) {
        // Fill up missing queue
        for (var i = 0; i < maxQueue; i++) {
            boolean needCreate = true;
            for (var queue : orderQueues) {
                if (queue.getQueueIndex() == i + 1) {
                    needCreate = false;
                    break;
                }
            }
            if (needCreate) {
                var newQueue = new OrderQueue();
                newQueue.setQueueIndex(i + 1);
                newQueue.setStoreId(storeId);
                orderQueues.add(orderQueueRepository.save(newQueue));
            }
        }
        // Return
        return orderQueues;
    }
}
