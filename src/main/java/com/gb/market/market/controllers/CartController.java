package com.gb.market.market.controllers;

import com.gb.market.market.dtos.ProductDTO;
import com.gb.market.market.entities.Cart;
import com.gb.market.market.entities.Product;
import com.gb.market.market.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final ProductService productService;
    private static final Cart cart = new Cart();

    @PutMapping("/{id}")
    public void addProductToCart(@PathVariable Long id) {
        Product product = productService.findProductById(id).get();
        cart.addToCart(new ProductDTO().createProductDTO(product));
    }

    @GetMapping
    public LinkedList<ProductDTO> findCartProduct() {
        return cart.getCartMap();
    }

    @DeleteMapping("/{id}")
    public void deleteProductFromCart(@PathVariable Long id) {
        cart.deleteFromCart(id);
    }

}
