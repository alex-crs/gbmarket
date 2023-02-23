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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
//@CrossOrigin("*") //обрабатывает любые запросы
public class CartController {
    private final CartService cartService;

    @GetMapping("/generateUuid/newUuid")
    @ResponseStatus(HttpStatus.OK)
    public StringResponse generateUuid(){
        return new StringResponse(UUID.randomUUID().toString());
    }

    @GetMapping("/{uuid}/add/{id}")
    public void addProductToCart(@RequestHeader(name = "username", required = false) String username,
                                 @PathVariable String uuid,
                                 @PathVariable Long id) {
         cartService.addToCartFromBD(username, uuid, id);
    }

    @GetMapping("/{uuid}")
    public CartDTO getCurrentCart(@RequestHeader(name = "username", required = false) String username,
                                  @PathVariable String uuid) {
        return CartConverter.convertToCartDTO(cartService.getCurrentCart(username, uuid));
    }

    @GetMapping("/{uuid}/delete/{id}")
    public void deleteProductFromCart(@RequestHeader(name = "username", required = false) String username,
                                      @PathVariable String uuid,
                                      @PathVariable Long id) {
        if (id != null) {
            cartService.deleteFromCart(username, uuid, id);
        }
    }

    @GetMapping("/{uuid}/clear")
    public void clearCart(@RequestHeader(name = "username", required = false) String username,
                          @PathVariable String uuid) {
        cartService.clearCart(username, uuid);
    }

    @GetMapping("/{uuid}/delete/productAmounts/{id}")
    public void deleteProductWithAmounts(@RequestHeader(name = "username", required = false) String username,
                                         @PathVariable String uuid,
                                         @PathVariable Long id) {
        cartService.deleteFromCartWithAllAmounts(username, uuid, id);
    }

    @GetMapping("/{uuid}/mergeCarts")
    @ResponseStatus(HttpStatus.OK)
    public void mergeCarts(@RequestHeader(name = "username", required = false) String username,
                           @PathVariable String uuid){
        cartService.mergeCarts(username, uuid);
    }

}
