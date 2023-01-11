package com.gb.market.carts.converter;

import com.gb.market.api.dtos.CartDTO;
import com.gb.market.carts.entities.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartConverter {
    private final CartItemConverter cartItemConverter;

    public CartDTO modelToDto(Cart cartItem){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartCost(cartItem.getCartCost());
        cartDTO.setCartMap(cartItem.getCartMap()
                .stream().map(cartItemConverter::modelToDto).collect(Collectors.toList()));
        return cartDTO;
    }

}
