package com.gb.market.front.intrgrations;

import com.gb.market.api.dtos.jwt.JwtRequest;
import com.gb.market.api.dtos.jwt.JwtResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class JwtServiceIntegration {
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();
    }

    public JwtResponse tryToAuth(JwtRequest jwtRequest) throws HttpClientErrorException {
        JwtResponse jwtResponse = null;
        jwtResponse = restTemplate.postForObject("http://localhost:8189/gbmarket/auth", jwtRequest, JwtResponse.class);
        return jwtResponse;

    }
}
