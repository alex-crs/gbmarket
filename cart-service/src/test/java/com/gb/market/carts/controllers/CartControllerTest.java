package com.gb.market.carts.controllers;

import com.gb.market.api.dtos.ProductDTO;
import com.gb.market.api.dtos.CartItem;
import com.gb.market.api.entities.Cart;
import com.gb.market.carts.integrations.ProductServiceIntegration;
import com.gb.market.carts.services.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerTest {

//    ProductDTO savingProductDTO = new ProductDTO(1L, "TestProduct", 15000);
//    CartItem cartItem = new CartItem().createCartItemDTO(savingProductDTO);

    @Autowired
    CartService cartService;

    @MockBean
    ProductServiceIntegration productServiceIntegration;

    @Autowired
    WebTestClient webTestClient;

//    @Test
//    void addProductToCart() {
////        Mockito.when(productServiceIntegration.getProductById(1L)).thenReturn(savingProductDTO);
////        webTestClient.get().uri("/api/v1/cart/add/" + 1L).exchange();
////
////        CartItem savedCartItem = cartService.getCurrentCart().getItemByTitle("TestProduct");
////
////        Assertions.assertNotNull(savedCartItem);
////        Assertions.assertEquals(savingProductDTO.getTitle(), savedCartItem.getTitle());
////        Assertions.assertEquals(savingProductDTO.getPrice(), savedCartItem.getPrice());
////
////        //проверяем логику увеличения количества продуктов
////        webTestClient.get().uri("/api/v1/cart/add/" + 1L).exchange();
////        Assertions.assertEquals(2, cartService.getCurrentCart().getItemByTitle("TestProduct").getAmount());
//    }

//    @Test
//    void deleteProductFromCart() {
//        for (int i = 0; i < 2; i++) {
////            cartService.getCurrentCart().addToCart(cartItem);
//        }
//
//        //удаляем один продукт, проверяем количество
//        webTestClient.get().uri("/api/v1/cart/delete/" + 1L).exchange();
////        Assertions.assertEquals(1, cartService.getCurrentCart().getItemByTitle("TestProduct").getAmount());
//
//        //удаляем последний продукт, корзина должна стать пустой
//        webTestClient.get().uri("/api/v1/cart/delete/" + 1L).exchange();
////        CartItem returnCartItem = cartService.getCurrentCart().getItemByTitle("TestProduct");
//        Assertions.assertNull(returnCartItem);
//    }

//    @Test
//    void clearCart() {
//////        cartService.getCurrentCart().addToCart(cartItem);
////        webTestClient.get().uri("/api/v1/cart/delete/clear").exchange();
////        Cart cart = cartService.getCurrentCart();
////        Assertions.assertEquals(0, cart.getCartMap().size());
//    }

//    @Test
//    void deleteProductWithAmounts() {
////        for (int i = 0; i < 2; i++) {
//////            cartService.getCurrentCart().addToCart(cartItem);
////        }
////
////        //удаляем еще один продукт, продукт должен быть полностью удален из корзины
////        webTestClient.get().uri("/api/v1/cart/delete/productAmounts/" + 1L).exchange();
////        Assertions.assertEquals(0, cartService.getCurrentCart().getCartMap().size());
//    }
}