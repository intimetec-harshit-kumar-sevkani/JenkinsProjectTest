package com.example.springEmployee.Service;

import com.example.springEmployee.entity.Department;
import com.example.springEmployee.repository.DepartmentRepository;
import com.example.springEmployee.service.DepartmentService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;



@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTesting {
    @TestConfiguration
    static class DepartmentServiceTestConfig {
        @Bean
        public DepartmentService departmentService() {
            return new DepartmentService();
        }
    }
    @MockBean
    private DepartmentRepository departmentRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;
    private Department department;

    @BeforeEach
    public void setup(){
        department = new Department();
        department.setDeptId(10);
        department.setDeptName("ertyuj");
        department.setActive(true);
    }

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void testGetAll(){

        Department department1 = new Department();
        department1.setDeptId(9);
        department1.setDeptName("werty");
        department1.setActive(true);

        Department department2 = new Department();
        department2.setDeptId(10);
        department2.setDeptName("qwefrgt");
        department2.setActive(true);


        given(departmentRepository.findAll()).willReturn(List.of(department1,department2));

        List<Department> departmentList = (List<Department>) departmentService.getAll();

        assertThat(departmentList).isNotNull();
        assertThat(departmentList.size()).isEqualTo(2);

    }

    @Test
    public void test_getById() {

        int validId = 10;
        Department department1= new Department();

        when(departmentRepository.findById(validId)).thenReturn(Optional.of(department1));

        System.out.println(departmentRepository);
        System.out.println(departmentService);

        Department result = departmentService.getById(validId);

        assertThat(result).isNotNull();

    }

    @Test
    public void testAddDepartment(){
        Department department = new Department();
        department.setActive(true);
        department.setDeptName("devops");
        department.setDeptId(1);

        given(departmentRepository.save(department)).willReturn(department);

        Department savedDepartment = departmentService.add(department);

        System.out.println(savedDepartment);

        assertThat(savedDepartment).isNotNull();
    }

}