package com.gb.market.market.controllers;

import com.gb.market.market.entities.User;
import com.gb.market.market.services.OrderService;
import com.gb.market.market.services.ProductService;
import com.gb.market.market.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public void createOrder(Principal principal, @RequestBody RequestData requestData){
//        User user = userService.findByUsername(principal.getName()).orElseThrow(()-> new RuntimeException()); //TODO исправить
//        orderService.createOrder(user);
//
//    }

}
