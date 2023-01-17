package com.gb.market.core.controllers;

import com.gb.market.api.converter.CartConverter;
import com.gb.market.api.dtos.*;
import com.gb.market.core.converters.ProductConverter;
import com.gb.market.core.dtos.order.OrderDTOFullInfo;
import com.gb.market.core.dtos.order.OrderDTOShort;
import com.gb.market.core.entities.Order;
import com.gb.market.core.entities.Product;
import com.gb.market.core.integrations.CartServiceIntegration;
import com.gb.market.core.repositories.OrderRepository;
import com.gb.market.core.repositories.ProductRepository;
import com.gb.market.core.services.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest{

    static CartDTO cartDTO;

    static CartItemDTO cartItemDTO;

    static Product product;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @MockBean
    CartServiceIntegration cartServiceIntegration;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    WebTestClient webTestClient;

    static {
        product = new Product();
        cartDTO = new CartDTO();
    }

    void init() {
        product.setTitle("TestProduct");
        product.setPrice(3500);
        productRepository.save(product);
        ProductDTO productDTO = ProductConverter.convertToProductDTO(product);
        cartItemDTO = CartConverter.convertToCartItemDTO(new CartItem().createCartItemDTO(productDTO));
        cartDTO.setCartCost(cartItemDTO.getPrice());
        cartDTO.getCartMap().add(cartItemDTO);
        Mockito.when(cartServiceIntegration.getCart()).thenReturn(cartDTO);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUsername("bobsdgdgdfgdsharjre433523253g435");
        orderInfo.setAddress("adsad");
        orderInfo.setPhone("89513");
        webTestClient.post().uri("/api/v1/order/create")
                .body(BodyInserters.fromValue(orderInfo)).exchange().expectStatus().isCreated();
    }

    @Test
    void createOrder() {
        //инициализируем заказ
        init();

        List<Order> savedOrder = orderRepository.findAllByUserName("bobsdgdgdfgdsharjre433523253g435");

        Assertions.assertNotNull(savedOrder);
        Assertions.assertEquals(savedOrder.get(0).getUserName(), "bobsdgdgdfgdsharjre433523253g435");
        Assertions.assertEquals(savedOrder.get(0).getAddress(), "adsad");
        Assertions.assertEquals(savedOrder.get(0).getPhone(), "89513");
        Assertions.assertEquals(savedOrder.get(0).getItems().get(0).getProduct().getTitle(), "TestProduct");
    }

    @Test
    void checkOrders() {
        init();
        List<OrderDTOShort> savedDTOShort = webTestClient.get()
                .uri("/api/v1/order/checkAll/" + "bobsdgdgdfgdsharjre433523253g435")
                .exchange().expectStatus().isOk().expectBodyList(OrderDTOShort.class).returnResult().getResponseBody();

        Assertions.assertNotNull(savedDTOShort);
        Assertions.assertEquals(3500, savedDTOShort.get(0).getTotalPrice());
        Assertions.assertNotNull(savedDTOShort.get(0).getOrderId());
    }

    @Test
    void checkOrderInfo() {
        init();
        List<Order> savedOrder = orderRepository.findAllByUserName("bobsdgdgdfgdsharjre433523253g435");

        OrderDTOFullInfo savedDTOFull = webTestClient.post()
                .uri("/api/v1/order/check/" + savedOrder.get(0).getId())
                .body(BodyInserters.fromValue(new CurrentUser("bobsdgdgdfgdsharjre433523253g435")))
                .exchange().expectStatus().isOk().expectBody(OrderDTOFullInfo.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(savedDTOFull);
        Assertions.assertEquals(savedOrder.get(0).getId(), savedDTOFull.getOrderId());
        Assertions.assertEquals(savedOrder.get(0).getPhone(), savedDTOFull.getPhone());
        Assertions.assertEquals(savedOrder.get(0).getAddress(), savedDTOFull.getAddress());
        Assertions.assertEquals(savedOrder.get(0).getTotalPrice(), savedDTOFull.getTotalPrice());
        Assertions.assertEquals(savedOrder.get(0).getItems().get(0).getProduct().getTitle(),
                savedDTOFull.getItems().get(0).getTitle());
    }
}