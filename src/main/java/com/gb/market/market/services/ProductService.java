package com.gb.market.market.services;

import com.gb.market.market.entities.Product;
import com.gb.market.market.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

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
}
