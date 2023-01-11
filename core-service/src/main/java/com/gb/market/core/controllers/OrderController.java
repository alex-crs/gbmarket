package com.gb.market.core.controllers;

import com.gb.market.core.dtos.order.DTOConverter;
import com.gb.market.api.dtos.ResourceNotFoundException;
import com.gb.market.core.dtos.order.OrderDTOFullInfo;
import com.gb.market.core.dtos.order.OrderDTOShort;
import com.gb.market.core.dtos.order.OrderInfo;
import com.gb.market.core.integrations.CartServiceIntegration;
import com.gb.market.core.repositories.OrderRepository;
//import com.gb.market.core.dtos.DTOConverter;
import com.gb.market.core.entities.Order;
import com.gb.market.core.entities.User;
//import com.gb.market.core.exceptions.ResourceNotFoundException;
//import com.gb.market.core.services.CartService;
import com.gb.market.core.services.OrderService;
import com.gb.market.core.services.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final CartServiceIntegration cartServiceIntegration;
//    private final CartService cartService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(Principal principal, @RequestBody OrderInfo orderInfo) {
        if (principal != null && cartServiceIntegration.getCart().get().getCartMap().size() > 0) {
            User user = userService.findByUsername(principal.getName()).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("У пользователя %s еще нет заказов", principal.getName())));
            orderService.createOrder(user, orderInfo);
            cartServiceIntegration.clearCart();
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else if (cartServiceIntegration.getCart().get().getCartMap().size() == 0) {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/checkAll")
    public List<OrderDTOShort> checkOrders(Principal principal) {
        if (principal != null) {
            List<Order> orderPerUser = orderRepository.findAllByUserName(principal.getName()); //TODO тут ошибка на пустой заказ
            List<OrderDTOShort> ordersDTO = new ArrayList<>();
            orderPerUser.forEach(order -> ordersDTO.add(new OrderDTOShort().createOrderDTO(order)));
            return ordersDTO;
        }
        return new ArrayList<>();
    }

    @GetMapping("/check/{id}")
    public OrderDTOFullInfo checkOrderInfo(Principal principal, @PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent() && principal.getName().equals(order.get().getUser().getName())) {
            return DTOConverter.createOrderDTOFullInfoFromOrder(order.get());
        }
        return null;
    }

}
