package com.gb.market.core.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductFullInfo {
    private long id;
    private String title;
    private BigDecimal price;
    private String description;
}
