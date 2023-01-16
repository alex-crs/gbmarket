package com.gb.market.carts.services;

import com.gb.market.api.dtos.ProductDTO;
import com.gb.market.api.dtos.CartItem;
import com.gb.market.carts.integrations.ProductServiceIntegration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CartServiceTest {

    ProductDTO productDTO = new ProductDTO(1L, "ProductTest", 3500);

    @MockBean
    ProductServiceIntegration productServiceIntegration;

    @Autowired
    CartService cartService;

    @Test
    void getCurrentCart() {
        Assertions.assertNotNull(cartService.getCurrentCart());
    }

    @Test
    void addToCartFromBD() {
        Mockito.when(productServiceIntegration.getProductById(1L)).thenReturn(productDTO);
        cartService.addToCartFromBD(1L);
        CartItem savedItem = cartService.getCurrentCart().getItemByTitle("ProductTest");
        Assertions.assertEquals(savedItem.getTitle(), productDTO.getTitle());
    }
}