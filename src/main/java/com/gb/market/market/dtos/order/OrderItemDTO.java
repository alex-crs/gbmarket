package com.gb.market.market.dtos.order;

import lombok.Data;

@Data
public class OrderItemDTO {
    private String title;
    private int amount;
    private int pricePerProduct;
    private int totalPrice;


}
