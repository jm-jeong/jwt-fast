package com.example.jwtfast.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void contextLoads() {

    }

    @Test
    void tokenCreate() {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("user_id", 923);

        LocalDateTime expiredAt = LocalDateTime.now().plusSeconds(300);

        String jwtToken = jwtService.create(claims, expiredAt);
        System.out.println(jwtToken);
    }

    @Test
    void tokenValidation() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo5MjMsImV4cCIs6MTcwMzI0MTUwM30.IjP5ISHwWu1ZL85EXq_sCEH3pCD2NwCiu5QXpQm9lOs";
        jwtService.validation(token);
    }
}