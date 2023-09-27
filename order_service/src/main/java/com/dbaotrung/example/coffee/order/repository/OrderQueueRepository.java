package com.dbaotrung.example.coffee.order.repository;

import com.dbaotrung.example.coffee.order.model.OrderQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderQueueRepository extends JpaRepository<OrderQueue, Long> {

    List<OrderQueue> findAllByStoreId(long storeId);

    List<OrderQueue> findAllByHavingOrderProcessing(boolean isProcessing);
}
