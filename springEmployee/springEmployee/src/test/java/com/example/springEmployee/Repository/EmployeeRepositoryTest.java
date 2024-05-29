package com.example.springEmployee.Repository;
import com.example.springEmployee.entity.Employee;
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
class EmployeeRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Order(1)
    @Test
    @Rollback(value = false)
    void saveEmployeeTest(){
        Employee employee = new Employee();
        employee.setEmpName("wergh");
        employee.setActive(true);

        employeeRepository.save(employee);

        assertThat(employee.getEmpId()).isGreaterThan(0);
    }


    @Order(2)
    @Test
    @Rollback(value = false)
    void getEmployeeTest(){
        Employee employee = employeeRepository.findById(152).get();
        assertThat(employee.getEmpId()).isEqualTo(152);
    }

    @Order(3)
    @Test
    @Rollback(value = false)
    void getEmployeesTest(){
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();
        assertThat(employees.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateEmployeeTest(){

        Employee employee = employeeRepository.findById(152).get();

        employee.setEmpName("wqerty");

        Employee employeeUpdated =  employeeRepository.save(employee);

        assertThat(employeeUpdated.getEmpName()).isEqualTo("wqerty");

    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteEmployeeTest(){

        Employee employee = employeeRepository.findById(152).get();

        employee.setActive(false);

        employeeRepository.save(employee);

        Employee existingEmployee = null;

        Optional<Employee> optionalEmployee = employeeRepository.findById(152);

        if(optionalEmployee.isPresent()){
            existingEmployee = optionalEmployee.get();
        }

        assertThat(existingEmployee.isActive()).isFalse();
    }

}










