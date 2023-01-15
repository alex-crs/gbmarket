package com.gb.market.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding //поля класса заполняются автоматически через конструктор
@ConfigurationProperties(prefix = "integrations.cart-service")
@Data
public class CartsServiceIntegrationProperties {
    private String url;
    private Integer connectTimeout;
    private Integer readTimeout;
    private Integer writeTimeout;

}
