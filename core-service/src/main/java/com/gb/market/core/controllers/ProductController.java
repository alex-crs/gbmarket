package com.gb.market.core.controllers;

import com.gb.market.api.dtos.ResourceNotFoundException;
import com.gb.market.core.converters.ProductConverter;
import com.gb.market.core.dtos.ProductFullInfo;
import com.gb.market.core.dtos.ViewDTO;
import com.gb.market.core.entities.Product;
import com.gb.market.core.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@CrossOrigin("*") //отключает CORS запрет
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ViewDTO findAllProducts(@RequestBody ViewDTO viewDTO) {
        return ProductConverter.convertToViewDTO(productService.findAll(viewDTO));
//        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product findProductByID(@PathVariable Long id) {
        return productService.findProductById(id).orElseThrow(() ->
                new ResourceNotFoundException("Продукт не найден, id: " + id));
    }

    @GetMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @GetMapping("/info/{id}")
    public ProductFullInfo findProductFullInfoByID(@PathVariable Long id) {
        return ProductConverter.convertToProductFullInfo(productService.findProductById(id).orElseThrow(() ->
                new ResourceNotFoundException("Продукт не найден, id: " + id)));
    }

}
