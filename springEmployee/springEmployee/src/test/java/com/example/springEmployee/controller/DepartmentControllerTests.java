package com.example.springEmployee.controller;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.example.springEmployee.Config.*;
import com.example.springEmployee.entity.Department;
import com.example.springEmployee.service.DepartmentService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
@ContextConfiguration(classes = {CustomUserDetailServiceTestConfig.class, EmployeeRepositoryTestConfig.class, DepartmentRepositoryTestConfig.class, JwtTokenConfig.class})
public class DepartmentControllerTests {

 //   private static ExtentReports extent;

    private static ExtentReports extent; //= ExtentReportManager.getInstance();

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
    private DepartmentService departmentService;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void test_AddDepartment() throws Exception {
        ExtentTest test = extent.createTest("test_AddDepartment");
        try {
            Department department = new Department();
            department.setDeptName("devops");
            department.setActive(true);
            String requestBody = objectMapper.writeValueAsString(department);
            when(departmentService.add(any(Department.class))).thenReturn(department);

            mockMvc.perform(post("/department")
                            .header("Authorization", "Bearer " + "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbXAzIiwiaWF0IjoxNjkwMTc1MjM4LCJleHAiOjE2OTAxOTMyMzh9.cgiD22xJ30uv1GA14u06Gq2wqphAqyzVrvps97jH-Ap6o2uyNH0731G230P4uCQeHstcLqsWNKEKRjpQbfMj7g")
                            .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                    .andExpect(status().isCreated())
                    .andDo(print());

            test.log(Status.PASS, "test_AddDepartment passed");
        }  catch (Exception e) {
            test.log(Status.FAIL, "test_AddDepartment failed");
        }

    }

    @Test
    public void test_getAllDepartments() throws Exception {
        Department department = new Department();
        Department department1 = new Department();
        List<Department> departmentList = new ArrayList<Department>();
        department.setDeptId(10);
        department.setDeptName("asd");
        department.setActive(true);
        departmentList.add(department);
        department1.setDeptId(11);
        department1.setDeptName("qwe");
        department1.setActive(true);
        departmentList.add(department1);

        when(departmentService.getAll()).thenReturn(departmentList);
        mockMvc.perform(get("/department")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void test_getDepartment() throws Exception {
        Department department = new Department();
        department.setDeptId(10);
        department.setDeptName("asd");
        department.setActive(true);

        when(departmentService.getById(10)).thenReturn(department);
        mockMvc.perform(get("/department/{id}", 10)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void test_deleteDepartment() throws Exception {
        Department department = new Department();
        department.setDeptId(10);
        department.setDeptName("asd");
        department.setActive(true);
        doNothing().when(departmentService).delete(department.getDeptId());
        mockMvc.perform(delete("/department/{deptId}", 10)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(department)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }



}

