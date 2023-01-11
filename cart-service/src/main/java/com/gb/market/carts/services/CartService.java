package com.gb.market.carts.services;

import com.gb.market.api.dtos.ProductDTO;
import com.gb.market.api.dtos.ResourceNotFoundException;
import com.gb.market.carts.integrations.ProductServiceIntegration;
import com.gb.market.carts.dtos.CartItem;
import com.gb.market.carts.entities.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
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
        ProductDTO productDTO = productServiceIntegration.getProductById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Продукт невозможно добавить id: " + productId));
        cart.addToCart(new CartItem().createCartItemDTO(productDTO));
    }
}
