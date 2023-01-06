package com.gb.market.market.services;

import com.gb.market.market.entities.Cart;
import com.gb.market.market.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductService productService;

    public void createOrder(User user){
        Cart cart = new Cart();

    }
}
