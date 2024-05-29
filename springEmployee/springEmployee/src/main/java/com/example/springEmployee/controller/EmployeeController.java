package com.example.springEmployee.controller;
import com.example.springEmployee.Exception.DepartmentNotFoundException;
import com.example.springEmployee.Exception.EmployeeNotFoundException;
import com.example.springEmployee.entity.Employee;
import com.example.springEmployee.entity.EmployeeDTO;
import com.example.springEmployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@ExposesResourceFor(Employee.class)
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService ;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees()
    {
        List<Employee> list = (List<Employee>) employeeService.getAll();
        if(list.size() <= 0)
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.of(Optional.of(list));
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable("id") int id) {
       try {
           Employee employee = employeeService.getById(id);
           return ResponseEntity.of(Optional.of(employee));
       }
       catch(EmployeeNotFoundException ex)
       {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
       }
       catch(Exception e)
       {
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
    }

    @PostMapping("/employee")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Employee> addEmployee(@RequestBody EmployeeDTO employeeDTO)
    {
        Employee emp = null ;
        try
        {
            emp = this.employeeService.add(employeeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(emp);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/employees/{empId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("empId") int empId){
        try{
            this.employeeService.delete(empId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/employee/{empId}")
    public ResponseEntity<?> updateEmployee(@PathVariable("empId") int empId , @RequestBody EmployeeDTO employeeDTO)
    {
        Employee emp = null ;
        try
        {
            emp = this.employeeService.update(empId,employeeDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(emp);
        }
        catch(EmployeeNotFoundException ex)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
