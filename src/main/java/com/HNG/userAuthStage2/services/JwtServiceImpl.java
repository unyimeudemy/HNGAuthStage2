package com.HNG.userAuthStage2.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.function.Function;


@Component
public class JwtServiceImpl {

    private static final String SECRET_KEY = System.getenv("JWT_SECRET");

    public  String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver ) {
        Claims claim = extractAllClaims(token);
        return claimResolver.apply(claim);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }



    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> otherDetails, UserDetails userDetails ){
        return Jwts
                .builder()
                .setClaims(otherDetails)
                .setSubject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 * 7 * 4))
                .signWith(getSecretKey())
                .compact();
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

