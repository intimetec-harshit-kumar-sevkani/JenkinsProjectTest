package com.example.springEmployee.controller;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.example.springEmployee.Config.CustomUserDetailServiceTestConfig;
import com.example.springEmployee.Config.EmployeeRepositoryTestConfig;
import com.example.springEmployee.Config.ExtentReportManager;
import com.example.springEmployee.Config.JwtTokenConfig;
import com.example.springEmployee.entity.Employee;
import com.example.springEmployee.entity.EmployeeDTO;
import com.example.springEmployee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@ContextConfiguration(classes = { CustomUserDetailServiceTestConfig.class, EmployeeRepositoryTestConfig.class, JwtTokenConfig.class})
public class EmployeeControllerTests {

  //  private static ExtentReports extent;

    private static ExtentReports extent = ExtentReportManager.getInstance();

    @BeforeAll
    public static void setUpExtentReport() {
        // Initialize the Extent Report
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("target/extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @AfterAll
    public static void tearDown() {
        // Flush the Extent Report
        extent.flush();
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_AddEmployee() throws Exception {
        System.out.println( "Jenkins build number: " + System.getenv("BUILD_NUMBER"));
        ExtentTest test = extent
                .createTest("test_AddEmployee")
                .assignCategory("Employee");
     try {
         EmployeeDTO employee = new EmployeeDTO("asd", "JSE", "emp4", "qwe", "ADMIN");
         Employee employee1 = new Employee();
         String requestBody = objectMapper.writeValueAsString(employee);
         when(employeeService.add(employee)).thenReturn(employee1);
         mockMvc.perform(post("/employee")
                         // .header("Authorization","Bearer "+"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbXAzIiwiaWF0IjoxNjkwMTc1MjM4LCJleHAiOjE2OTAxOTMyMzh9.cgiD22xJ30uv1GA14u06Gq2wqphAqyzVrvps97jH-Ap6o2uyNH0731G230P4uCQeHstcLqsWNKEKRjpQbfMj7g")
                         .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                 .andExpect(status().isCreated())
                 .andDo(print());
         test.log(Status.PASS, "test_AddEmployee passed" + System.getenv("JENKINS_HOME"));
     }  catch (Exception e) {
         test.log(Status.FAIL, "test_AddEmployee failed");
     }

    }

    @Test
    public void test_deleteEmployee() throws Exception {
        ExtentTest test = extent.createTest("test_deleteEmployee").assignCategory("Employee");
        try {
            Employee employee = new Employee();
            employee.setEmpId(10);
            employee.setPassword("1234");
            employee.setActive(true);
            employee.setEmpName("zxcv");
            employee.setUserName("emp2");
            doNothing().when(employeeService).delete(employee.getEmpId());
            mockMvc.perform(delete("/employees/{empId}", 10)
                            .header("Authorization", "Bearer " + "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbXAzIiwiaWF0IjoxNjkwMTc1MjM4LCJleHAiOjE2OTAxOTMyMzh9.cgiD22xJ30uv1GA14u06Gq2wqphAqyzVrvps97jH-Ap6o2uyNH0731G230P4uCQeHstcLqsWNKEKRjpQbfMj7g")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(employee)))
                    .andExpect(status().isNoContent())
                    .andDo(print());
            test.log(Status.PASS, "test_deleteEmployee passed");
        }  catch (Exception e) {
            test.log(Status.FAIL, "test_deleteEmployee failed");
        }


    }

    @Test
    public void test_getEmployee() throws Exception {
        ExtentTest test = extent.createTest("test_getEmployee").assignCategory("Employee");
        try {
            Employee employee = new Employee();
            employee.setEmpId(10);
            employee.setPassword("1234");
            employee.setActive(true);
            employee.setEmpName("zxcv");
            employee.setUserName("emp2");

            when(employeeService.getById(10)).thenReturn(employee);
            mockMvc.perform(get("/employees/{id}", 10)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print());

            test.log(Status.PASS, "test_getEmployee passed");

        }  catch (Exception e) {
            test.log(Status.FAIL, "test_getEmployee failed");
        }

    }


    @Test
    public void test_getAllEmployees() throws Exception {
        ExtentTest test = extent.createTest("test_getAllEmployees").assignCategory("Department");
        try {
            Employee employee = new Employee();
            Employee employee1 = new Employee();
            List<Employee> employeesList = new ArrayList<Employee>();

            employee.setEmpId(10);
            employee.setPassword("1234");
            employee.setActive(true);
            employee.setEmpName("zxcv");
            employee.setUserName("emp2");

            employeesList.add(employee);

            employee1.setEmpId(11);
            employee1.setPassword("12345");
            employee1.setActive(true);
            employee1.setEmpName("zxcvytre");
            employee1.setUserName("emp3");

            employeesList.add(employee1);

            when(employeeService.getAll()).thenReturn(employeesList);
            mockMvc.perform(get("/employees")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print());

            test.log(Status.PASS, "test_getAllEmployees passed");

        }  catch (Exception e) {
            test.log(Status.FAIL, "test_getAllEmployees failed");
        }

    }

    }