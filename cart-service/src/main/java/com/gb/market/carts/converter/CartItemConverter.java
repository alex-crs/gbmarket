package com.gb.market.carts.converter;

import com.gb.market.api.dtos.CartItemDTO;
import com.gb.market.carts.dtos.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemConverter {
    public CartItemDTO modelToDto(CartItem cartItem){
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setPrice(cartItem.getPrice());
        cartItemDTO.setAmount(cartItem.getAmount());
        cartItemDTO.setTitle(cartItem.getTitle());
        cartItemDTO.setId(cartItem.getId());
        return cartItemDTO;
    }

}
