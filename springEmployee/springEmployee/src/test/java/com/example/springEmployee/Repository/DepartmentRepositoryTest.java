package com.example.springEmployee.Repository;

import com.example.springEmployee.entity.Department;
import com.example.springEmployee.repository.DepartmentRepository;
import com.example.springEmployee.repository.EmployeeRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Order(1)
    @Test
    @Rollback(value = false)
    void saveDepartmentTest() {
        Department department = new Department();
        department.setDeptName("wergh");
        department.setActive(true);

        departmentRepository.save(department);

        assertThat(department.getDeptId()).isGreaterThan(0);
    }


    @Order(2)
    @Test
    @Rollback(value = false)
    void getDepartmentTest() {
        Department department = departmentRepository.findById(52).get();
        assertThat(department.getDeptId()).isEqualTo(52);
    }

    @Order(3)
    @Test
    @Rollback(value = false)
    void getAllDepartmentTest() {
        List<Department> departments = (List<Department>) departmentRepository.findAll();
        assertThat(departments.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateDepartmentTest() {

        Department department = departmentRepository.findById(52).get();

        department.setDeptName("sdfgh");

        Department departmentUpdated = departmentRepository.save(department);

        assertThat(departmentUpdated.getDeptName()).isEqualTo("sdfgh");

    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteDepartmentTest() {

        Department department = departmentRepository.findById(52).get();

        department.setActive(false);

        departmentRepository.save(department);

        Department existingDepartment = null;

        Optional<Department> optionalDepartment = departmentRepository.findById(52);

        if (optionalDepartment.isPresent()) {
            existingDepartment = optionalDepartment.get();
        }

        assertThat(existingDepartment.isActive()).isFalse();
    }

}

