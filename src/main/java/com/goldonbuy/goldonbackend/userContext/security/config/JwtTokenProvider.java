package com.goldonbuy.goldonbackend.userContext.security.config;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;


@Component
public class JwtTokenProvider {

    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.token.expirationInMils}")
    private int expirationTime;

    public String generateToken(Authentication authentication) {

        String username = authentication.getName();
        Date expiredDate = new Date(new Date().getTime() + this.expirationTime);

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expiredDate)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Extract username from Jwt Token
    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Boolean validateToken(String token) {
        Jwts.parser().verifyWith((SecretKey) getKey())
                .build()
                .parse(token);

        return true;
    }

}
