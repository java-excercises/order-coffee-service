package com.dbaotrung.example.coffee.order.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_queue")
@Data
public class OrderQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "store_id")
    private long storeId;

    @Column(name = "queue_index")
    private int queueIndex;

    @Column(name = "having_order_processing")
    private boolean havingOrderProcessing;
}
