package com.dbaotrung.example.coffee.order.model;

import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "coffee_order")
@Data
public class CoffeeOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @JdbcTypeCode(SqlTypes.JSON)
    private List<OrderItem> items;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @Column(name = "sub_total")
    private double subTotal;
    private double vat;
    private double total;
    private long storeId;
    private String queueId;
    private int queueIndex;
}
