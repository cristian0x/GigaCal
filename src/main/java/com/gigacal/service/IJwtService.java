package com.gigacal.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface IJwtService {

    String generateJwtToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    String extractEmail(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    boolean isTokenValid(UserDetails userDetails, String refreshToken);

}