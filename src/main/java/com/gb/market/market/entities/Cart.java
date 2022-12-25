package com.gb.market.market.entities;

import com.gb.market.market.dtos.ProductDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.function.Consumer;

@NoArgsConstructor
@Data
public class Cart {
    private final LinkedList<ProductDTO> cartMap = new LinkedList<>();

    public void addToCart(ProductDTO product) {
        final boolean[] success = new boolean[1];
        cartMap.forEach(productDTO -> {
            if (productDTO.getId() == product.getId()) {
                int amount = productDTO.getAmount();
                productDTO.setAmount(++amount);
                success[0] = true;
            }
        });

        if (!success[0]) {
            cartMap.add(product);
        }
    }


    public void deleteFromCart(long id) {
        cartMap.forEach(productDTO -> {
            if (productDTO.getId() == id) {
                if (productDTO.getAmount() == 1) {
                    cartMap.remove(productDTO);
                } else {
                    int amount = productDTO.getAmount();
                    productDTO.setAmount(--amount);
                }
            }
        });
    }
}
