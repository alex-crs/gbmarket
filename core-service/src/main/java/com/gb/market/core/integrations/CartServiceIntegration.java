package com.gb.market.core.integrations;

import com.gb.market.api.dtos.CartDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final WebClient cartsServiceIntegrations;

    public CartDTO getCart(String username) {
        return cartsServiceIntegrations.get().uri("api/v1/cart/0")
                .header("username",username)
                .retrieve()
                .bodyToMono(CartDTO.class)
                .block();
    }

    public void clearCart(String username) {
        cartsServiceIntegrations.get().uri("api/v1/cart/0/clear")
                .header("username",username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
