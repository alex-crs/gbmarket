package com.gb.market.market.entities;

import com.gb.market.market.dtos.CartItem;
import lombok.Data;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> cartMap;
    private int cartCost;

    public List<CartItem> getCartMap() {
        return Collections.unmodifiableList(cartMap);
    }

    public Cart() {
        this.cartMap = new LinkedList<>();
    }

    public void clearCart() {
        cartMap.clear();
        reCalcCartCost();
    }

    public void reCalcCartCost() {
        cartCost = 0;
        cartMap.forEach(cartItem -> cartCost += (cartItem.getPrice() * cartItem.getAmount()));
    }

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
