package com.gb.market.front.controller;

import com.gb.market.api.dtos.jwt.JwtRequest;
import com.gb.market.api.dtos.jwt.JwtResponse;
import com.gb.market.front.intrgrations.JwtServiceIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthUserProxyController {
    private final JwtServiceIntegration jwtServiceIntegration;

    @PostMapping
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        JwtResponse jwtResponse;
        try {
            jwtResponse = jwtServiceIntegration.tryToAuth(authRequest);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(jwtResponse);
    }
}
