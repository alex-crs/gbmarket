package com.gb.market.api.dtos;

import lombok.Data;

@Data
public class CartItemDTO {
    private long id;
    private String title;
    private int price;
    private int amount;

    public CartItemDTO() {
        this.amount = 1;
    }
}
