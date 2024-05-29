package com.example.springEmployee.Config;

import com.example.springEmployee.jwt.CustomUserDetailService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class CustomUserDetailServiceTestConfig {

    @Bean
    public CustomUserDetailService customUserDetailService() {
        return mock(CustomUserDetailService.class);
    }
}