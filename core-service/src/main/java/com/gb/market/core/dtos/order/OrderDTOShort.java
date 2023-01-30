package com.gb.market.core.dtos.order;

import com.gb.market.core.entities.Order;
import lombok.Data;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDTOShort {
    private Long orderId;
    private String createdAt;
    private BigDecimal totalPrice;
    private StringBuilder itemsView;

    public OrderDTOShort createOrderDTO(Order order) {
        itemsView = new StringBuilder();
        this.orderId = order.getId();
        this.createdAt = order.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        this.totalPrice = order.getTotalPrice();
        List<String> items = order.getItems().stream().map(orderItem -> orderItem.getProduct().getTitle()).collect(Collectors.toList());
        items.stream().limit(2)
                .forEach(title -> itemsView.append(title).append(items.size()>1?", ":""));
        if (items.size() > 1) {
            itemsView.append(" ...");
        }
        return this;
    }
}
