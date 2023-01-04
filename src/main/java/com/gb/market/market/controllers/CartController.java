package com.gb.market.market.controllers;

import com.gb.market.market.entities.Cart;
import com.gb.market.market.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/add/{id}")
    public void addProductToCart(@PathVariable Long id) {
        cartService.addToCartFromBD(id);
    }

    @GetMapping
    public Cart getCurrentCart() {
        return cartService.getCurrentCart();
    }

    @GetMapping("/delete/{id}")
    public void deleteProductFromCart(@PathVariable Long id) {
        if (id != null) {
            cartService.getCurrentCart().deleteFromCart(id);
        }
    }

    @GetMapping("/delete/clear")
    public void clearCart() {
        cartService.getCurrentCart().clearCart();
    }

    @GetMapping("/delete/productAmounts/{id}")
    public void deleteProductWithAmounts(@PathVariable Long id) {
        cartService.getCurrentCart().deleteProductWithAmountsFromCart(id);
    }
}
