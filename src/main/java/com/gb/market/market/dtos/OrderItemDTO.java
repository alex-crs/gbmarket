package com.gb.market.market.dtos;

import com.gb.market.market.entities.OrderItem;
import lombok.Data;

@Data
public class OrderItemDTO {
    private String title;
    private int amount;
    private int pricePerProduct;
    private int totalPrice;

    public OrderItemDTO createOrderItemDTO(OrderItem orderItem) {
        this.title = orderItem.getProduct().getTitle();
        this.amount = orderItem.getAmount();
        this.pricePerProduct = orderItem.getPricePerProduct();
        this.totalPrice = orderItem.getTotalPrice();
        return this;
    }
}
