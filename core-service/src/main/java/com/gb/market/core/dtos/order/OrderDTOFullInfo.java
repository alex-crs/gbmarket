package com.gb.market.core.dtos.order;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTOFullInfo {
    private Long orderId;
    private String createdAt;
    private BigDecimal totalPrice;
    private String address;
    private String phone;
    private List<OrderItemDTO> items;

    public OrderDTOFullInfo() {
        this.items = new ArrayList<>();
    }
}
