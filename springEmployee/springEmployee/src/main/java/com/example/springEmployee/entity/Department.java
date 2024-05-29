package com.example.springEmployee.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int deptId;

    private String deptName;
    private boolean isActive;

    @OneToMany(mappedBy="department",cascade = CascadeType.ALL)
    @JsonBackReference
    @JsonIgnore
    private List<Employee> employees;

    public Department() {
    }

    public Department(int deptId, String deptName, boolean isActive, List<Employee> employees) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.isActive = isActive;
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Department{" +
                "deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", isActive=" + isActive +
                ", employees=" + employees +
                '}';
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
