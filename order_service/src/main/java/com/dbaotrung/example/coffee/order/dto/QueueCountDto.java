package com.dbaotrung.example.coffee.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueueCountDto {
    private long queueId;
    private int count;
}
