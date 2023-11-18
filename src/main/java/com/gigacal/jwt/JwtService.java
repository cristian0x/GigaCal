package com.gigacal.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.lang.System.currentTimeMillis;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.token-expiration}")
    private Long tokenExpiration;

    @Value("${application.security.jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    public String generateJwtToken(final UserDetails userDetails) {
        return this.generateToken(userDetails, this.tokenExpiration);
    }

    public String generateRefreshToken(final UserDetails userDetails) {
        return this.generateToken(userDetails, this.refreshTokenExpiration);
    }

    public String extractEmail(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(final UserDetails user, final String refreshToken) {
        final String email = this.extractEmail(refreshToken);

        return email.equals(user.getUsername()) && !this.isTokenExpired(refreshToken);
    }

    private boolean isTokenExpired(final String token) {
        return this.extractExpiration(token).before(new Date(currentTimeMillis()));
    }

    private Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(final UserDetails userDetails,
                                 final Long expirationTime) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(currentTimeMillis()))
                .setExpiration(new Date(currentTimeMillis() + expirationTime))
                .signWith(this.getSigningKey(), HS256)
                .compact();
    }

    private Key getSigningKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
