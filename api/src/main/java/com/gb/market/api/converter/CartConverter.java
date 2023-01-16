package com.gb.market.api.converter;

import com.gb.market.api.dtos.CartDTO;
import com.gb.market.api.dtos.CartItem;
import com.gb.market.api.dtos.CartItemDTO;
import com.gb.market.api.dtos.ProductDTO;
import com.gb.market.api.entities.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Data
public class CartConverter {

    public static CartDTO convertToCartDTO(Cart cart){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartCost(cart.getCartCost());
        cartDTO.setCartMap(cart.getCartMap()
                .stream().map(CartConverter::convertToCartItemDTO).collect(Collectors.toList()));
        return cartDTO;
    }

    public static CartItemDTO convertToCartItemDTO(CartItem cartItem){
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setPrice(cartItem.getPrice());
        cartItemDTO.setAmount(cartItem.getAmount());
        cartItemDTO.setTitle(cartItem.getTitle());
        cartItemDTO.setId(cartItem.getId());
        return cartItemDTO;
    }
}
