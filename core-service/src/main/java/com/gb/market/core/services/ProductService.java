package com.gb.market.core.services;

import com.gb.market.api.dtos.ResourceNotFoundException;
import com.gb.market.core.repositories.ProductRepository;
import com.gb.market.core.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor //позволяет создать конструктор для каждого поля
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }


    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public Product getSoapProductById(Long id) {
        Product entityProduct = findProductById(id).orElseThrow(() -> new ResourceNotFoundException("Не обнаружен"));
        return entityToSoap(entityProduct);
    }

    public List<Product> getAllSoapProduct() {
        List<Product> soapProducts = new ArrayList<>();
        List<Product> entityProduct = findAll();
        entityProduct.forEach(product -> {
            soapProducts.add(entityToSoap(product));
        });
        return soapProducts;
    }

    private Product entityToSoap(Product product) {
        Product soapProduct = new Product();
        soapProduct.setTitle(product.getTitle());
        soapProduct.setPrice(product.getPrice());
        return soapProduct;
    }

}
