package com.example.springEmployee.repository;

import com.example.springEmployee.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {


   public Optional<Employee> findByUserName(String userName);

}
