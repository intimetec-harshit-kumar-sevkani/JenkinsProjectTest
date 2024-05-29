package com.example.springEmployee.controller;
import com.example.springEmployee.Exception.DepartmentNotFoundException;
import com.example.springEmployee.entity.Department;
import com.example.springEmployee.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/department")
    public ResponseEntity<List<Department>> getAllDepartments()
    {
        List<Department> list = (List<Department>) departmentService.getAll();
        if(list.size() <= 0)
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.of(Optional.of(list));
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<?> getDepartment(@PathVariable("id") int id) {
       try {
           Department department = departmentService.getById(id);
           return ResponseEntity.of(Optional.of(department));
       }
       catch(DepartmentNotFoundException ex)
       {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
       }
       catch(Exception e)
       {
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
    }


    @PostMapping("/department")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Department> addDepartment(@RequestBody Department department)
    {
        Department dept = null ;
        try
        {
            dept = this.departmentService.add(department);
            return ResponseEntity.status(HttpStatus.CREATED).body(dept);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/department/{deptId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDepartment(@PathVariable("deptId") int deptId){
        try{
            this.departmentService.delete(deptId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PutMapping("/department/{deptId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateDepartment(@PathVariable("deptId") int deptId , @RequestBody Department department)
    {
        Department dept = null ;
        try
        {
            dept = this.departmentService.update(deptId,department);
            if(dept != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(dept);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dept);
            }
        }
        catch(DepartmentNotFoundException ex)
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
/*

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<String> handleDepartmentNotFoundException(DepartmentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
*/
