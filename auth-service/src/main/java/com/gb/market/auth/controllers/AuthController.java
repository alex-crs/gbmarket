package com.gb.market.auth.controllers;

import com.gb.market.api.dtos.RegistrationUserDto;
import com.gb.market.auth.configs.KeyCloakConfig;
import com.gb.market.auth.services.KeyCloakService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RestController
@Slf4j
//@CrossOrigin("*")
public class AuthController {
    private final KeyCloakService service;
    private final KeyCloakConfig keyCloakConfig;

    public AuthController(KeyCloakService service, KeyCloakConfig keyCloakConfig) {
        this.service = service;
        this.keyCloakConfig = keyCloakConfig;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> addUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (service.addUser(registrationUserDto).getStatus()==201) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}


