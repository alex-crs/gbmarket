package com.gb.market.core.dtos.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class OrderItemDTO {
    private String title;
    private int amount;
    private BigDecimal pricePerProduct;
    private BigDecimal totalPrice;
}
