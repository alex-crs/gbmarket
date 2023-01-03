package com.gb.market.market.controllers;

import com.gb.market.market.entities.Product;
import com.gb.market.market.exceptions.AppError;
import com.gb.market.market.exceptions.ResourceNotFoundException;
import com.gb.market.market.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> findAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product findProductByID(@PathVariable Long id) {
        return productService.findProductById(id).orElseThrow(() ->
                new ResourceNotFoundException("Продукт не найден, id: " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

}
