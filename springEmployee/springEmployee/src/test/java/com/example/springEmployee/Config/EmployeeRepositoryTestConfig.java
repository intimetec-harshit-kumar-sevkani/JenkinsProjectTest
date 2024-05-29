package com.example.springEmployee.Config;

import com.example.springEmployee.repository.EmployeeRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class EmployeeRepositoryTestConfig {

    @Bean
    public EmployeeRepository employeeRepository() {
        return mock(EmployeeRepository.class);
    }
}