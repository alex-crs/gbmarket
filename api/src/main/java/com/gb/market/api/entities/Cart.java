package com.gb.market.api.entities;

import com.gb.market.api.dtos.CartItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@Data
public class Cart {
    private List<CartItem> cartMap;
    private BigDecimal cartCost;

    public Cart() {
        this.cartMap = new LinkedList<>();
    }

    public void clearCart() {
        cartMap.clear();
        reCalcCartCost();
    }

    public void reCalcCartCost() {
        cartCost = BigDecimal.ZERO;
        cartMap.forEach(cartItem -> cartCost = cartCost.add(cartItem
                .getPrice()
                .multiply(BigDecimal.valueOf(cartItem.getAmount()))));
    }

    public CartItem getItemByTitle(String title) {
        return cartMap.stream().filter(cartItem -> cartItem.getTitle().equals(title))
                .findFirst().orElse(null);
    }

    //TODO добавить возможность добавления пачки продуктов за 1 раз
    //добавляет элемент в список
    //если элемент уже существует, инкрементирует его количество
    public void addToCart(CartItem product) {
        CartItem currentProduct = cartMap.stream().filter(cartItem ->
                cartItem.getId() == product.getId()).findFirst().orElse(null);

        if (currentProduct == null) {
            cartMap.add(product);
        } else {
            increaseAmount(currentProduct);
        }
        reCalcCartCost();
    }

    //удаляет элемент из корзины по id
    //если количество элементов продукта больше одного, то декрементирует количество
    public void deleteFromCart(long id) {
        CartItem currentProduct = cartMap.stream().filter(cartItem ->
                cartItem.getId() == id).findFirst().orElse(null);

        if (currentProduct != null && currentProduct.getAmount() > 1) {
            decreaseAmount(currentProduct);
        } else {
            cartMap.remove(currentProduct);
        }
        reCalcCartCost();
    }

    public void deleteProductWithAmountsFromCart(Long id) {
        CartItem currentProduct = cartMap.stream().filter(cartItem ->
                cartItem.getId() == id).findFirst().orElse(null);

        if (currentProduct != null) {
            cartMap.remove(currentProduct);
        }
        reCalcCartCost();
    }

    //повышает количество продуктов в представлении
    private void increaseAmount(CartItem cartItem) {
        int amount = cartItem.getAmount();
        cartItem.setAmount(++amount);
    }

    //понижает количество продуктов в представлении
    private void decreaseAmount(CartItem cartItem) {
        int amount = cartItem.getAmount();
        cartItem.setAmount(--amount);
    }

}
