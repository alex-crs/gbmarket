package com.gb.market.carts.services;

import com.gb.market.api.dtos.ProductDTO;
import com.gb.market.api.dtos.CartItem;
import com.gb.market.api.entities.Cart;
import com.gb.market.carts.integrations.ProductServiceIntegration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;

@SpringBootTest
class CartServiceTest {

    ProductDTO productDTO = new ProductDTO(1L, "ProductTest", BigDecimal.valueOf(3500));

    Cart cart;

    @MockBean
    ProductServiceIntegration productServiceIntegration;

    @Autowired
    CartService cartService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    {
        cart = new Cart();
    }

    @Test
    void getCurrentCart() {
        Assertions.assertNotNull(cartService.getCurrentCart("Test", "Test"));
    }

    @Test
    void addToCartFromBD() {
        cartService.clearCart("Test", "Test");
        Mockito.when(productServiceIntegration.getProductById(1L)).thenReturn(productDTO);
        cartService.addToCartFromBD("Test", "Test", 1L);
        CartItem savedItem = cartService.getCurrentCart("Test", "Test").getItemByTitle("ProductTest");
        Assertions.assertEquals(savedItem.getTitle(), productDTO.getTitle());
        cartService.deleteFromCart("Test", "Test", 1L);
    }
}