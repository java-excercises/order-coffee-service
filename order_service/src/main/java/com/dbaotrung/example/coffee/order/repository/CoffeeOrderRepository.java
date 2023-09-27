package com.dbaotrung.example.coffee.order.repository;

import com.dbaotrung.example.coffee.order.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {

    @Query("SELECT o.queueId as queueid, COUNT(o) as cnt FROM CoffeeOrder o WHERE o.storeId=:storeId AND o.queueId IS NOT NULL " +
            "  AND o.queueId <> '' GROUP BY o.queueId")
    List<Map<String, Object>> loadQueueCount(@Param("storeId") long storeId);
}
