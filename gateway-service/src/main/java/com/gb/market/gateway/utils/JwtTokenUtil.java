package com.gb.market.gateway.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


@Component
@Slf4j
public class JwtTokenUtil {

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).get("preferred_username").asString();
    }

    public String getRoles(String token) {
        return getAllClaimsFromToken(token).get("realm_access").asMap().get("roles").toString();
    }

    public String[] getRolesList(String token) {
        return getAllClaimsFromToken(token)
                .get("realm_access")
                .asMap()
                .get("roles")
                .toString()
                .replaceAll("[],\\[]","")
                .split(" ");
    }

    public Map<String, Claim> getAllClaimsFromToken(String token) {
        DecodedJWT jwt;
        try {
            jwt = JWT.decode(token);
        } catch (JWTCreationException exception) {
            return null;
        }
        return jwt.getClaims();
    }

    public boolean isInvalid(String token) {
        return isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getAllClaimsFromToken(token).get("exp").asDate().before(new Date());
    }
}
