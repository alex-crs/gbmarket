package com.gb.market.carts.services;

import com.gb.market.api.dtos.ProductDTO;
import com.gb.market.carts.integrations.ProductServiceIntegration;
import com.gb.market.api.dtos.CartItem;
import com.gb.market.api.entities.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CartService {
    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;
    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;

    public Cart getCurrentCart(String username, String uuid) {
        String targetUuid = addPrefix(getCartUuid(username, uuid));
        if (Boolean.FALSE.equals(redisTemplate.hasKey(targetUuid))) {
            redisTemplate.opsForValue().set(targetUuid, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(targetUuid);
    }

    public void mergeCarts(String username, String uuid) {
        //добавляем префиксы корзинам
        String registeredUserCartName = addPrefix(username);
        String generatedUuidCartName = addPrefix(uuid);

        Cart registeredUserCart;
        Cart generatedUuidCart;

        //инициализируем корзину зарегистрированного пользователя
        if (Boolean.TRUE.equals(redisTemplate.hasKey(registeredUserCartName))) {
            registeredUserCart = (Cart) redisTemplate.opsForValue().get(registeredUserCartName);
        } else {
            registeredUserCart = new Cart();
        }

        //инициализируем корзину гостевого пользователя
        if (Boolean.TRUE.equals(redisTemplate.hasKey(generatedUuidCartName))) {
            generatedUuidCart = (Cart) redisTemplate.opsForValue().get(generatedUuidCartName);
        } else {
            generatedUuidCart = new Cart();
        }

        assert generatedUuidCart != null;
        assert registeredUserCart != null;

        //объединяем корзины и очищаем гостевую корзину
        //при этом если у пользователя уже были эти продукты в корзине - они складываются
        generatedUuidCart.getCartMap().forEach(registeredUserCart::addToCart);
        generatedUuidCart.clearCart();

        //сохраняем корзины
        //можно было бы не очищать корзину гостевого пользователя, но тогда при каждом последующем входе
        //она будет склеиваться, а если поставить какой-нибудь флаг, то это усложняет логику...
        redisTemplate.opsForValue().set(generatedUuidCartName, generatedUuidCart);
        redisTemplate.opsForValue().set(registeredUserCartName, registeredUserCart);
    }

    public void addToCartFromBD(String username, String uuid, long productId) {
        ProductDTO productDTO = productServiceIntegration.getProductById(productId);
        execute(username, uuid, cart -> cart.addToCart(new CartItem().createCartItemDTO(productDTO)));
    }

    public void deleteFromCart(String username, String uuid, long productId) {
        execute(username, uuid, cart -> cart.deleteFromCart(productId));
    }

    public void deleteFromCartWithAllAmounts(String username, String uuid, long productId) {
        execute(username, uuid, cart -> cart.deleteProductWithAmountsFromCart(productId));
    }

    //TODO исключить удаленную корзину из редиса
    public void clearCart(String username, String uuid) {
        execute(username, uuid, Cart::clearCart);
    }

    private void execute(String username, String uuid, Consumer<Cart> cartConsumer) {
        Cart cart = getCurrentCart(username, uuid);
        cartConsumer.accept(cart);
        redisTemplate.opsForValue().set(addPrefix(getCartUuid(username, uuid)), cart);
    }

    private String addPrefix(String name) {
        return cartPrefix + "_" + name;
    }


    private String getCartUuid(String username, String uuid) {
        if (username != null) {
            return username;
        }
        return uuid;
    }

}
