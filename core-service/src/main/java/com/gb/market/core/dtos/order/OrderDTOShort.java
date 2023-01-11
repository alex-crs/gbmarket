package com.gb.market.core.dtos.order;

import com.gb.market.core.entities.Order;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDTOShort {
    private Long orderId;
    private LocalDateTime createdAt;
    private int totalPrice;

    public OrderDTOShort createOrderDTO(Order order) {
        this.orderId = order.getId();
        this.createdAt = order.getCreatedAt();
        this.totalPrice = order.getTotalPrice();
        return this;
    }
}
