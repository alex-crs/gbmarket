package com.gb.market.core.integrations;

import com.gb.market.api.dtos.CartDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private RestTemplate restTemplate;

    @PostConstruct
    public void init(){
        restTemplate = new RestTemplate();
    }

    public Optional<CartDTO> getCart() {
        return Optional.ofNullable(restTemplate
                .getForObject("http://localhost:8190/gbmarket/api/v1/cart", CartDTO.class));
    }

    public void clearCart(){
        restTemplate.getForObject("http://localhost:8190/gbmarket/api/v1/cart/delete/clear", String.class);
    }

}
