package com.gb.market.market.dtos.order;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTOFullInfo {
    private Long orderId;
    private LocalDateTime createdAt;
    private int totalPrice;
    private String address;
    private String phone;
    private List<OrderItemDTO> items;

    public OrderDTOFullInfo() {
        this.items = new ArrayList<>();
    }
}
