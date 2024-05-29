package com.example.springEmployee.repository;

import com.example.springEmployee.entity.Employee;
import com.example.springEmployee.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Integer> {

    public Role findByRoleName(String roleName);

}
