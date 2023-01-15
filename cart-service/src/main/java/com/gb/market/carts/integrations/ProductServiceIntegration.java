package com.gb.market.carts.integrations;

import com.gb.market.api.dtos.ProductDTO;
import com.gb.market.api.dtos.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final WebClient productServiceWebClient;

    public ProductDTO getProductById(Long id) {
        return productServiceWebClient.get().uri("api/v1/products/" + id)//то что добавляется в запрос (куки или хедеры)
                .retrieve()//можно добавить .header("username", username)
                .onStatus(httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(), //когда придет ошибка
                        clientResponse -> Mono.error(new ResourceNotFoundException("Товар не найден"))) //мы выполним следующее
                .bodyToMono(ProductDTO.class)
                //собираем класс (тело запроса преобразовывается к объекту типа .toBodilessEntity() - значит ответ не нужен
                .block();
    }

    //Может прийти Mono - он придет неизвестно когда (в ближайшем будущем)
    //Flux может прийти пачка объектов последовательно.

}
