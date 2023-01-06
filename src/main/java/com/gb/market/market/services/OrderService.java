package com.gb.market.market.services;

import com.gb.market.market.dtos.CartItem;
import com.gb.market.market.dtos.OrderInfo;
import com.gb.market.market.entities.*;
import com.gb.market.market.exceptions.ResourceNotFoundException;
import com.gb.market.market.repositories.OrderItemRepository;
import com.gb.market.market.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;

    public void createOrder(User user, OrderInfo orderInfo) {

        Order order = new Order();
        order.setAddress(orderInfo.getAddress());
        order.setPhone(orderInfo.getPhone());
        order.setUser(user);
        order.setTotalPrice(cartService.getCurrentCart().getCartCost());
        orderRepository.save(order);
        cartService.getCurrentCart().getCartMap().forEach(cartItem -> {
            Product product = productService.findProductById(cartItem.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            String.format("Продукт с id: %s не найден", cartItem.getId())));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setAmount(cartItem.getAmount());
            orderItem.setPricePerProduct(cartItem.getPrice());
            orderItem.setTotalPrice(cartItem.getPrice() * cartItem.getAmount());
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
        });
    }
}
