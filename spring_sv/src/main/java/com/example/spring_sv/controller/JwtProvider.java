package com.example.spring_sv.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;
import com.example.spring_sv.model.User;

import java.util.Date;

import javax.crypto.SecretKey;

@Component
public class JwtProvider {

    private final SecretKey key;
    private final long exp = 1000L * 60 * 60; 

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }


    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("userId", user.getNum());
        claims.put("savedUsername", user.getUsername());
        claims.put("savedPassword", user.getPassword());

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + exp))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }
    
    public String getAccount(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }
}