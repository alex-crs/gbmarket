package com.gb.market.api.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class CartDTO {
    private List<CartItemDTO> cartMap;
    private BigDecimal cartCost;

    public CartDTO() {
        cartMap = new ArrayList<>();
    }
}
