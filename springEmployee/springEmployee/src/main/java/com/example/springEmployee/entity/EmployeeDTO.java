package com.example.springEmployee.entity;

public class EmployeeDTO {
    private String empName;
    private String deptName;

    private String userName;
    private String password;

    private String roleName;


    public EmployeeDTO() {
    }

    public EmployeeDTO(String empName, String deptName, String userName, String password, String roleName) {
        this.empName = empName;
        this.deptName = deptName;
        this.userName = userName;
        this.password = password;
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
