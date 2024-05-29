package com.example.springEmployee.repository;

import com.example.springEmployee.entity.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {

    public  Department findByDeptName(String deptName);

}
