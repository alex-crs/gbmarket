package com.gb.market.core.services;

import com.gb.market.core.dtos.ViewDTO;
import com.gb.market.core.repositories.ProductRepository;
import com.gb.market.core.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<Product> findAll(ViewDTO viewDTO) {
        return productRepository.findAll(
                PageRequest.of(
                        viewDTO.getCurrentPage(),
                        viewDTO.getMaxItemsOnThePage(),
                        viewDTO.getSortType().equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC,
                        viewDTO.getSortBy()));
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

}
