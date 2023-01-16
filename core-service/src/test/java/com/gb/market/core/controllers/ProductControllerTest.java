package com.gb.market.core.controllers;

import com.gb.market.core.entities.Product;
import com.gb.market.core.repositories.ProductRepository;
import com.gb.market.core.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest{

    static Product product;

    @Autowired
    ProductService productService;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ProductRepository productRepository;

    static {
        product = new Product();
        product.setTitle("TestProduct");
        product.setPrice(350);
    }

    @Test
    void findAllProducts() {
        productRepository.save(product);
        List<?> productList = webTestClient.get()
                .uri("/api/v1/products")
                .exchange()
                .expectBody(List.class)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(productList);
        Assertions.assertTrue(productList.size() > 0);
    }

    @Test
    void findProductByID() {
        Product product = webTestClient.get()
                .uri("/api/v1/products/" + 1L)
                .exchange()
                .expectBody(Product.class)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(product);
        Assertions.assertNotNull(product.getTitle());
    }

    @Test
    void deleteProductById() {
        productRepository.save(product);
        long id = product.getId();
        webTestClient.get()
                .uri("/api/v1/products/delete/" + id)
                .exchange().expectStatus().isAccepted();


        Product savedProduct = webTestClient.get()
                .uri("/api/v1/products/" + id)
                .exchange()
                .expectBody(Product.class)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(savedProduct);
        Assertions.assertNull(savedProduct.getTitle());
    }
}