package com.gb.market.api.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDTO {
    private long id;
    private String title;
    private BigDecimal price;
    private int amount;

    public CartItemDTO() {
        this.amount = 1;
    }
}
