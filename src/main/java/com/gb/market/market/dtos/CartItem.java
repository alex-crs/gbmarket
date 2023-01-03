package com.gb.market.market.dtos;

import com.gb.market.market.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem implements Comparable<CartItem> {
    private long id;
    private String title;
    private int price;
    private int amount;

    public CartItem createCartItemDTO(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        amount = 1;
        return this;
    }

    @Override
    public int compareTo(CartItem o) {
        int result = -1;
        if (this.id == o.getId()) {
            result++;
        }
        if (this.getTitle().equals(o.getTitle())) {
            result++;
        }
        return result;
    }
}
