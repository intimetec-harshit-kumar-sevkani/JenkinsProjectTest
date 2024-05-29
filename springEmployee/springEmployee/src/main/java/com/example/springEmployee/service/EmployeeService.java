package com.example.springEmployee.service;

import com.example.springEmployee.Exception.EmployeeNotFoundException;
import com.example.springEmployee.entity.Department;
import com.example.springEmployee.entity.Employee;
import com.example.springEmployee.entity.EmployeeDTO;
import com.example.springEmployee.entity.Role;
import com.example.springEmployee.repository.DepartmentRepository;
import com.example.springEmployee.repository.EmployeeRepository;
import com.example.springEmployee.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Iterable<Employee> getAll(){

        List<Employee> employees = ( List<Employee>) this.employeeRepository.findAll();
        return employees.stream()
                .filter(Employee::isActive)
                .collect(Collectors.toList());

    }

    public Employee getById(int id){
        Employee employee = new Employee();
        employee = this.employeeRepository.findById(id).orElse(null);
        if(employee != null)
        {
            if(employee.isActive())
            {
                return employee;
            }
        }
        throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");
    }

    public Employee add(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        Department dept = departmentRepository.findByDeptName(employeeDTO.getDeptName());
        Role role = roleRepository.findByRoleName(employeeDTO.getRoleName());
        employee.setEmpName(employeeDTO.getEmpName());
        employee.setActive(true);
        employee.setDepartment(dept);
        employee.setRole(role);
        employee.setUserName(employeeDTO.getUserName());
        employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        return employeeRepository.save(employee);
    }

    public Employee update(int empId , EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(empId).orElse(null);

         if(employee != null)
         {
             if(employee.isActive()) {
                 employee.setEmpName(employeeDTO.getEmpName());
                 Department dept = departmentRepository.findByDeptName(employeeDTO.getDeptName());
                 Role role = roleRepository.findByRoleName(employeeDTO.getRoleName());
                 employee.setActive(true);
                 employee.setDepartment(dept);
                 employee.setRole(role);
                 employee.setUserName(employeeDTO.getUserName());
                 employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
                 return employeeRepository.save(employee);
             }
         }
        throw new EmployeeNotFoundException("Employee with ID " + empId + " not found.");

    }


    public void delete(int id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if(employee != null) {
            if(employee.isActive()) {
                employee.setActive(false);
            }
        }
    }

}
