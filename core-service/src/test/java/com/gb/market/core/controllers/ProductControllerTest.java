package com.gb.market.core.controllers;

import com.gb.market.api.dtos.ProductDTO;
import com.gb.market.core.dtos.ViewDTO;
import com.gb.market.core.entities.Product;
import com.gb.market.core.repositories.ProductRepository;
import com.gb.market.core.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    static Product product;

    static ViewDTO requestViewDTO;

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

        requestViewDTO = new ViewDTO();
        requestViewDTO.setCurrentPage(0);
        requestViewDTO.setSortType("ASC");
        requestViewDTO.setSortBy("id");
        requestViewDTO.setMaxItemsOnThePage(Integer.MAX_VALUE);
    }

    @Test
    void findAllProducts() {
        product = productRepository.save(product);
        ViewDTO viewDTOS = webTestClient.post()
                .uri("/api/v1/products")
                .body(BodyInserters.fromValue(requestViewDTO))
                .exchange()
                .expectBody(ViewDTO.class)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(viewDTOS);
        List<Product> productList = viewDTOS.getProductList();
        Product savedProduct = productList.stream().filter((e) -> e.getTitle()
                .equals(product.getTitle())).findFirst().orElse(null);
        Assertions.assertTrue(productList.size() > 0);
        Assertions.assertNotNull(savedProduct);
        Assertions.assertEquals(product.getTitle(), savedProduct.getTitle());
        Assertions.assertEquals(product.getPrice(), savedProduct.getPrice());
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