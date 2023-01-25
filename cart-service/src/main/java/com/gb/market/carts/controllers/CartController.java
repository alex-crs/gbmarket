package com.gb.market.carts.controllers;

import com.gb.market.api.converter.CartConverter;
import com.gb.market.api.dtos.CartDTO;
import com.gb.market.api.dtos.StringResponse;
import com.gb.market.carts.services.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
//@CrossOrigin("*") //обрабатывает любые запросы
public class CartController {
    private final CartService cartService;
    private final Logger logger = LoggerFactory.getLogger(CartController.class);


    @GetMapping("/generateUuid/newUuid")
    public StringResponse generateUuid(){
        StringResponse stringResponse = new StringResponse(UUID.randomUUID().toString());
        logger.info("Генерирую UUID");
        return stringResponse;
    }

    @GetMapping("/{uuid}/add/{id}")
    public void addProductToCart(@RequestHeader(name = "username", required = false) String username,
                                 @PathVariable String uuid,
                                 @PathVariable Long id) {
        cartService.addToCartFromBD(getCartUuid(username, uuid), id);
    }

    @GetMapping("/{uuid}")
    public CartDTO getCurrentCart(@RequestHeader(name = "username", required = false) String username,
                                  @PathVariable String uuid) {
        return CartConverter.convertToCartDTO(cartService.getCurrentCart(getCartUuid(username, uuid)));
    }

    @GetMapping("/{uuid}/delete/{id}")
    public void deleteProductFromCart(@RequestHeader(name = "username", required = false) String username,
                                      @PathVariable String uuid,
                                      @PathVariable Long id) {
        if (id != null) {
            cartService.deleteFromCart(getCartUuid(username, uuid), id);
        }
    }

    @GetMapping("/{uuid}/clear")
    public void clearCart(@RequestHeader(name = "username", required = false) String username,
                          @PathVariable String uuid) {
        cartService.clearCart(getCartUuid(username, uuid));
    }

    @GetMapping("/{uuid}/delete/productAmounts/{id}")
    public void deleteProductWithAmounts(@RequestHeader(name = "username", required = false) String username,
                                         @PathVariable String uuid,
                                         @PathVariable Long id) {
        cartService.deleteFromCartWithAllAmounts(getCartUuid(username, uuid), id);
    }

    private String getCartUuid(String username, String uuid) {
        if (username != null) {
            return username;
        }
        return uuid;
    }
}
