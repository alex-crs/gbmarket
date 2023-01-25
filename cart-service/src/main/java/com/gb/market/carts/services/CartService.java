package com.gb.market.carts.services;

import com.gb.market.api.dtos.ProductDTO;
import com.gb.market.carts.integrations.ProductServiceIntegration;
import com.gb.market.api.dtos.CartItem;
import com.gb.market.api.entities.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;
    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;

    //TODO почистить
//    private Map<String, Cart> cart;
//
//    @PostConstruct
//    public void init() {
//        cart = new HashMap<>();
//    }

    public Cart getCurrentCart(String uuid) {
        String targetUuid = cartPrefix + "_" + uuid;
        if (Boolean.FALSE.equals(redisTemplate.hasKey(targetUuid))) {
            redisTemplate.opsForValue().set(uuid, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(targetUuid);
    }

    //TODO почистить этот метод
    //Добавляет в корзину пользователя продукт из базы данных (формируя DTO)
    public void addToCartFromBD(String uuid, long productId) {
        ProductDTO productDTO = productServiceIntegration.getProductById(productId);
//        Cart cart = getCurrentCart(uuid);
//        cart.addToCart(new CartItem().createCartItemDTO(productDTO));
//        redisTemplate.opsForValue().set(uuid, cart);
        execute(uuid, cart -> cart.addToCart(new CartItem().createCartItemDTO(productDTO)));
    }

    public void deleteFromCart(String uuid, long productId) {
        execute(uuid, cart -> cart.deleteFromCart(productId));
    }

    public void deleteFromCartWithAllAmounts(String uuid, long productId) {
        execute(uuid, cart -> cart.deleteProductWithAmountsFromCart(productId));
    }

    //TODO исключить удаленную корзину из редиса
    public void clearCart(String uuid) {
        execute(uuid, Cart::clearCart);
    }

    private void execute(String uuid, Consumer<Cart> cartConsumer) {
        Cart cart = getCurrentCart(uuid);
        cartConsumer.accept(cart);
        redisTemplate.opsForValue().set(cartPrefix + "_" + uuid, cart);
    }

}
