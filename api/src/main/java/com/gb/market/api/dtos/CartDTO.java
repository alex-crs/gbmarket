package com.gb.market.api.dtos;

import java.util.List;

public class CartDTO {
    private List<CartItemDTO> cartMap;
    private int cartCost;

    public List<CartItemDTO> getCartMap() {
        return cartMap;
    }

    public void setCartMap(List<CartItemDTO> cartMap) {
        this.cartMap = cartMap;
    }

    public int getCartCost() {
        return cartCost;
    }

    public void setCartCost(int cartCost) {
        this.cartCost = cartCost;
    }
}
