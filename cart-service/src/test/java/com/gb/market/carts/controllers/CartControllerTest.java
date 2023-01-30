package com.gb.market.carts.controllers;

import com.gb.market.api.dtos.CartDTO;
import com.gb.market.api.dtos.ProductDTO;
import com.gb.market.api.dtos.CartItem;
import com.gb.market.api.dtos.UserDTO;
import com.gb.market.api.entities.Cart;
import com.gb.market.carts.integrations.ProductServiceIntegration;
import com.gb.market.carts.services.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerTest {

    ProductDTO savingProductDTO = new ProductDTO(1L, "TestProduct", BigDecimal.valueOf(15000));
    CartItem cartItem = new CartItem().createCartItemDTO(savingProductDTO);

    @Autowired
    CartService cartService;

    @MockBean
    ProductServiceIntegration productServiceIntegration;

    @Autowired
    WebTestClient webTestClient;

    {
    }

    @Test
    void addProductToCart() {
        webTestClient.get().uri("/api/v1/cart/Test/clear").exchange();
        Mockito.when(productServiceIntegration.getProductById(1L)).thenReturn(savingProductDTO);
        webTestClient.get().uri("/api/v1/cart/Test/add/" + 1L).exchange();

        CartDTO savedCartItem = webTestClient.get().uri("/api/v1/cart/Test")
                .exchange()
                .expectBody(CartDTO.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(savedCartItem);
        Assertions.assertEquals(savingProductDTO.getPrice(), savedCartItem.getCartCost());
        Assertions.assertEquals(savingProductDTO.getTitle(), savedCartItem.getCartMap().get(0).getTitle());

        //проверяем логику увеличения количества продуктов
        webTestClient.get().uri("/api/v1/cart/Test/add/" + 1L).exchange();
        Assertions.assertEquals(2, cartService.getCurrentCart("Test", "Test").getItemByTitle("TestProduct").getAmount());
        webTestClient.get().uri("/api/v1/cart/Test/clear").exchange();
    }

    @Test
    void deleteProductFromCart() {
        webTestClient.get().uri("/api/v1/cart/Test/clear").exchange();
        Mockito.when(productServiceIntegration.getProductById(1L)).thenReturn(savingProductDTO);
        for (int i = 0; i < 2; i++) {
            webTestClient.get().uri("/api/v1/cart/Test/add/" + 1L).exchange();
        }

        //удаляем один продукт, проверяем количество
        webTestClient.get().uri("/api/v1/cart/Test/delete/" + 1L).exchange();
        CartDTO savedCartItem = webTestClient.get().uri("/api/v1/cart/Test")
                .exchange()
                .expectBody(CartDTO.class)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(savedCartItem);
        Assertions.assertEquals(1, savedCartItem.getCartMap().get(0).getAmount());

        //удаляем последний продукт, корзина должна стать пустой
        webTestClient.get().uri("/api/v1/cart/Test/delete/" + 1L).exchange();
        CartItem returnCartItem = cartService.getCurrentCart("Test", "Test").getItemByTitle("TestProduct");
        Assertions.assertNull(returnCartItem);
        webTestClient.get().uri("/api/v1/cart/Test/clear").exchange();
    }

    @Test
    void clearCart() {
        cartService.getCurrentCart("Test", "Test").addToCart(cartItem);
        webTestClient.get().uri("/api/v1/cart/delete/clear").exchange();
        Cart cart = cartService.getCurrentCart("Test", "Test");
        Assertions.assertEquals(0, cart.getCartMap().size());
        webTestClient.get().uri("/api/v1/cart/Test/clear").exchange();
    }

    @Test
    void deleteProductWithAmounts() {
        for (int i = 0; i < 2; i++) {
            cartService.getCurrentCart("Test", "Test").addToCart(cartItem);
        }

        //удаляем еще один продукт, продукт должен быть полностью удален из корзины
        webTestClient.get().uri("/api/v1/cart/Test/delete/productAmounts/" + 1L).exchange();
        Assertions.assertEquals(0, cartService.getCurrentCart("Test", "Test").getCartMap().size());
        webTestClient.get().uri("/api/v1/cart/Test/clear").exchange();
    }
}