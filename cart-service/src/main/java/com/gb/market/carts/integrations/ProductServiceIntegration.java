package com.gb.market.carts.integrations;

import com.gb.market.api.dtos.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final RestTemplate restTemplate;

    public Optional<ProductDTO> getProductById(Long id) {
        return Optional.ofNullable(restTemplate
                .getForObject("http://localhost:8189/gbmarket/api/v1/products/" + id, ProductDTO.class));
    }

}
