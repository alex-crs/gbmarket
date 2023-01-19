package com.gb.market.core.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductFullInfo {
    private long id;
    private String title;
    private int price;
    private String description;
}
