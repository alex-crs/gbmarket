package com.gb.market.market.controllers;

import com.gb.market.market.dtos.OrderDTOFullInfo;
import com.gb.market.market.dtos.OrderDTOShort;
import com.gb.market.market.dtos.OrderInfo;
import com.gb.market.market.entities.Order;
import com.gb.market.market.entities.User;
import com.gb.market.market.exceptions.AppError;
import com.gb.market.market.exceptions.GlobalExceptionHandler;
import com.gb.market.market.exceptions.ResourceNotFoundException;
import com.gb.market.market.repositories.OrderRepository;
import com.gb.market.market.services.CartService;
import com.gb.market.market.services.OrderService;
import com.gb.market.market.services.ProductService;
import com.gb.market.market.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(Principal principal, @RequestBody OrderInfo orderInfo) {
        if (principal != null && cartService.getCurrentCart().getCartMap().size() > 0) {
            User user = userService.findByUsername(principal.getName()).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("У пользователя %s еще нет заказов", principal.getName())));
            orderService.createOrder(user, orderInfo);
            cartService.getCurrentCart().clearCart();
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else if (cartService.getCurrentCart().getCartMap().size() == 0) {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/checkAll")
    public List<OrderDTOShort> checkOrders(Principal principal) {
        List<Order> orderPerUser = orderRepository.findAllByUserName(principal.getName());
        if (orderPerUser.size() > 0) {
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
            return new OrderDTOFullInfo().createOrderDTOFullInfo(order.get());
        }
        return null;
    }

}
