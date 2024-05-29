package com.example.springEmployee.Config;

import com.example.springEmployee.jwt.JwtHelper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class JwtTokenConfig {
    @Bean
    public JwtHelper jwtHelper() {
        return mock(JwtHelper.class);
    }
}