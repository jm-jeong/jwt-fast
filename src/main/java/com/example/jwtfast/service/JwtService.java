package com.example.jwtfast.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class JwtService {
    private static String secretKey = "java11SpringBootJWTTokenIssueMethod";

    public String create(Map<String, Object> claims, LocalDateTime expiredAt) {
//        SecretKey key = Jwts.SIG.HS256.key().build();
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Date _expiredAt = Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(claims)
                .setExpiration(_expiredAt)
                .compact();
    }

    public void validation(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        JwtParser parser = Jwts.parser().setSigningKey(key).build();

        try{
            var result = parser.parseClaimsJws(token);
            result.getBody().entrySet().forEach(value ->{
                log.info("key : {}, value : {}", value.getKey(), value.getValue());
            });

        }catch (Exception e){
            if(e instanceof SignatureException){
                throw new RuntimeException("JWT Token Not Valid Exception");
            }
            else if(e instanceof ExpiredJwtException){
                throw new RuntimeException("JWT Token Expired Exception");
            }
            else{
                throw new RuntimeException("JWT Token Validation Exception");
            }
        }
    }
}
