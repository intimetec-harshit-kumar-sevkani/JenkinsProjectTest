package com.example.springEmployee.Service;
import com.example.springEmployee.entity.Department;
import com.example.springEmployee.entity.Employee;
import com.example.springEmployee.entity.EmployeeDTO;
import com.example.springEmployee.entity.Role;
import com.example.springEmployee.repository.DepartmentRepository;
import com.example.springEmployee.repository.EmployeeRepository;
import com.example.springEmployee.repository.RoleRepository;
import com.example.springEmployee.service.EmployeeService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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
public class EmployeeServiceTesting {
    @TestConfiguration
    static class EmployeeServiceTestConfig {
        @Bean
        public EmployeeService employeeService() {
            return new EmployeeService();
        }
    }
    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private DepartmentRepository departmentRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;
    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = new Employee();
        employee.setEmpId(122);
        employee.setPassword("sdfghj");
        employee.setEmpName("ertyuj");
        employee.setActive(true);
        employee.setUserName("qwert");
    }

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testGetAll(){

        Employee employee1 = new Employee();
        employee1.setEmpId(9);
        employee1.setEmpName("emp9");
        employee1.setUserName("qwerty");
        employee1.setActive(true);
        employee1.setPassword("asdfg");

        Employee employee2 = new Employee();
        employee2.setEmpId(10);
        employee2.setEmpName("emp10");
        employee2.setUserName("qwertywert");
        employee2.setActive(true);
        employee2.setPassword("asdfgewrt");

        given(employeeRepository.findAll()).willReturn(List.of(employee1,employee2));

        List<Employee> employeeList = (List<Employee>) employeeService.getAll();

        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

    }

    @Test
    public void test_getById() {

        int validId = 10;
        Employee employee = new Employee();

        when(employeeRepository.findById(validId)).thenReturn(Optional.of(employee));

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        Employee result = employeeService.getById(validId);

        assertThat(result).isNotNull();

    }

    @Test
    public void testAddEmployee(){
        Department department = new Department();
        department.setDeptId(1);
        department.setDeptName("devops");
        department.setActive(true);

        Role role = new Role();
        role.setRoleId(1);
        role.setRoleName("USER");

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmpName("qwert");
        employeeDTO.setDeptName(department.getDeptName());
        employeeDTO.setPassword("ertysdf");
        employeeDTO.setUserName("reetyu");
        employeeDTO.setRoleName(role.getRoleName());

        Employee employee= new Employee();
        employee.setEmpName("qwert");
        employee.setDepartment(department);
        employee.setPassword("ertysdf");
        employee.setUserName("reetyu");
        employee.setRole(role);
        employee.setActive(true);

        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);

        given(departmentRepository.findByDeptName(department.getDeptName())).willReturn(department);
        given(roleRepository.findByRoleName(role.getRoleName())).willReturn(role);
        given(passwordEncoder.encode(employeeDTO.getPassword())).willReturn("ertysdf");

        when(employeeRepository.save(employeeCaptor.capture())).thenAnswer(invocation -> {
            Employee savedEmployee = invocation.getArgument(0);
            savedEmployee.setEmpId(1);
            return savedEmployee;
        });

        Employee savedEmployee = employeeService.add(employeeDTO);

        verify(departmentRepository, times(1)).findByDeptName(department.getDeptName());
        verify(roleRepository, times(1)).findByRoleName(role.getRoleName());
        verify(passwordEncoder, times(1)).encode(employeeDTO.getPassword());
        verify(employeeRepository, times(1)).save(employeeCaptor.capture());

        assertThat(savedEmployee).isNotNull();

    }

}
