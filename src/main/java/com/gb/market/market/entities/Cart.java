package com.gb.market.market.entities;

import com.gb.market.market.dtos.ProductDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;

@NoArgsConstructor
@Data
public class Cart {
    private final LinkedList<ProductDTO> cartMap = new LinkedList<>();

    //добавляет элемент в список
    //если элемент уже существует, инкрементирует его количество
    public void addToCart(ProductDTO product) {
        ProductDTO currentProduct = cartMap.stream().filter(productDTO ->
                productDTO.getId() == product.getId()).findFirst().orElse(null);

        if (currentProduct == null) {
            cartMap.add(product);
        } else {
            increaseAmount(currentProduct);
        }
    }

    //удаляет элемент из корзины по id
    //если количество элементов продукта больше одного, то декрементирует количество
    public void deleteFromCart(long id) {
        ProductDTO currentProduct = cartMap.stream().filter(productDTO ->
                productDTO.getId() == id).findFirst().orElse(null);

        if (currentProduct != null && currentProduct.getAmount() > 1) {
            decreaseAmount(currentProduct);
        } else {
            cartMap.remove(currentProduct);
        }
    }

    //повышает количество продуктов в представлении
    private void increaseAmount(ProductDTO product) {
        int amount = product.getAmount();
        product.setAmount(++amount);
    }

    //понижает количество продуктов в представлении
    private void decreaseAmount(ProductDTO product) {
        int amount = product.getAmount();
        product.setAmount(--amount);
    }

}
