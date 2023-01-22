package com.gb.market.auth.controllers;

import com.gb.market.auth.converters.UserConverter;
import com.gb.market.auth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestHeader String username){
        return ResponseEntity.ok(UserConverter.userToUserDTOConverter(userService.findUserInfoByUserName(username)));
    }
}
