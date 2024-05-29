package com.example.springEmployee.Config;

import com.example.springEmployee.entity.Department;
import com.example.springEmployee.repository.DepartmentRepository;
import com.example.springEmployee.repository.EmployeeRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class DepartmentRepositoryTestConfig {
    @Bean
    public DepartmentRepository departmentRepository() {
        return mock(DepartmentRepository.class);
    }
}

