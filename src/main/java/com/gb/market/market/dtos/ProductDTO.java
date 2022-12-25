package com.gb.market.market.dtos;

import com.gb.market.market.entities.Product;
import lombok.Data;

@Data
public class ProductDTO implements Comparable<ProductDTO> {
    private long id;
    private String title;
    private int price;
    private int amount;

    public ProductDTO() {
    }

    public ProductDTO createProductDTO(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        amount = 1;
        return this;
    }

    @Override
    public int compareTo(ProductDTO o) {
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
