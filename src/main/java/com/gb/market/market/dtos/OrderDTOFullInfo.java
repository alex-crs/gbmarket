package com.gb.market.market.dtos;

import com.gb.market.market.entities.Order;
import com.gb.market.market.entities.OrderItem;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Data
public class OrderDTOFullInfo {
    private Long orderId;
    private LocalDateTime createdAt;
    private int totalPrice;
    private String address;
    private String phone;
    private List<OrderItemDTO> items;

    public OrderDTOFullInfo createOrderDTOFullInfo(Order order) {
        this.orderId = order.getId();
        this.createdAt = order.getCreatedAt();
        this.totalPrice = order.getTotalPrice();
        this.address = order.getAddress();
        this.phone = order.getPhone();
        items = new ArrayList<>();
        order.getItems().stream().forEach(orderItem ->
                items.add(new OrderItemDTO().createOrderItemDTO(orderItem)));
        return this;
    }

}
