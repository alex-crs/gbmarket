package com.gb.market.core.controllers;

import com.gb.market.api.dtos.ResourceNotFoundException;
import com.gb.market.core.entities.Product;
import com.gb.market.core.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@CrossOrigin("*") //обрабатывает любые запросы
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

    @GetMapping("/delete/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

}
