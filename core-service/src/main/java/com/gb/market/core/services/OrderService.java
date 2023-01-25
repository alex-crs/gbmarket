package com.gb.market.core.services;

import com.gb.market.api.dtos.CartDTO;
import com.gb.market.api.dtos.ResourceNotFoundException;
import com.gb.market.api.dtos.OrderInfo;
import com.gb.market.core.integrations.CartServiceIntegration;
import com.gb.market.core.repositories.OrderItemRepository;
import com.gb.market.core.repositories.OrderRepository;
import com.gb.market.core.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartServiceIntegration cartServiceIntegration;

    @Transactional
    public void createOrder(String user, OrderInfo orderInfo) {
        Order order = new Order();
        order.setAddress(orderInfo.getAddress());
        order.setPhone(orderInfo.getPhone());
        order.setUserName(user);
        CartDTO cartDTO = cartServiceIntegration.getCart(user);
        order.setTotalPrice(cartDTO.getCartCost());
        orderRepository.save(order);
        cartDTO.getCartMap().forEach(cartItem -> {
            Product product = productService.findProductById(cartItem.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            String.format("Продукт с id: %s не найден", cartItem.getId())));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setAmount(cartItem.getAmount());
            orderItem.setPricePerProduct(cartItem.getPrice());
            orderItem.setTotalPrice(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getAmount())));
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
        });
    }
}
