package com.gb.market.market.services;

import com.gb.market.market.dtos.CartItem;
import com.gb.market.market.entities.Cart;
import com.gb.market.market.entities.Product;
import com.gb.market.market.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private Cart cart;

    @PostConstruct
    public void init() {
        cart = new Cart();
    }

    // TODO пересмотреть логику этого метода
    public Cart getCurrentCart() {
        return cart;
    }

    //Добавляет в корзину пользователя продукт из базы данных (формируя DTO)
    public void addToCartFromBD(long productId) {
        Product product = productService.findProductById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Продукт невозможно добавить id: " + productId));
        cart.addToCart(new CartItem().createCartItemDTO(product));
    }
}
