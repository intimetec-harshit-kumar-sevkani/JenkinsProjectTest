package com.example.springEmployee.service;

import com.example.springEmployee.Exception.DepartmentNotFoundException;
import com.example.springEmployee.entity.Department;
import com.example.springEmployee.entity.Employee;
import com.example.springEmployee.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public Iterable<Department> getAll(){

        List<Department> departments = ( List<Department>) this.departmentRepository.findAll();
        return departments.stream()
                .filter(Department::isActive)
                .collect(Collectors.toList());
    }

    public Department getById(int id){

        Department department = new Department();
        department = this.departmentRepository.findById(id).orElse(null);
        if(department != null)
        {
            if(department.isActive())
            {
                return department;
            }
        }
        throw new DepartmentNotFoundException("Department with ID " + id + " not found.");
    }

    public Department add(Department department){
        department.setActive(true);
        return departmentRepository.save(department);
    }

    public Department update(int deptId,Department department)
    {
        Department updateDepartment= departmentRepository.findById(deptId).orElse(null);
        if(updateDepartment != null)
        {
            if(updateDepartment.isActive()) {
                updateDepartment.setDeptName(department.getDeptName());
                return departmentRepository.save(department);
            }
        }
        throw new DepartmentNotFoundException("Department with ID " + deptId + " not found.");
    }

    public void delete(int id) {
        Department department = departmentRepository.findById(id).orElse(null);
        if(department != null) {
            if(department.isActive()) {
                department.setActive(false);
            }
        }
    }

}


