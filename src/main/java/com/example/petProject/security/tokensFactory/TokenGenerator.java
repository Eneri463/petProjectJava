package com.example.petProject.security.tokensFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;

@Component
public class TokenGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenGenerator.class);
    private Duration refreshTokenTtl = Duration.ofDays(1);
    private Duration accessTokenTtl = Duration.ofMinutes(5);
    @Value("${jwt.secret.access}")
    String jwtAccessSecretKey;
    @Value("${jwt.secret.refresh}")
    String jwtRefreshSecretKey;

    private SecretKey jwtAccessSecret;
    private SecretKey jwtRefreshSecret;

    @PostConstruct
    private void afterConstruct()
    {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecretKey));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecretKey));
    }

    public String generateAccessToken(Authentication authentication) {

        String username = authentication.getName();

        Instant currentDate = Instant.now();
        Instant expireDate = currentDate.plus(accessTokenTtl);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(currentDate))
                .setExpiration(Date.from(expireDate))
                .signWith(jwtAccessSecret , SignatureAlgorithm.HS512)
                .claim("roles", authentication.getAuthorities())
                .compact();

        return token;
    }

    public String generateRefreshToken(Authentication authentication) {
        String username = authentication.getName();

        Instant currentDate = Instant.now();
        Instant expireDate = currentDate.plus(refreshTokenTtl);

        var authorities = new LinkedList<String>();
        authorities.add("JWT_REFRESH");
        authorities.add("JWT_LOGOUT");

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(currentDate))
                .setExpiration(Date.from(expireDate))
                .signWith(jwtRefreshSecret , SignatureAlgorithm.HS512)
                .claim("roles", authentication.getAuthorities())
                .compact();

        return token;
    }

    public boolean validateAccessToken(String string) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtAccessSecret)
                    .build()
                    .parseClaimsJws(string);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return false;
    }

    public boolean validateRefreshToken(String string) {

        try {
            Jwts.parser()
                    .setSigningKey(jwtRefreshSecret)
                    .build()
                    .parseClaimsJws(string);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return false;
    }

    public String getUsernameFromAccessToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtAccessSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}
