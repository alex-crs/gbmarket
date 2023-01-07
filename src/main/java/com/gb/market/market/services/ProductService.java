package com.gb.market.market.services;

import com.gb.market.market.entities.Product;
import com.gb.market.market.exceptions.ResourceNotFoundException;
import com.gb.market.market.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
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

    public com.gb.market.market.soap.product.Product getSoapProductById(Long id) {
        Product entityProduct = findProductById(id).orElseThrow(() -> new ResourceNotFoundException("Не обнаружен"));
        return entityToSoap(entityProduct);
    }

    public List<com.gb.market.market.soap.product.Product> getAllSoapProduct() {
        List<com.gb.market.market.soap.product.Product> soapProducts = new ArrayList<>();
        List<Product> entityProduct = findAll();
        entityProduct.forEach(product -> {
            soapProducts.add(entityToSoap(product));
        });
        return soapProducts;
    }

    private com.gb.market.market.soap.product.Product entityToSoap(Product product) {
        com.gb.market.market.soap.product.Product soapProduct = new com.gb.market.market.soap.product.Product();
        soapProduct.setTitle(product.getTitle());
        soapProduct.setPrice(product.getPrice());
        return soapProduct;
    }

}
