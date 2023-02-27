package com.gb.market.core.controllers;

import com.gb.market.api.dtos.ResourceNotFoundException;
import com.gb.market.core.converters.ProductConverter;
import com.gb.market.core.dtos.ProductFullInfo;
import com.gb.market.core.dtos.ViewDTO;
import com.gb.market.core.entities.Product;
import com.gb.market.core.integrations.LoggerRMQBridge;
import com.gb.market.core.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
//@CrossOrigin("*") //отключает CORS запрет
public class ProductController {
    private final ProductService productService;

    private final LoggerRMQBridge loggerMQ = new LoggerRMQBridge(ProductController.class);

    @PostMapping
    public ViewDTO findAllProducts(@RequestBody ViewDTO viewDTO) {
        loggerMQ.sendLog("Получен список продуктов");
        return ProductConverter.convertToViewDTO(productService.findAll(viewDTO));
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addProduct(@RequestBody ProductFullInfo productFullInfo){
        Product addingProduct = Product.builder()
                .title(productFullInfo.getTitle())
                .price(productFullInfo.getPrice())
                .description(productFullInfo.getDescription())
                .build();
        productService.addProduct(addingProduct);
    }

    @GetMapping("/{id}")
    public Product findProductByID(@PathVariable Long id) {
        return productService.findProductById(id);
    }

    @GetMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @GetMapping("/info/{id}")
    public ProductFullInfo findProductFullInfoByID(@PathVariable Long id) {
        return ProductConverter.convertToProductFullInfo(productService.findProductById(id));
    }

}
