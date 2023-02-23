package com.gb.market.core.services;

import com.gb.market.api.dtos.ResourceNotFoundException;
import com.gb.market.core.dtos.ViewDTO;
import com.gb.market.core.repositories.ProductRepository;
import com.gb.market.core.entities.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    @Value("${core-service.product-cache-prefix}")
    private String productPrefix;
    private final ProductRepository productRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    public Page<Product> findAll(ViewDTO viewDTO) {
        return productRepository.findAll(
                PageRequest.of(
                        viewDTO.getCurrentPage(),
                        viewDTO.getMaxItemsOnThePage(),
                        viewDTO.getSortType().equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC,
                        viewDTO.getSortBy()));
    }

    public Product findProductById(Long id) throws ResourceNotFoundException {
        Product product;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(addPrefix(id)))) {
            product = (Product) redisTemplate.opsForValue().get(addPrefix(id));
            log.info(String.format("Взято из кэша %s", id));
        } else {
            product = productRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Продукт не найден, id: " + id));
            if (product != null) {
                redisTemplate.opsForValue().set((addPrefix(id)), product);
            }
            log.info(String.format("Добавлено в кэш %s", id));
        }
        return product;
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }


    private String addPrefix(Long id) {
        return productPrefix + "_" + id;
    }

}
