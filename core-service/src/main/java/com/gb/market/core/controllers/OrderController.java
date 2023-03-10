package com.gb.market.core.controllers;

import com.gb.market.api.dtos.AppError;
import com.gb.market.api.dtos.CurrentUser;
import com.gb.market.core.dtos.order.DTOConverter;
import com.gb.market.core.dtos.order.OrderDTOFullInfo;
import com.gb.market.core.dtos.order.OrderDTOShort;
import com.gb.market.api.dtos.OrderInfo;
import com.gb.market.core.integrations.CartServiceIntegration;
import com.gb.market.core.integrations.LoggerRMQBridge;
import com.gb.market.core.repositories.OrderRepository;
import com.gb.market.core.entities.Order;
import com.gb.market.core.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
//@CrossOrigin("*") //обрабатывает любые запросы
public class OrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final CartServiceIntegration cartServiceIntegration;

    private final LoggerRMQBridge loggerMQ = new LoggerRMQBridge(OrderController.class);

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createOrder(@RequestHeader(required = false) String username, @RequestBody OrderInfo orderInfo) {
        if (username != null && cartServiceIntegration.getCart(username).getCartMap().size() > 0) {
            orderService.createOrder(username, orderInfo);
            cartServiceIntegration.clearCart(username);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else if (username != null && cartServiceIntegration.getCart(username).getCartMap().size() == 0) {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/checkAll")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTOShort> checkOrders(@RequestHeader String username) {
        loggerMQ.sendLog(username + " запросил список заказов");
        if (username != null) {
            List<Order> orderPerUser = orderRepository.findAllByUserName(username);
            List<OrderDTOShort> ordersDTO = new ArrayList<>();
            orderPerUser.forEach(order -> ordersDTO.add(new OrderDTOShort().createOrderDTO(order)));
            return ordersDTO;
        }
        return new ArrayList<>();
    }

    @PostMapping("/check/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTOFullInfo checkOrderInfo(@RequestHeader String username, @PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent() && username.equals(order.get().getUserName())) {
            return DTOConverter.createOrderDTOFullInfoFromOrder(order.get());
        }
        return null;
    }

}
